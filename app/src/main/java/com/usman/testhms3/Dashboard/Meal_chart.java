package com.usman.testhms3.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.usman.testhms3.R;

public class Meal_chart extends AppCompatActivity {

    ImageView imageMeal;
    String IMAGEURL = "https://thedawg.000webhostapp.com/images/other/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_meal_chart );

//        String url = getString( IMAGEURL );
        imageMeal= findViewById( R.id.imageMeal );
        Glide.with(imageMeal.getContext()).load( IMAGEURL+"MealChart.jpg").into( imageMeal );
    }
}