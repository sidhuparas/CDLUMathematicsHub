package com.parassidhu.cdlumaths.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.adapters.HomeAdapter;
import com.parassidhu.cdlumaths.models.AndroidVersion;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;

import java.util.ArrayList;

public class SyllabusUtils {

    private final static String add = "CDLU/syllabus/5year/";
    private final static String add2Years="CDLU/syllabus/2year/";

    // For 5 Years
    public static void downloadSyllabus(final Context context, final String pos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Start Download?")
                .setMessage("Do you want to download Semester " + pos + " syllabus?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (pos) {
                            case "1":
                                AppUtils.startDownload("Semester " + pos + ".pdf", 
                                        add + "1st%20Semester.pdf", context);
                                break;
                            case "2":
                                AppUtils.startDownload("Semester " + pos + ".pdf", 
                                        add + "2nd%20Semester.pdf", context);
                                break;
                            case "3":
                                AppUtils.startDownload("Semester " + pos + ".pdf", 
                                        add + "3rd%20Semester.pdf", context);
                                break;
                            case "7":
                                Sem7(context);
                                break;
                            case "8":
                                Sem8(context);
                                break;
                            case "9":
                                Sem9(context);
                                break;
                            case "10":
                                Sem10(context);
                                break;
                            default:
                                AppUtils.startDownload("Semester " + pos + ".pdf",
                                        add + pos + "th%20Semester.pdf", context);
                                break;
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    // A series of 4 functions specially for semesters 7-10
    // because they have multiple syllabi, one for CBCS
    // students and other for old students

    public static void Sem7(final Context context) {
        downloadOldNew(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppUtils.startDownload("Semester 7th.pdf",
                        add + "7th%20Semester.pdf", context);
                ;
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppUtils.startDownload("Semester 7th Old.pdf",
                        add + "7th%20SemesterO.pdf", context);
                ;
            }
        },context);
    }

    public static void Sem8(final Context context) {
        downloadOldNew(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppUtils.startDownload("Semester 8th.pdf",
                        add + "8th%20Semester.pdf", context);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppUtils.startDownload("Semester 8th Old.pdf",
                        add + "8th%20SemesterO.pdf", context);
            }
        },context);
    }

    public static void Sem9(final Context context) {
        downloadOldNew(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppUtils.startDownload("Semester 9th.pdf",
                        add + "9th%20Semester.pdf", context);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppUtils.startDownload("Semester 9th Old.pdf",
                        add + "9th%20SemesterO.pdf", context);
            }
        }, context);
    }

    public static void Sem10(final Context context) {
        downloadOldNew(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppUtils.startDownload("Semester 10th.pdf",
                        add + "10th%20Semester.pdf", context);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppUtils.startDownload("Semester 10th Old.pdf",
                        add + "10th%20SemesterO.pdf", context);
            }
        }, context);
    }

    // Above 4 methods call this
    public static void downloadOldNew(DialogInterface.OnClickListener latest,
                                      DialogInterface.OnClickListener old, final Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Start Download?")
                .setMessage("Do you want to download the new or old syllabus?")
                .setPositiveButton("Latest", latest)
                .setNegativeButton("Old", old).show();
    }

    // For 2 years
    public static void downloadSyllabus2Years(final Context context,final String pos){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Start Download?")
                .setMessage("Do you want to download Semester "+pos+" syllabus?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (pos){
                            case "1":
                                AppUtils.startDownload("Semester "+pos +" (2-Year).pdf",
                                        add2Years+"Semester%20-%20I.pdf",context);
                                break;
                            case "2":
                                AppUtils.startDownload("Semester "+pos +" (2-Year).pdf",
                                        add2Years+"Semester%20-%20II.pdf",context);
                                break;
                            case "3":
                                AppUtils.startDownload("Semester "+pos +" (2-Year).pdf",
                                        add2Years+"Semester%20-%20III.pdf",context);
                                break;
                            default:
                                AppUtils.startDownload("Semester "+pos +" (2-Year).pdf",
                                        add2Years+"Semester%20-%20IV.pdf",context);
                                break;
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}