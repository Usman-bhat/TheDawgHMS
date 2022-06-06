package com.usman.testhms3.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.usman.testhms3.Dashboard.My_Data.Profile_Model;
import com.usman.testhms3.HMS_Utils.NetworkChkDashboard;
import com.usman.testhms3.R;

public class Student_profile extends AppCompatActivity {

    TextView p_full_name, p_roll_number, p_semister, p_branch, p_email, p_phone_number, p_parentage;
    ProgressBar progressBar;
    ImageView p_image;
    String IMAGEURL = "https://thedawg.000webhostapp.com/images/";
    LinearLayout main2;
    NetworkChkDashboard networkChkDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_student_profile );
        main2=findViewById( R.id.main2 );
        networkChkDashboard = new NetworkChkDashboard(main2);
        progressBar = findViewById( R.id.progressBar );
        progressBar.setVisibility( View.VISIBLE );
        Intent in = getIntent();
        String id = in.getStringExtra( "id" );
        String URL = "https://thedawg.000webhostapp.com/API-Android/getStudentDetails.php?id=" + id;

        StringRequest stringRequest = new StringRequest( URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Profile_Model data[] = gson.fromJson( response, Profile_Model[].class );
                progressBar.setVisibility( View.INVISIBLE );
                p_full_name = findViewById( R.id.p_full_name );
                p_roll_number = findViewById( R.id.p_roll_number );
                p_semister = findViewById( R.id.p_semister );
                p_branch = findViewById( R.id.p_branch );
                p_email = findViewById( R.id.p_email );
                p_phone_number = findViewById( R.id.p_phone_number );
                p_parentage = findViewById( R.id.p_parentage );
                p_image = findViewById( R.id.p_image );


                p_full_name.setText( data[0].getS_name() );
                p_roll_number.setText( data[0].getS_rollno() );
                p_semister.setText( data[0].getS_batch() );
                p_branch.setText( data[0].getS_branch() );
                p_email.setText( data[0].getS_email() );
                p_phone_number.setText( data[0].getS_phone() );
                p_parentage.setText( data[0].getS_parentage() );
                Glide.with( p_image.getContext() ).load( IMAGEURL + data[0].getS_image() ).into( p_image );


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "me", error.toString() );

//                if (error instanceof TimeoutError) {
//                    showDialog( getApplicationContext(), "Connection TimedOut " );
//                    Toast.makeText( getApplicationContext(), error.toString(), Toast.LENGTH_SHORT ).show();
//                } else if (error instanceof NoConnectionError) {
//                    showDialog( getApplicationContext(), "No Internet Connection " );
//                    Toast.makeText( getApplicationContext(), "No Internet Connection ", Toast.LENGTH_SHORT ).show();
//
//                } else if (error instanceof AuthFailureError) {
//                    showDialog( getApplicationContext(), error.toString() );
//                    Toast.makeText( getApplicationContext(), "" + error.toString(), Toast.LENGTH_SHORT ).show();
//                } else if (error instanceof ServerError) {
//                    Toast.makeText( getApplicationContext(), "" + error.toString(), Toast.LENGTH_SHORT ).show();
//                } else if (error instanceof NetworkError) {
//                    showDialog( getApplicationContext(), "Network Error" );
//                    Toast.makeText( getApplicationContext(), error.toString(), Toast.LENGTH_SHORT ).show();
//                } else if (error instanceof ParseError) {
//                    Toast.makeText( getApplicationContext(), "" + error.toString(), Toast.LENGTH_SHORT ).show();
//                } else {
//                    showDialog( getApplicationContext(), error.toString() );
//                    Toast.makeText( getApplicationContext(), error.toString(), Toast.LENGTH_SHORT ).show();
//                }

            }
        } );
        RequestQueue queue = Volley.newRequestQueue( getApplicationContext() );
        queue.add( stringRequest );
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


//    private void showDialog(Context context, String error) {
//        AlertDialog.Builder builder = new AlertDialog.Builder( context );
//        View layout_dialog = LayoutInflater.from( context ).inflate( R.layout.no_internet_dialog, null );
//        builder.setView( layout_dialog );
//
//        AppCompatButton btnRetry = layout_dialog.findViewById( R.id.retrybtn );
//        TextView error_text = layout_dialog.findViewById( R.id.error_volly );
//        error_text.setText( error );
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//        dialog.setCancelable( false );
//
//        dialog.getWindow().setGravity( Gravity.CENTER );
//
//        btnRetry.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        } );
//    }
}
