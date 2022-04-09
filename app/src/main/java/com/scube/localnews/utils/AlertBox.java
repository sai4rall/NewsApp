package com.scube.localnews.utils;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.scube.localnews.R;


public class AlertBox {
   static String m_Text="";
    public static Dialog showAlertDialog(Context ctx, String msg) {
        final Dialog dialog = new Dialog(ctx, R.style.Theme_AppCompat_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.requestWindowFeature(WindowManager.LayoutParams.DIM_AMOUNT_CHANGED);
        dialog.setContentView(R.layout.custom_alert);
        dialog.setCancelable(false);
        dialog.findViewById(R.id.textMsg);
        dialog.getWindow().setLayout(860,420);
        TextView msgTv = (TextView) dialog.findViewById(R.id.textMsg);
        msgTv.setText(msg);
        //btnOk
        dialog.show();
        return dialog;
    }


    public static Dialog showAlertDismisableDialog(Context ctx, String msg) {
        final Dialog dialog = new Dialog(ctx, R.style.Theme_AppCompat_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.requestWindowFeature(WindowManager.LayoutParams.DIM_AMOUNT_CHANGED);
        dialog.setContentView(R.layout.custom_alert);
        dialog.setCancelable(false);
        dialog.findViewById(R.id.textMsg);
        dialog.getWindow().setLayout(550,220);
        TextView msgTv = (TextView) dialog.findViewById(R.id.textMsg);
        msgTv.setText(msg);
      Button btnok=(Button) dialog.findViewById(R.id.btnOk);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }



    public static Dialog showDismisableAlertDialog(Context ctx, String msg) {
        final Dialog dialog = new Dialog(ctx, R.style.Theme_AppCompat_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.requestWindowFeature(WindowManager.LayoutParams.DIM_AMOUNT_CHANGED);
        dialog.setContentView(R.layout.custom_alert);
        dialog.setCancelable(false);
        dialog.findViewById(R.id.textMsg);
        TextView msgTv = (TextView) dialog.findViewById(R.id.textMsg);
        msgTv.setText(msg);
        Button ok = (Button) dialog.findViewById(R.id.btnOk);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }
   public static ProgressDialog intPDialog(Context mcon) {
        ProgressDialog pDialog = new ProgressDialog(mcon,R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);
       return  pDialog;
    }

}
