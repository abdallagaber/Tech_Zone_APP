package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.slider.Slider;

public class MainOfContact extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_of_contact);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPaper);
        mDotLayout= (LinearLayout) findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(  this);
        mSlideViewPager.setAdapter (sliderAdapter);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.contactUs);

        addDotsIndicator();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.categories:
                        startActivity(new Intent(getApplicationContext(),categories.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),profile.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),cart.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.contactUs:
                        startActivity(new Intent(getApplicationContext(),MainOfContact.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }


    public void onBackPressed(){
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }
    public void addDotsIndicator() {
        mDots = new TextView[8];
        for(int i = 0; i < mDots.length; i++) {
            mDots [i] = new TextView( this);
            mDots [i].setText(Html.fromHtml(  "&#8226;"));
            mDots [i].setTextSize(35);
            mDots [i].setTextColor (getResources ().getColor (R. color.colorTransparentWhite));
            mDotLayout.addView(mDots [i]);
    }
    }
}