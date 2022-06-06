package com.usman.testhms3.HMS_Utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

public class NetworkChkDashboard extends BroadcastReceiver {

    View main;

    public NetworkChkDashboard(View main) {
        this.main = main;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!com.usman.testhms3.HMS_Utils.Common.isConnectedToInternet( context )){
            Snackbar snackbar = Snackbar.make( main,"No internet Please Try Again!", Snackbar.LENGTH_INDEFINITE )
                    .setAction( "RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            context.startActivity( intent );

                        }
                    } );
            snackbar.setActionTextColor( Color.RED ).show();
        }
    }
}
