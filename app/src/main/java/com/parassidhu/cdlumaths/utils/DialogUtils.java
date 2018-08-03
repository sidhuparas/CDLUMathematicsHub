package com.parassidhu.cdlumaths.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.widget.Toast;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.activities.Home;
import com.parassidhu.cdlumaths.adapters.DataAdapter;
import com.parassidhu.cdlumaths.models.OldItem;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class DialogUtils {

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
}
