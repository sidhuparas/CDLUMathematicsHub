package com.parassidhu.cdlumaths.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.DialogUtils;

import java.io.File;
import java.io.FileWriter;

public class Feedback extends AppCompatActivity {

    private StorageReference mStorageRef;
    private ProgressDialog pDialog;
    private RatingBar smileRating;
    private final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE=42;

    private EditText feedback;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        android.support.v7.app.ActionBar acb = getSupportActionBar();
        acb.setTitle("Send Feedback");
        acb.show();
        AppUtils.renderTheme(this);

        feedback = findViewById(R.id.editText3);
        name = findViewById(R.id.editText2);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        name.clearFocus();
        feedback.clearFocus();

        smileRating = findViewById(R.id.ratingBar);
        final Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().length()==0 || feedback.getText().length()==0){
                    Toast.makeText(Feedback.this, "Please fill the given fields.", Toast.LENGTH_SHORT).show();
                }else{
                      if (checkPermissions())
                          processFeedback();
                }

            }
        });

        feedback.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btn.callOnClick();
                }
                return false;
            }
        });
        smileRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override public void onRatingChanged(RatingBar ratingBar, float rating,
                                                  boolean fromUser) {
                if(rating<1.0f)
                    ratingBar.setRating(1.0f);
            }
        });
    }

    private void processFeedback(){
        pDialog = new ProgressDialog(this);

        pDialog.setMessage("Transmitting to the team... Just few moments...");
        pDialog.setIndeterminate(true);
        pDialog.show();
        String filename = name.getText().toString() + "_" + (int) System.currentTimeMillis() + ".txt";
        String separator = System.getProperty("line.separator");
        createFile(filename, "Name: " + name.getText() + separator +
                "App Version: " + giveVersion() + separator +
                "OS: " + Build.VERSION.RELEASE + separator +
                "Rating: " + smileRating.getRating() + separator +
                "Feedback: " + separator + feedback.getText());
    }

    private String giveVersion(){
        PackageInfo packageInfo=null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "Error";
    }
    private boolean checkPermissions(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) ==PackageManager.PERMISSION_DENIED){
            DialogUtils.permBox(this, "We need storage permissions to send feedback.", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(Feedback.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
                }
            });
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    processFeedback();
                } else {
                    Toast.makeText(this, "Permissions for accessing External Storage not granted! " +
                            "Please go to Settings->Apps to grant Storage permission.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean allow(){
        return !(name.getText().toString().isEmpty() || feedback.getText().toString().isEmpty());
    }

    private void createFile(String filename, String content){
        try{
            File root = new File(Environment.getExternalStorageDirectory().getPath(),"CDLU Mathematics Hub");
            if (!root.exists())
                root.mkdirs();
            File txtfile = new File(root,filename);
            FileWriter writer = new FileWriter(txtfile);
            writer.write(content);
            writer.flush();
            writer.close();
            uploadFile(filename);
        }catch (Exception e){
            pDialog.dismiss();
            Toast.makeText(this, "Some error occurred!", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFeed(String filename){
        try{
            File root = new File(Environment.getExternalStorageDirectory().getPath(),"CDLU Mathematics Hub");
            if (!root.exists())
                root.mkdirs();
            File txtfile = new File(root,filename);
            txtfile.delete();
        }catch (Exception e){
            pDialog.dismiss();
            Toast.makeText(this, "Some error occurred!", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadFile(final String filename){
        try {
            String path = Environment.getExternalStorageDirectory().getPath();
            Uri file = Uri.fromFile(new File(path + "/CDLU Mathematics Hub/" + filename));
            StorageReference riversRef = mStorageRef.child(filename);

            riversRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          try {
                              pDialog.dismiss();
                              Toast.makeText(Feedback.this, "Thank you! Your feedback has been successfully sent.", Toast.LENGTH_LONG).show();
                              deleteFeed(filename);
                          }catch (Exception e){}
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            try{
                            pDialog.dismiss();
                            Toast.makeText(Feedback.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                            deleteFeed(filename);
                            }catch (Exception e){}
                        }
                    });
        }catch (Exception e){
            pDialog.dismiss();
            Toast.makeText(this, "Error occurred!", Toast.LENGTH_SHORT).show();
            deleteFeed(filename);
        }
    }
}
