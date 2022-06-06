package com.usman.testhms3.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.usman.testhms3.Dashboard.My_Data.Profile_Model;
import com.usman.testhms3.HMS_Utils.NetworkChkDashboard;
import com.usman.testhms3.Login_Activity;
import com.usman.testhms3.R;

public class Dashboard_student_Activity extends AppCompatActivity {

    ImageView logout1,sProfileImage;
    TextView sName,sRollNumber,sHostel,sRoom;
    ConstraintLayout s_profile,s_wallet,s_notifications,s_meal_chart,s_help,s_settings_tab;
    String id1;
    ProgressBar progressBar2;
    ConstraintLayout mainLayout;
    String IMAGEURL = "https://thedawg.000webhostapp.com/images/";
    NetworkChkDashboard networkChkDashboard;
    Dialog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dashboard_student );
        getSupportActionBar().hide();
        SharedPreferences sp = getSharedPreferences( "data",MODE_PRIVATE );
        mainLayout=findViewById( R.id.mainLayout );
        networkChkDashboard = new NetworkChkDashboard( mainLayout );
        progressBar2=findViewById( R.id.progressBar2 );
        progressBar2.setVisibility( View.VISIBLE );
        if (sp.contains( "uname" )){
            id1 = sp.getString( "uname","" );
        }
        String URL = "https://thedawg.000webhostapp.com/API-Android/getStudentDetails.php?id="+id1 ;

        //details
        sName= findViewById( R.id.sName );
        sRollNumber= findViewById( R.id.sRollNumber );
        sHostel= findViewById( R.id.sHostel );
        sRoom= findViewById( R.id.sRoom );
        sProfileImage=findViewById( R.id.sProfileImage );

        StringRequest stringRequest = new StringRequest( URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.e( "me",response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Profile_Model data[] = gson.fromJson( response, Profile_Model[].class );
                progressBar2.setVisibility( View.INVISIBLE );
                sName.setText( data[0].getS_name() );
                sRollNumber.setText( data[0].getS_rollno() );
                String hostel_details = data[0].getS_hostal_details();

                if(hostel_details.equals("NA")){
                    sHostel.setText("Hostel: NA");
                    sRoom.setText( "Room : NA");
                }
                else{
                    String res[]=hostel_details.split( "@" );
                    sHostel.setText("Hostel: "+ res[0] );
                    sRoom.setText( "Room: "+ res[1]);
                }
                Glide.with(sName.getContext()).load( IMAGEURL+data[0].getS_image()).into( sProfileImage );

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make( mainLayout,"No internet Please Try Again!", Snackbar.LENGTH_LONG )
                        .setAction( "RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onRestart();
                            }
                        } );
                snackbar.setActionTextColor( Color.RED ).show();
            }
        } );
        RequestQueue queue = Volley.newRequestQueue( getApplicationContext() );
        queue.add( stringRequest );


        //events
        s_profile= findViewById( R.id.s_profile );
        s_wallet= findViewById( R.id.s_wallet );
        s_notifications= findViewById( R.id.s_notifications );
        s_meal_chart= findViewById( R.id.s_meal_chart );
        s_help= findViewById( R.id.s_help );
        s_settings_tab= findViewById( R.id.s_setting_tab );



//        wallet
        s_wallet.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Dashboard_student_Activity.this, Wallet_Activity.class );
                intent1.putExtra( "id",id1 );
                intent1.putExtra( "name",sName.getText().toString().trim() );
                startActivity( intent1 );
            }
        } );
//        notification
        s_notifications.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Dashboard_student_Activity.this, Notification_board.class );
                intent1.putExtra( "id",id1 );
                startActivity( intent1 );
            }
        } );
//        meal cahrt
        s_meal_chart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Dashboard_student_Activity.this, Meal_chart.class );
                intent1.putExtra( "id",id1 );
                startActivity( intent1 );
            }
        } );
//        help
        s_help.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Dashboard_student_Activity.this, Help_activity.class );
                intent1.putExtra( "id",id1 );
                startActivity( intent1 );
            }
        } );
//        Settings
        s_settings_tab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Dashboard_student_Activity.this, Settings_Activity.class );
                intent1.putExtra( "id",id1 );
                startActivity( intent1 );
            }
        } );
        s_profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Dashboard_student_Activity.this, Student_profile.class );
                intent1.putExtra( "id",id1 );
                startActivity( intent1 );

            }
        } );

        //logout
        logout1= findViewById( R.id.logout1 );
        logout1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1=new Dialog( Dashboard_student_Activity.this );
                dialog1.setContentView( R.layout.custom_logout_dialog );
                dialog1.setCancelable( true );
                dialog1.getWindow().getAttributes().windowAnimations=R.style.animation1;
                Button logoutbtn = dialog1.findViewById( R.id.logoutbtn );
                Button canclebtn = dialog1.findViewById( R.id.canclebtn );
                logoutbtn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //logout
                        SharedPreferences.Editor editor = sp.edit();
                        editor.remove( "uname" );
                        editor.commit();
                        startActivity( new Intent(getApplicationContext(), Login_Activity.class) );
                        finish();
                        Toast.makeText( Dashboard_student_Activity.this, "Logged out Successfully", Toast.LENGTH_SHORT ).show();
                        dialog1.dismiss();
                    }
                } );
                canclebtn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                } );
                dialog1.show();




            }
        } );
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver( networkChkDashboard,filter );
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver( networkChkDashboard );
        super.onStop();
    }
}