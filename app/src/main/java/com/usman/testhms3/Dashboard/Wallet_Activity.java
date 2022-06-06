package com.usman.testhms3.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.usman.testhms3.Dashboard.My_Data.Transiction_Adapter;
import com.usman.testhms3.Dashboard.My_Data.Transiction_Model;
import com.usman.testhms3.R;

public class Wallet_Activity extends AppCompatActivity {

    TextView total_bal,errorText,wallet_name;
    RecyclerView trans_rec;
    ImageView refresh;
    ProgressBar prog2255,pbar11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_wallet );
        getSupportActionBar().hide();
        Intent in = getIntent();
        String id = in.getStringExtra( "id" );
        String name1 = in.getStringExtra( "name" );
        final String URL = "https://thedawg.000webhostapp.com/API-Android/getTransictions.php?sid="+id;
        final String URL_AMOUNT = "https://thedawg.000webhostapp.com/API-Android/getAmount.php?sid="+id;

        total_bal= findViewById( R.id.total_bal );
        trans_rec = findViewById( R.id.trans_rec );
        prog2255 = findViewById( R.id.prog2255 );
        errorText = findViewById( R.id.errorText );
        wallet_name = findViewById( R.id.wallet_name );
        pbar11=findViewById( R.id.pbar11 );
        wallet_name.setText( name1 );
        refresh=findViewById( R.id.refresh );
        getAmount(total_bal,URL_AMOUNT,pbar11);

        refresh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prog2255.setVisibility( View.VISIBLE );
                trans_rec.setLayoutManager( new LinearLayoutManager( Wallet_Activity.this) );
                StringRequest request = new StringRequest( URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Transiction_Model[] data = gson.fromJson( response,Transiction_Model[].class );
                        errorText.setVisibility( View.GONE );
                        prog2255.setVisibility( View.GONE );
                        trans_rec.setAdapter( new Transiction_Adapter( data,getApplicationContext()) );

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (error instanceof TimeoutError) {
                            Toast.makeText( Wallet_Activity.this, "Connection timed out Please Retry", Toast.LENGTH_LONG ).show();
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText( Wallet_Activity.this, "No Connection Please Retry", Toast.LENGTH_LONG ).show();
                        } else if (error instanceof AuthFailureError) { // 403's fall in this category
                            Toast.makeText( Wallet_Activity.this, "Auth Connection Please Retry", Toast.LENGTH_LONG ).show();
                        } else if (error instanceof ServerError) { // 404's fall in this category
                            Toast.makeText( Wallet_Activity.this, "Request not found Please Retry", Toast.LENGTH_LONG ).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText( Wallet_Activity.this, "Some network error occurred Please Retry", Toast.LENGTH_LONG ).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText( Wallet_Activity.this, "Unknown error occurred Please Retry", Toast.LENGTH_LONG ).show();
                        }else{
                            Toast.makeText( Wallet_Activity.this, "Please Retry", Toast.LENGTH_LONG ).show();
                        }

                    }
                } );
                RequestQueue queue = Volley.newRequestQueue( Wallet_Activity.this );
                queue.add( request );
            }
        });
    }
    //oncreate ends here

    private void getAmount(TextView tv,String url,ProgressBar p1)
    {
        tv.setVisibility( View.INVISIBLE );
        p1.setVisibility( View.VISIBLE );
        StringRequest request1 = new StringRequest( url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                tv.setVisibility( View.VISIBLE );
                p1.setVisibility( View.INVISIBLE );
                if(response==null){
                    tv.setText( "₹ 0" );
                }
                else if(response.equals("NA")){
                    Toast.makeText( Wallet_Activity.this, "An Error Occured ", Toast.LENGTH_SHORT ).show();
                    tv.setText("₹ 0");
                }
                else{
                    tv.setText("₹ "+response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                tv.setVisibility( View.VISIBLE );
                p1.setVisibility( View.INVISIBLE );
                if (error instanceof TimeoutError) {
                    Toast.makeText( Wallet_Activity.this, "Connection timed out Please Retry", Toast.LENGTH_LONG ).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText( Wallet_Activity.this, "No Connection Please Retry", Toast.LENGTH_LONG ).show();
                } else if (error instanceof AuthFailureError) { // 403's fall in this category
                    Toast.makeText( Wallet_Activity.this, "Auth Connection Please Retry", Toast.LENGTH_LONG ).show();
                } else if (error instanceof ServerError) { // 404's fall in this category
                    Toast.makeText( Wallet_Activity.this, "Request not found Please Retry", Toast.LENGTH_LONG ).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText( Wallet_Activity.this, "Some network error occurred Please Retry", Toast.LENGTH_LONG ).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText( Wallet_Activity.this, "Unknown error occurred Please Retry", Toast.LENGTH_LONG ).show();
                }else{
                    Toast.makeText( Wallet_Activity.this, "Please Retry", Toast.LENGTH_LONG ).show();
                }

            }
        } );
        RequestQueue queue1 = Volley.newRequestQueue( Wallet_Activity.this );
        queue1.add( request1 );

    }
}