package com.parassidhu.cdlumaths.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.adapters.DataAdapter;
import com.parassidhu.cdlumaths.models.OldItem;
import com.parassidhu.cdlumaths.models.Pair;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.DialogUtils;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;
import com.parassidhu.cdlumaths.utils.PrefsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class Offline extends Fragment {

    public static final String OFFLINE_SORTING = "offlinesorting";
    public static final String PIN = "Pin";
    public static final String APP_FOLDER = Environment.getExternalStorageDirectory() + "/CDLU Mathematics Hub";
    public static final String root = Environment.getExternalStorageDirectory().toString();
    public static final String path = root + "/CDLU Mathematics Hub";
    private final String[] sortItems = {"Name", "Date Created"};
    private final String nothing = "nothing";

    private DataAdapter adapter;
    private SharedPreferences sharedPreferences;
    private ArrayList<String> names;
    private ArrayList<OldItem> oldItems;
    private int chosenSortItem;
    private String one, two, three;
    private Boolean remove = true;

    private ActionMode actionMode;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();

    @BindView(R.id.nofile) TextView fileErrorTV;
    @BindView(R.id.offlinerecycle) RecyclerView rcl;
    @BindView(R.id.sortBtn) Button sortBtn;
    @BindView(R.id.theme) Button theme;

    public Offline() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().setTitle("Offline");

        initViews();
    }

    private void initViews() {
        // Whether to show error message or show files
        try {
            fileErrorTV.setVisibility(View.GONE);
            reloadData();
        } catch (Exception ex) {
            fileErrorTV.setVisibility(View.VISIBLE);
            sortBtn.setVisibility(View.GONE);
            theme.setVisibility(View.GONE);
        }
    }

    // Gets list of names according to the sort preference
    public ArrayList<String> getFileNamesW(File[] file) {
        PrefsUtils.initialize(getActivity(), OFFLINE_SORTING);
        int sort = PrefsUtils.getIntValue(OFFLINE_SORTING, 0);

        ArrayList<String> arrayFiles = new ArrayList<>();

        // If sort is by Name
        if (sort == 0) {
            if (file.length == 0)
                return null;
            else {
                for (File aFile : file) {
                    String fileName = aFile.getName(); //Full-name
                    String newName = fileName.substring(0, fileName.lastIndexOf(".")); //Name without extension
                    arrayFiles.add(newName);
                }
            }
            Collections.sort(arrayFiles);

        } else { //If sort is by Date-Modified

            Pair[] pairs = new Pair[file.length];
            for (int i = 0; i < file.length; i++) {
                pairs[i] = new Pair(file[i]);
            }

            Arrays.sort(pairs);
            for (int i = 0; i < file.length; i++) {
                file[i] = pairs[i].getF();
                String fileName = file[i].getName();

                String newName = fileName.substring(0, fileName.lastIndexOf("."));
                arrayFiles.add(newName);
            }

            Collections.reverse(arrayFiles);
        }

        checkIfFileIsPinned(file, arrayFiles, "2");

        checkIfFileIsPinned(file, arrayFiles, "1");

        checkIfFileIsPinned(file, arrayFiles, "0");

        return arrayFiles;
    }

    // Iterates through the files to retrieve pinned files
    private void checkIfFileIsPinned(File[] file, ArrayList<String> arrayFiles, String s) {
        for (int i = 0; i < file.length; i++) {
            if (arrayFiles.get(i).equals(getPinValue(s))) {
                String ithval = arrayFiles.get(i);
                arrayFiles.remove(i);
                arrayFiles.add(0, ithval);
                break;
            }
        }
    }

    private void reloadData() {
        File f = new File(path);
        File file[] = f.listFiles();
        names = getFileNamesW(file);
        setup();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort);
        AppUtils.setOptVisibility(menu, false, true);
    }

    private String giveName() {
        PrefsUtils.initialize(getActivity(), PIN);
        one = PrefsUtils.getValue("0", nothing);
        two = PrefsUtils.getValue("1", nothing);
        three = PrefsUtils.getValue("2", nothing);
        if (one.equals(nothing))
            return "0";
        if (two.equals(nothing))
            return "1";
        if (three.equals(nothing))
            return "2";
        return nothing;
    }

    private String getPinValue(String s) {
        PrefsUtils.initialize(getActivity(), PIN);
        return PrefsUtils.getValue(s, nothing);
    }

    private void setPinValue(String s, String p) {
        PrefsUtils.initialize(getActivity(), PIN);
        PrefsUtils.saveOffline(s, p);
    }

    private void shiftPinValue(String s) {
        PrefsUtils.initialize(getActivity(), PIN);
        one = PrefsUtils.getValue("0", nothing);
        two = PrefsUtils.getValue("1", nothing);
        three = PrefsUtils.getValue("2", nothing);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (s) {
            case "0":
                setPinValue("0", two);
                setPinValue("1", three);
                setPinValue("2", nothing);
                break;
            case "1":
                setPinValue("0", one);
                setPinValue("1", three);
                setPinValue("2", nothing);
                break;
            case "2":
                setPinValue("0", one);
                setPinValue("1", two);
                setPinValue("2", nothing);
                break;
        }
        editor.apply();
    }

    private void PinItNow(String s) {
        PrefsUtils.initialize(getActivity(), PIN);
        one = PrefsUtils.getValue("0", nothing);
        two = PrefsUtils.getValue("1", nothing);
        three = PrefsUtils.getValue("2", nothing);

        PrefsUtils.saveOffline("0", s);
        PrefsUtils.saveOffline("1", one);
        PrefsUtils.saveOffline("2", two);

        reloadData();
    }

    public void showSheet(final View v, final ArrayList<OldItem> oldItems, final TextView textView, final int position) {
        int pinMenu = R.menu.offline_pin_list;

        if (textView.getText().toString().equals(getPinValue("0")) ||
                textView.getText().toString().equals(getPinValue("1")) ||
                textView.getText().toString().equals(getPinValue("2")))
            pinMenu = R.menu.offline_unpin_list;

        new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog)
                .sheet(pinMenu)
                .title(textView.getText().toString())
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.PinTo:
                                menuPinTo(textView);
                                break;
                            case R.id.Unpin:
                                menuUnpin(textView, oldItems, position);
                                break;
                            case R.id.delete1:
                                deleteFile(oldItems, position, textView, v);
                                break;
                            case R.id.share:

                                menuShare(textView);
                                break;

                            case R.id.addtohome:

                                if (!isOreo()) {
                                    addShortcutPreOreo(textView.getText().toString());
                                } else {
                                    addShortcutInOreo(textView.getText().toString());
                                }

                                break;
                            case R.id.rename:

                                if (AppUtils.checkPerm(getActivity()))
                                    renameFile(textView.getText().toString(), position, oldItems);

                                break;
                        }
                    }
                }).show();
    }

    private void deleteFile(ArrayList<OldItem> oldItems, int position, final TextView textView, View v) {
        try {
            oldItems.remove(position);
            adapter.notifyItemRemoved(position);
            final String itemText = textView.getText().toString();
            final Handler mHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message message) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (remove) {
                                actuallyDeleteFile(itemText);
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

            Snackbar.make(v, itemText + " deleted successfully!", 2000)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            remove = false;
                            try {
                                reloadData();
                            } catch (Exception a) {
                                rcl.setVisibility(View.GONE);
                                fileErrorTV.setVisibility(View.VISIBLE);
                                getActivity().setTitle("Offline");
                            }
                        }
                    }).show();
        } catch (Exception ex) {
        }
    }

    // Delete file instantly without any Undo option
    private void actuallyDeleteFile(String itemText) {
        File file = new File(root + "/CDLU Mathematics Hub/" +
                itemText + ".pdf");
        if (file.exists()) {
            file.delete();
        }
    }

    private void menuShare(TextView textView) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        File file1 = new File(root + "/CDLU Mathematics Hub/" + textView.getText().toString() + ".pdf");
        Uri sharePath;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sharePath = FileProvider.getUriForFile(getActivity(),
                    getActivity().getApplicationContext().getPackageName() + ".provider", file1);
        } else {
            sharePath = Uri.fromFile(file1);
        }

        sendIntent.putExtra(Intent.EXTRA_STREAM, sharePath);
        sendIntent.setType("application/pdf");
        startActivity(Intent.createChooser(sendIntent, "Share " + textView.getText().toString()));
    }

    private void menuUnpin(TextView textView, ArrayList<OldItem> oldItems, int position) {
        if (getPinValue("0").equals(textView.getText().toString())) {
            setPinValue("0", nothing);
            oldItems.remove(position);
            adapter.notifyItemRemoved(position);
            shiftPinValue("0");
        }
        if (getPinValue("1").equals(textView.getText().toString())) {
            setPinValue("1", nothing);
            oldItems.remove(position);
            adapter.notifyItemRemoved(position);
            shiftPinValue("1");
        }
        if (getPinValue("2").equals(textView.getText().toString())) {
            setPinValue("2", nothing);
            oldItems.remove(position);
            adapter.notifyItemRemoved(position);
            shiftPinValue("2");
        }
        reloadData();
    }

    private void menuPinTo(TextView textView) {
        sharedPreferences = getActivity().getSharedPreferences(PIN, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!giveName().equals(nothing))
            PinItNow(textView.getText().toString());
        else
            Toast.makeText(getActivity(), "Maximum 3 files can be pinned at a time. Please unpin one of them to pin this."
                    , Toast.LENGTH_LONG).show();

        editor.apply();
    }

    private void renameFile(final String name, final int position, final ArrayList<OldItem> list) {
        final File file = new File(APP_FOLDER,
                name + ".pdf");

        DialogUtils.showRenameDialog(getActivity(), name, position, list, file, names, adapter);
    }

    public void setup() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcl.setLayoutManager(layoutManager);

        oldItems = new ArrayList<>(prepareData());
        adapter = new DataAdapter(getActivity(), oldItems, true, this);
        rcl.setAdapter(adapter);

        //Added Dividers
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcl.getContext(),
                DividerItemDecoration.VERTICAL);
        rcl.addItemDecoration(dividerItemDecoration);

        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (actionMode != null) {
                    toggleSelection(position);
                } else {
                    AppUtils.openFile(getActivity(), names.get(position) + ".pdf");
                }
            }
        });

        ItemClickSupport.addTo(rcl).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(final RecyclerView recyclerView, final int position, final View v) {
                if (actionMode == null) {
                    actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
                }

                toggleSelection(position);
                return true;
            }
        });

        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showSortDialog(getActivity(), sortItems, getTag());
            }
        });

        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showThemeDialog(getActivity());
            }
        });
    }

    private void addShortcutPreOreo(String path1) {
        File file = new File(APP_FOLDER, path1 + ".pdf");
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
        } else {
            Toast.makeText(getActivity(), "File doesn't exist!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addShortcutInOreo(String pdfName) {
        File file = new File(APP_FOLDER, pdfName + ".pdf");

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
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Some error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isOreo() {
        return Build.VERSION.SDK_INT > 25;
    }

    private ArrayList<OldItem> prepareData() {
        PrefsUtils.initialize(getActivity(), PIN);
        ArrayList<OldItem> items = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            OldItem oldItem = new OldItem();
            oldItem.setName(names.get(i));
            if (names.get(i).equals(getPinValue("0")) ||
                    names.get(i).equals(getPinValue("1")) ||
                    names.get(i).equals(getPinValue("2")))
                oldItem.setImage_url(R.drawable.ic_pin_top);
            else
                oldItem.setImage_url(R.drawable.ic_offline);
            items.add(oldItem);
        }

        return items;
    }

    private void toggleSelection(int position) {
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @SuppressWarnings("unused")
        private final String TAG = ActionModeCallback.class.getSimpleName();

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:
                    List<Integer> listOfSelectedItems = adapter.getSelectedItems();

                    int numberOfDeletedFiles = 0;
                    // Delete files and update arraylist
                    for (Integer i:listOfSelectedItems){
                        String name = oldItems.get(i - numberOfDeletedFiles).getName();
                        actuallyDeleteFile(name);

                        oldItems.remove(i - numberOfDeletedFiles);
                        numberOfDeletedFiles++;
                        Log.d(TAG, "onActionItemClicked: " + name);
                    }

                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Successfully deleted the files!", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelection();
            actionMode = null;
        }
    }
}