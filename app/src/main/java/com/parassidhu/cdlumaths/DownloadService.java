package com.parassidhu.cdlumaths;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class DownloadService extends Service {

    public DownloadService() {
        super();
    }

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private int totalFileSize;
    private Notification.Builder notificationBuilder;
    private NotificationManager notificationManager;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            try {
                MyApp x = (MyApp) getApplicationContext();
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationBuilder = new Notification.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.newnotif)
                        .setContentTitle("Downloading")
                        .setContentText("Just a moment...")
                        .setOngoing(true)
                        .setAutoCancel(true);

                if (Build.VERSION.SDK_INT>=26) {
                    NotificationChannel notificationChannel = new NotificationChannel("download"
                            , "Download Service", NotificationManager.IMPORTANCE_LOW);
                    notificationChannel.enableLights(false);
                    notificationChannel.enableVibration(false);
                    notificationManager.createNotificationChannel(notificationChannel);
                    notificationBuilder.setChannelId("download");
                }

                if (!sidhu.isLollipop())
                    notificationBuilder.setSmallIcon(R.drawable.ic_file_download_deep_orange_a400_18dp);

                notificationManager.notify(x.ID, notificationBuilder.build());
                Log.i("Paras", "onHandleIntent: " + x.filename + x.url + "  " + x.ID);
                initDownload(x.filename, x.url, x.ID);
            }catch (Exception ex){}
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        MyApp x = (MyApp)getApplicationContext();
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    public void starting() {
        Toast.makeText(this, "The download is going to start...\n" +
                "1. Please check notifications panel for progress.\n" +
                "2. Access the file from Offline section present in top-left Navigation Drawer.", Toast.LENGTH_LONG).show();
    }

    private void initDownload(String filename, String url, int id) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(sidhu.checkPerm(DownloadService.this))
                    starting();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.downloadinformer.com/")
                .build();

        RequestInterface.RetrofitInterface retrofitInterface = retrofit.create(RequestInterface.RetrofitInterface.class);

        Call<ResponseBody> request = retrofitInterface.downloadFile(url);

        try {
            downloadFile(request.execute().body(),filename,id);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            String error = "There's some issue with Internet Connection. Please try again";
            notificationBuilder.setContentTitle("Download Failed!")
                        .setProgress(0,0,false)
                        .setContentText(error)
                        .setStyle(new Notification.BigTextStyle().bigText(error))
                        .setOngoing(false);

            notificationManager.notify(id, notificationBuilder.build());
        }
    }

    private void increaseDownloads(){
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    getResources().getString(R.string.downloads),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {}
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }catch (Exception e){}
    }

    private void downloadFile(ResponseBody body, String filename,int id) throws IOException {
        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory(), "/CDLU Mathematics Hub");

        try{mediaStorageDir.mkdirs();}catch (Exception e){}

        File outputFile = new File(Environment.getExternalStorageDirectory() +
                File.separator + "CDLU Mathematics Hub", filename);
        OutputStream output = new FileOutputStream(outputFile);

        long total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;

        while ((count = bis.read(data)) != -1) {

            total += count;
            totalFileSize = (int) (fileSize / (Math.pow(1, 2))) / 1000;
            double current = Math.round(total / (Math.pow(1, 2))) / 1000;

            int progress = (int) ((total * 100) / fileSize);

            long currentTime = System.currentTimeMillis() - startTime;

            Download download = new Download();
            download.setTotalFileSize(totalFileSize);

            if (currentTime > 1000 * timeCount) {
                download.setCurrentFileSize((int) current);
                download.setProgress(progress);
                sendNotification(download,id,filename);
                timeCount++;
            }

            output.write(data, 0, count);
        }

        onDownloadComplete(filename,id);
        output.flush();
        output.close();
        bis.close();

        //TODO: Modify and test it

        if (!BuildConfig.DEBUG)
        increaseDownloads();
    }

    private void sendNotification(Download download, int id,String filename) {
        sendIntent(download,id);

        String progress = "Downloading " + download.getCurrentFileSize() + "/" + totalFileSize + " KB";

        notificationBuilder.setProgress(100, download.getProgress(), false)
                            .setContentTitle(filename)
                            .setContentText(progress)
                            .setStyle(new Notification.BigTextStyle().bigText(progress));
        notificationManager.notify(id, notificationBuilder.build());
    }

    private void sendIntent(Download download, int id) {
        Intent intent = new Intent(sem1.MESSAGE_PROGRESS);
        intent.putExtra("download", download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(String filename,int id) {
        try {

            Download download = new Download();
            download.setProgress(100);
            sendIntent(download,id);

            notificationManager.cancel(id);
            notificationBuilder.setProgress(0, 0, false)
                                .setContentText("Tap to open")
                                .setOngoing(false)
                                .setStyle(new Notification.BigTextStyle().bigText("File Downloaded"));

            notificationManager.notify(id, notificationBuilder.build());

            String path1 =Environment.getExternalStorageDirectory() +
                    File.separator + "CDLU Mathematics Hub" + "/" + filename;

            File file = new File(path1);
            Uri sharePath;

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
                sharePath = FileProvider.getUriForFile(this,
                        getApplicationContext().getPackageName() + ".provider", file);
            else sharePath = Uri.fromFile(file);

            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension
                    (MimeTypeMap.getFileExtensionFromUrl(path1));

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW)
                        .setType(mimeType)
                        .setDataAndType(sharePath, mimeType)
                        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            PendingIntent pIntent = PendingIntent.getActivity(this,(int) System.currentTimeMillis(),
                    intent, 0);

            notificationBuilder
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .setContentTitle(filename + " Downloaded")
                    .setOngoing(false);

            Log.i("Paras", "onDownloadComplete: " + filename);

            notificationManager.notify(id, notificationBuilder.build());

        }catch (Exception ex){}
    }
}
