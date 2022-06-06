package com.usman.testhms3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.usman.testhms3.Dashboard.Dashboard_student_Activity;
import com.usman.testhms3.HMS_Utils.NetworkChangerListener;

public class MainActivity extends AppCompatActivity {

    Button register1,login1;
    NetworkChangerListener networkChangerListener = new NetworkChangerListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        getSupportActionBar().hide();
        SharedPreferences sp = getSharedPreferences( "data",MODE_PRIVATE );
        if (sp.contains( "uname" )){
            startActivity( new Intent(getApplicationContext(), Dashboard_student_Activity.class) );
            finish();
        }


        register1 = findViewById( R.id.register1 );
        login1 = findViewById( R.id.login1 );

        register1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),Register_Activity.class);
                startActivity( in );
            }
        } );
        login1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),Login_Activity.class);
                startActivity( in );
            }
        } );
    }


    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver( networkChangerListener,filter );
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver( networkChangerListener );
        super.onStop();
    }
}