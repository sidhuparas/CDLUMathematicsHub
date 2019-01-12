package com.parassidhu.cdlumaths.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Environment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.activities.Home;
import com.parassidhu.cdlumaths.adapters.DataAdapter;
import com.parassidhu.cdlumaths.fragments.Offline;
import com.parassidhu.cdlumaths.models.OldItem;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.parassidhu.cdlumaths.fragments.Offline.OFFLINE_SORTING;

public class DialogUtils {

    public static int chosenSortItem;

    public static void showRenameDialog(final Context context, String name, final int position, final ArrayList<OldItem> list,
                                 final File file, final ArrayList<String> names, final DataAdapter adapter) {
        new LovelyTextInputDialog(context)
                .setTopColorRes(R.color.blue)
                .setIcon(R.drawable.menu_rename)
                .setInitialInput(name)
                .setTopTitle("Rename File")
                .setTopTitleColor(R.color.white)
                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        if (!text.isEmpty()) {
                            File renamed = new File(Environment.getExternalStorageDirectory() + "/CDLU Mathematics Hub",
                                    text + ".pdf");
                            if (file.renameTo(renamed)) {
                                list.get(position).setName(text);
                                names.set(position, text);
                                adapter.notifyItemChanged(position);
                                Toast.makeText(context,
                                        "File renamed Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .show();
    }

    public static void showThemeDialog(final Context context) {
        String[] list = {"Green", "Red", "Orange", "Blue", "Pink", "Dark Blue"};
        ArrayList<String> defaultList = new ArrayList<>(Arrays.asList(list));

        new LovelyChoiceDialog(context)
                .setTitle("Select Your Color")
                .setItems(defaultList, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(int position, String item) {
                        int a = 110, b = 110, c = 0, d = 0, e = 0, f = 0;
                        switch (position) {
                            case 0:
                                a = 76;
                                b = 175;
                                c = 80;
                                d = 56;
                                e = 142;
                                f = 60;
                                break;
                            case 1:
                                a = 244;
                                b = 67;
                                c = 54;
                                d = 211;
                                e = 47;
                                f = 47;
                                break;
                            case 2:
                                a = 255;
                                b = 152;
                                c = 0;
                                d = 245;
                                e = 124;
                                f = 0;
                                break;
                            case 3:
                                a = 3;
                                b = 169;
                                c = 244;
                                d = 2;
                                e = 136;
                                f = 209;
                                break;
                            case 4:
                                //Pink
                                a = 255;
                                b = 128;
                                c = 171;
                                d = 201;
                                e = 79;
                                f = 124;
                                break;
                            case 5:
                                //Dark Blue
                                a = 1;
                                b = 87;
                                c = 155;
                                d = 0;
                                e = 47;
                                f = 108;
                                break;
                        }

                        PrefsUtils.initialize(context, "colors");
                        PrefsUtils.saveOffline("r", a);
                        PrefsUtils.saveOffline("g", b);
                        PrefsUtils.saveOffline("b", c);
                        PrefsUtils.saveOffline("e", d);
                        PrefsUtils.saveOffline("f", e);
                        PrefsUtils.saveOffline("v", f);

                        Toast.makeText(context, "The changes will be applied once you restart the app!"
                                , Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(R.drawable.ic_theme)
                .setTopColor(Color.rgb(Home.r, Home.g, Home.b))
                .show();
    }

    public static void showSortDialog(final Context context, final String[] sortItems,
                                      final String TAG){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Sort Offline Files")
                .setSingleChoiceItems(sortItems, getPosition(context), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chosenSortItem = i;
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PrefsUtils.initialize(context, OFFLINE_SORTING);

                        if (sortItems[chosenSortItem].equals("Name")) {
                            PrefsUtils.saveOffline(OFFLINE_SORTING, 0);
                        } else {
                            PrefsUtils.saveOffline(OFFLINE_SORTING, 1);
                        }

                        try {
                            if (TAG.equals("Offline")) {
                                Fragment fragment = new Offline();
                                FragmentTransaction ft = ((AppCompatActivity)context)
                                        .getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, fragment, "Offline");
                                ft.commit();
                            }
                        } catch (Exception ignored) { }
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static int getPosition(Context context) {
        PrefsUtils.initialize(context, OFFLINE_SORTING);
        return PrefsUtils.getIntValue(OFFLINE_SORTING, 0);
    }

    /* Used to show any standard dialog with
            TITLE and
            MESSAGE */

    public static void MsgBox(Context context, String title, String message) {
        new LovelyStandardDialog(context)
                .setTopColorRes(com.parassidhu.cdlumaths.R.color.colorAccent)
                .setTitle(title)
                .setPositiveButton("Ok", null)
                .setMessage(message)
                .show();
    }

    /* Used to show any standard dialog with
            TITLE
            MESSAGE and
            ID */

    public static void MsgBox(Context context, String title, String msg, int id) {
        new LovelyInfoDialog(context)
                .setTopColorRes(R.color.blue)
                .setIcon(R.drawable.ic_info)
                .setTitle(title)
                .setMessage(msg)
                .show();
    }

    /* Used to show permission dialog
       Accepts MESSAGE,
               LISTENER */

    public static void permBox(Context context, String message, View.OnClickListener onClickListener) {
        new LovelyStandardDialog(context)
                .setTopColorRes(com.parassidhu.cdlumaths.R.color.colorAccent)
                .setTitle("Permissions Required!")
                .setPositiveButton("Grant Permissions", onClickListener)
                .setMessage(message)
                .show();
    }

     /* Used to show tip about app features
        Accepts MESSAGE only  */

    public static void tipMsg(Context context, String message, int id) {
        new LovelyInfoDialog(context)
                .setTopColorRes(com.parassidhu.cdlumaths.R.color.Orange)
                .setTitle("Did you know?")
                .setNotShowAgainOptionEnabled(id)
                .setMessage(message)
                .show();
    }

    public static void showNotifMessage(Context context, String message) {
        new LovelyInfoDialog(context)
                .setMessage(message)
                .setTitle("Notification")
                .setCancelable(false)
                .setIcon(R.drawable.ic_info)
                .setTopColorRes(R.color.blue)
                .show();
    }

    public static void welcomeReleaseNotes(Context context, String title, String msg, int id) {
        new LovelyInfoDialog(context)
                .setTopColorRes(R.color.holo_red_dark)
                .setIcon(R.drawable.ic_info)
                .setNotShowAgainOptionEnabled(id)
                .setNotShowAgainOptionChecked(true)
                .setTitle(title)
                .setMessage(msg)
                .show();
    }

}
