package com.usman.testhms3.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.usman.testhms3.R;

public class Help_activity extends AppCompatActivity {

    LinearLayout to_cell,to_email,to_phone;
    TextView tvPhone,tvCell,tvEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_help );

        tvCell=findViewById( R.id.tvCell );
        tvPhone=findViewById( R.id.tvPhone );
        tvEmail=findViewById( R.id.tvEmail );

        to_cell=findViewById( R.id.to_cell );
        to_email=findViewById( R.id.to_email );
        to_phone=findViewById( R.id.to_phone );

        to_email.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:"+tvEmail.getText().toString()+"?subject=" + Uri.encode("") + "&body=" + Uri.encode(""));
                intent1.setData(data);
                startActivity(intent1);
            }
        } );
        to_phone.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+tvPhone.getText().toString()));
                startActivity(callIntent);
            }
        } );
        to_cell.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_VIEW);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                intent2.setData(Uri.parse("http://"+tvCell.getText().toString()));
                startActivity(intent2);
            }
        } );
    }
}




