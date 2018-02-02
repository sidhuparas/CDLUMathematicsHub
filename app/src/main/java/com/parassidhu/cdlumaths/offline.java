package com.parassidhu.cdlumaths;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static android.content.Context.MODE_PRIVATE;

public class offline extends Fragment {
    DataAdapter adapter;
    SharedPreferences sharedPreferences;
    private String[] code = {"Name", "Date Created"};
    ArrayList<String> names;
    TextView textView;
    Button sortbtn,theme;
    int p;
    String one,two,three;
    String nothing = "nothing";
    final String root = Environment.getExternalStorageDirectory().toString();
    public offline() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offline, container, false);
    }

    public ArrayList<String> getFileNamesW(File[] file){
        sharedPreferences = getActivity().getSharedPreferences("offlinesorting", MODE_PRIVATE);
        int sort = sharedPreferences.getInt("offlinesorting",0);

        ArrayList<String> arrayFiles;
        if (sort==0) {
            arrayFiles = new ArrayList<String>();
            if (file.length == 0)
                return null;
            else {
                for (int i = 0; i < file.length; i++) {
                    String fileName = file[i].getName();
                    //arrayFiles.add(file[i].getName().split("\\.")[count-1]);
                    String newName = fileName.substring(0,fileName.lastIndexOf("."));
                    arrayFiles.add(newName);

                }
            }
            Collections.sort(arrayFiles);
        }else {
            arrayFiles = new ArrayList<String>();
            Pair[] pairs = new Pair[file.length];
            for (int i = 0; i < file.length; i++)
                pairs[i] = new Pair(file[i]);
            Arrays.sort(pairs);
            for (int i = 0; i < file.length; i++) {
                file[i] = pairs[i].f;
                String fileName = file[i].getName();

                String newName = fileName.substring(0,fileName.lastIndexOf("."));
                //arrayFiles.add(file[i].getName().split("\\.")[count-1]);
                arrayFiles.add(newName);
            }
            Collections.reverse(arrayFiles);
        }

        for(int i =0;i<file.length;i++){
            if (arrayFiles.get(i).equals(getPinValue("2"))){
                String ithval = arrayFiles.get(i);
                arrayFiles.remove(i);
                arrayFiles.add(0,ithval);
                break;
            }
        }

        for(int i =0;i<file.length;i++){
            if (arrayFiles.get(i).equals(getPinValue("1"))){
                String ithval = arrayFiles.get(i);
                arrayFiles.remove(i);
                arrayFiles.add(0,ithval);
                break;
            }
        }

        for(int i =0;i<file.length;i++){
            if (arrayFiles.get(i).equals(getPinValue("0"))){
                String ithval = arrayFiles.get(i);
                arrayFiles.remove(i);
                arrayFiles.add(0,ithval);
                break;
            }
        }

        return arrayFiles;
    }

    String path = Environment.getExternalStorageDirectory().toString()+"/CDLU Mathematics Hub";

    private void reloadData(){
        File f = new File(path);
        File file[] = f.listFiles();
        names = getFileNamesW(file);
        setup();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        getActivity().setTitle("Offline");

        TextView nofile = getActivity().findViewById(R.id.nofile);
        try {
            nofile.setVisibility(View.GONE);
            reloadData();
        }
        catch (Exception ex){
            nofile.setVisibility(View.VISIBLE);
            sortbtn=getActivity().findViewById(R.id.sortBtn);
            theme=getActivity().findViewById(R.id.theme);
            sortbtn.setVisibility(View.GONE);
            theme.setVisibility(View.GONE);
        }
    }
    Boolean remove = true;

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort);
        sidhu.setOptVisibility(menu,false,true);
    }

    private String giveName(){
        sharedPreferences = getActivity().getSharedPreferences("Pin", MODE_PRIVATE);
        one = sharedPreferences.getString("0",nothing);
        two = sharedPreferences.getString("1",nothing);
        three = sharedPreferences.getString("2",nothing);
        if (one.equals(nothing))
            return "0";
        if (two.equals(nothing))
            return "1";
        if (three.equals(nothing))
            return "2";
        return nothing;
    }

    private String getPinValue(String s){
        sharedPreferences = getActivity().getSharedPreferences("Pin", MODE_PRIVATE);
        return sharedPreferences.getString(s,nothing);
    }

    private void setPinValue(String s, String p){
        sharedPreferences = getActivity().getSharedPreferences("Pin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(s,p);
        editor.apply();
    }

    private void shiftPinValue(String s){
        sharedPreferences = getActivity().getSharedPreferences("Pin", MODE_PRIVATE);
        one = sharedPreferences.getString("0",nothing);
        two = sharedPreferences.getString("1",nothing);
        three = sharedPreferences.getString("2",nothing);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (s){
            case "0":
                setPinValue("0",two);
                setPinValue("1",three);
                setPinValue("2",nothing);
                break;
            case "1":
                setPinValue("0",one);
                setPinValue("1",three);
                setPinValue("2",nothing);
                break;
            case "2":
                setPinValue("0",one);
                setPinValue("1",two);
                setPinValue("2",nothing);
                break;
        }
        editor.apply();
    }

    private void PinItNow(String s){
        sharedPreferences = getActivity().getSharedPreferences("Pin", MODE_PRIVATE);
        one = sharedPreferences.getString("0",nothing);
        two = sharedPreferences.getString("1",nothing);
        three = sharedPreferences.getString("2",nothing);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("0",s);
        editor.putString("1",one);
        editor.putString("2",two);
        editor.apply();
        reloadData();
    }

    public void showSheet(final View v,final ArrayList androidVersions,final TextView textView, final int position){
        int pinMenu = R.menu.list;

        if (textView.getText().toString().equals(getPinValue("0")) ||
                textView.getText().toString().equals(getPinValue("1")) ||
                        textView.getText().toString().equals(getPinValue("2")))
            pinMenu = R.menu.list2;
        new BottomSheet.Builder(getActivity(),R.style.BottomSheet_StyleDialog).sheet(pinMenu).title(textView.getText().toString())
                .listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.PinTo:
                        sharedPreferences = getActivity().getSharedPreferences("Pin", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if (!giveName().equals(nothing))
                            PinItNow(textView.getText().toString());
                        else Toast.makeText(getActivity(), "Maximum 3 files can be pinned at a time. Please unpin one of them to pin this."
                                , Toast.LENGTH_LONG).show();

                        editor.apply();
                        break;
                    case R.id.Unpin:
                        if(getPinValue("0").equals(textView.getText().toString())) {
                            setPinValue("0", nothing);
                            androidVersions.remove(position);
                            adapter.notifyItemRemoved(position);
                            shiftPinValue("0");
                        }
                        if(getPinValue("1").equals(textView.getText().toString())) {
                            setPinValue("1", nothing);
                            androidVersions.remove(position);
                            adapter.notifyItemRemoved(position);
                            shiftPinValue("1");
                        }
                        if(getPinValue("2").equals(textView.getText().toString())) {
                            setPinValue("2", nothing);
                            androidVersions.remove(position);
                            adapter.notifyItemRemoved(position);
                            shiftPinValue("2");
                        }
                        reloadData();
                        break;
                    case R.id.delete1:
                        try {
                            androidVersions.remove(position);
                            adapter.notifyItemRemoved(position);
                            final Handler mHandler = new Handler(Looper.getMainLooper()) {
                                @Override
                                public void handleMessage(Message message) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (remove) {
                                                File file = new File(root + "/CDLU Mathematics Hub/" + textView.getText().toString() + ".pdf");
                                                if (file.exists()) {
                                                    file.delete();
                                                }
                                            } else {
                                                remove = true;
                                            }
                                        }
                                    }, 2000);
                                }
                            };
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Message message = mHandler.obtainMessage();
                                    message.sendToTarget();
                                }
                            });
                            t.start();

                            Snackbar.make(v, textView.getText().toString() + " deleted successfully!", 2000)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            remove = false;
                                            try {
                                                String path = Environment.getExternalStorageDirectory().toString() + "/CDLU Mathematics Hub";
                                                File f = new File(path);
                                                File file1[] = f.listFiles();
                                                names = getFileNamesW(file1);
                                                setup();
                                            } catch (Exception a) {
                                                TextView nofile = getActivity().findViewById(R.id.nofile);
                                                RecyclerView off = getActivity().findViewById(R.id.offlinerecycle);
                                                off.setVisibility(View.GONE);
                                                nofile.setVisibility(View.VISIBLE);
                                                getActivity().setTitle("Offline");
                                            }
                                        }
                                    }).show();
                        }catch (Exception ex){}
                        break;
                    case R.id.shar:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        File file1= new File(root + "/CDLU Mathematics Hub/"+textView.getText().toString() +".pdf");
                        Uri sharePath;
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
                            sharePath = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", file1);
                        }else {
                            sharePath = Uri.fromFile(file1);
                        }
                        sendIntent.putExtra(Intent.EXTRA_STREAM, sharePath);
                        sendIntent.setType("application/pdf");
                        startActivity(Intent.createChooser(sendIntent,"Share "+textView.getText().toString()));
                        break;
                    case R.id.addtohome:
                        if (!isOreo())
                        addShortcut(textView.getText().toString());
                        else addShortcutInOreo(textView.getText().toString());
                        break;
                }
            }
        }).show();
    }

    public void setup(){
        final RecyclerView rcl = getActivity().findViewById(R.id.offlinerecycle);
        Button sort = getActivity().findViewById(R.id.sortBtn);
        Button theme = getActivity().findViewById(R.id.theme);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcl.setLayoutManager(layoutManager);
        final ArrayList androidVersions;
        androidVersions = new ArrayList<>(prepareData());
        adapter = new DataAdapter(getActivity(),androidVersions,true,this);
        //Added Dividers
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcl.getContext(),DividerItemDecoration.VERTICAL);
        rcl.addItemDecoration(dividerItemDecoration);
        rcl.setAdapter(adapter);
        sidhu.setFastScrolling(rcl);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                openFile(names.get(position)+".pdf");
            }
        });

        ItemClickSupport.addTo(rcl).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(final RecyclerView recyclerView, final int position, final View v) {
                textView = v.findViewById(R.id.tv_android);
                showSheet(v,androidVersions,textView,position);
                return false;
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeThemeOptions();
            }
        });
    }

    private void changeThemeOptions(){
        ArrayList defaultList = new ArrayList<>();
        String[] list = {"Green","Red","Orange","Blue"};
        defaultList.addAll(Arrays.asList(list));

        new LovelyChoiceDialog(getActivity())
                .setTitle("Select Your Color")
                .setItems(defaultList, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(int position, String item) {
                        int a=110,b=110,c=0,d=0,e=0,f=0;
                        switch (position){
                            case 0:
                                a=76; b=175; c=80;
                                d=56; e=142; f=60 ;
                                break;
                            case 1:
                                a = 244; b = 67; c = 54;
                                d = 211; e = 47; f = 47;
                                break;
                            case 2:
                                a = 255; b = 152; c = 0;
                                d = 245; e = 124; f = 0;
                                break;
                            case 3:
                                a=3; b=169; c=244;
                                d=2; e=136; f=209 ;
                                break;
                        }
                        SharedPreferences sp = getActivity().getSharedPreferences("colors",MODE_PRIVATE);
                        SharedPreferences.Editor ed;

                        ed = sp.edit();
                        ed.putInt("r",a);
                        ed.putInt("g",b);
                        ed.putInt("b",c);
                        ed.putInt("e",d);
                        ed.putInt("f",e);
                        ed.putInt("v",f);

                        ed.apply();
                        Toast.makeText(getActivity(), "The changes will be applied once you restart the app!"
                                , Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(R.drawable.theme)
                .setTopColor(Color.rgb(Home.r,Home.g,Home.b ))
                .show();
    }

    public int getPosition(){
        sharedPreferences = getActivity().getSharedPreferences("offlinesorting", MODE_PRIVATE);
        int sort = sharedPreferences.getInt("offlinesorting",0);
        return sort;
    }

    public void showDialog(){
        final AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        AlertDialog d;
        b.setTitle("Sort Offline Files");
        b.setSingleChoiceItems(code, getPosition(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                p=i;
            }
        });
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sharedPreferences = getActivity().getSharedPreferences("offlinesorting", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (code[p].equals("Name")) {
                    editor.putInt("offlinesorting", 0);
                } else {
                    editor.putInt("offlinesorting", 1);
                }
                editor.apply();
                try {
                    if (getTag().equals("Offline")) {
                        Fragment fragment = new offline();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment, "Offline");
                        ft.commit();
                    }
                }catch (Exception e){}
            }
        });
        b.setNegativeButton("Cancel", null);
        d = b.create();
        d.show();
    }


    private void addShortcut(String path1) {
        File file = new File(Environment.getExternalStorageDirectory()+"/CDLU Mathematics Hub",path1+".pdf");
        if (file.exists()) {
            Uri path = Uri.fromFile(file);
            Intent shortcutIntent = new Intent(Intent.ACTION_VIEW);
            shortcutIntent.setDataAndType(path, "application/pdf");
            Intent addIntent = new Intent();
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, path1);
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                    Intent.ShortcutIconResource.fromContext(getActivity().getApplicationContext(),
                            R.drawable.pdf));
            addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            getActivity().getApplicationContext().sendBroadcast(addIntent);
            Toast.makeText(getActivity(), "Shortcut Added!", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getActivity(), "File doesn't exist!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addShortcutInOreo(String pdfName){
        File file = new File(Environment.getExternalStorageDirectory()+"/CDLU Mathematics Hub",pdfName+".pdf");

        try {
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(Uri.fromFile(file), "application/pdf");

            if (Build.VERSION.SDK_INT > 25) {
                ShortcutManager shortcutManager;
                shortcutManager = getActivity().getSystemService(ShortcutManager.class);

                ShortcutInfo shortcut = new ShortcutInfo.Builder(getActivity(), pdfName)
                        .setShortLabel(pdfName)
                        .setLongLabel(pdfName)
                        .setIcon(Icon.createWithResource(getActivity(), R.drawable.pdf))
                        .setIntent(pdfIntent)
                        .build();

                shortcutManager.requestPinShortcut(shortcut, null);
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), "Some error occurred: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isOreo() {
        return Build.VERSION.SDK_INT > 25;
    }

    public void openFile(String filename){
        File file = new File(Environment.getExternalStorageDirectory()+"/CDLU Mathematics Hub",
                filename);
        Uri path;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            path = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", file);
        }else {
            path = Uri.fromFile(file);
        }
        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW,path);
        pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pdfOpenintent.setDataAndType(path, "application/pdf");
        try {
            startActivity(pdfOpenintent);
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "No PDF reader installed. Please download from Play Store.", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList prepareData(){
        sharedPreferences = getActivity().getSharedPreferences("Pin", MODE_PRIVATE);
        ArrayList android_version = new ArrayList<>();
        for(int i=0;i<names.size();i++){
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(names.get(i));
            if (names.get(i).equals(getPinValue("0")) ||
                    names.get(i).equals(getPinValue("1")) ||
                    names.get(i).equals(getPinValue("2")) )
                androidVersion.setAndroid_image_url(R.drawable.pinoffline);
                else
                androidVersion.setAndroid_image_url(R.drawable.offline1);
            android_version.add(androidVersion);
        }
        return android_version;
    }
}