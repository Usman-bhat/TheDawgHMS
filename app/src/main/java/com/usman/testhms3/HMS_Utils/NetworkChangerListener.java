package com.usman.testhms3.HMS_Utils;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.usman.testhms3.R;

public class NetworkChangerListener extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!com.usman.testhms3.HMS_Utils.Common.isConnectedToInternet( context )){
            AlertDialog.Builder builder= new AlertDialog.Builder( context );
            View layout_dialog = LayoutInflater.from( context ).inflate( R.layout.no_internet_dialog,null);
            builder.setView( layout_dialog );

            AppCompatButton btnRetry = layout_dialog.findViewById( R.id.retrybtn );

            AlertDialog dialog  = builder.create();
            dialog.show();
            dialog.setCancelable( false );

            dialog.getWindow().setGravity( Gravity.CENTER );

            btnRetry.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    onReceive( context,intent );
                }
            } );

        }
    }
}
