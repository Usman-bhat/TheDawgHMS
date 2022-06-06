package com.usman.testhms3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.usman.testhms3.Dashboard.Dashboard_student_Activity;
import com.usman.testhms3.HMS_Utils.NetworkChangerListener;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {

    TextView gotoRegisterLogin,error1;
    Button btnLogin;
    ProgressBar progress4433;
    EditText s_username,s_password;

    NetworkChangerListener networkChangerListener = new NetworkChangerListener();
    public static final String LOGIN_URL = "https://thedawg.000webhostapp.com/API-Android/studentLogin.php?okay=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        getSupportActionBar().hide();

        gotoRegisterLogin = findViewById( R.id.gotoRegisterLogin );
        btnLogin = findViewById( R.id.btnLogin );

        s_username = findViewById( R.id.s_username );
        s_password = findViewById( R.id.s_password );
        error1 = findViewById( R.id.error1 );
        progress4433=findViewById( R.id.progress4433 );

        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setVisibility( View.INVISIBLE );
                progress4433.setVisibility( View.VISIBLE );
                loginProcess();
            }
        } );




        gotoRegisterLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),Register_Activity.class);
                startActivity( in );
            }
        } );
    }

    private void loginProcess() {

        String name = s_username.getText().toString().trim();
        String pass = s_password.getText().toString().trim();


        StringRequest request = new StringRequest( Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals( "true" )){
                    btnLogin.setVisibility( View.VISIBLE );
                    progress4433.setVisibility( View.INVISIBLE );
                    Intent intent = new Intent(Login_Activity.this, Dashboard_student_Activity.class);
                    SharedPreferences sp = getSharedPreferences( "data",MODE_PRIVATE );
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString( "uname",name );
                    editor.commit();
                    startActivity( intent );
                    finish();
                    Toast.makeText( Login_Activity.this, "Logged Successfully", Toast.LENGTH_SHORT ).show();
                }
                else if(response.equals( "false" )){
                    error1.setVisibility( View.VISIBLE );
                    error1.setText( "No User Found." );
                    s_username.setText( "" );
                    s_password.setText( "" );
                    btnLogin.setVisibility( View.VISIBLE );
                    progress4433.setVisibility( View.INVISIBLE );
                    Toast.makeText( Login_Activity.this, "Login failed", Toast.LENGTH_SHORT ).show();
                }
                else{
                    error1.setVisibility( View.VISIBLE );
                    error1.setText( "An Unknown error Occurred Please Try lalter" );
                    s_username.setText( "" );
                    s_password.setText( "" );
                    btnLogin.setVisibility( View.VISIBLE );
                    progress4433.setVisibility( View.INVISIBLE );
                    Toast.makeText( Login_Activity.this, "An Error Occured:"+response, Toast.LENGTH_SHORT ).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                btnLogin.setVisibility( View.VISIBLE );
                progress4433.setVisibility( View.INVISIBLE );
                Toast.makeText( Login_Activity.this, error.toString(), Toast.LENGTH_SHORT ).show();
            }
        } ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_name",name );
                map.put("pwd",pass );

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add( request );
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