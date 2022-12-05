package com.example.mobileproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slideImages = {
            R.drawable.abdullah_gaber,
            R.drawable.abdullah_abdalhalim,
            R.drawable.ali,
            R.drawable.amany,
            R.drawable.rawan,
            R.drawable.khalid,
            R.drawable.youssef,
            R.drawable.zeyad
    };

    public String[] slide_names = {
            "Abdalla Gaber Ashour " ,
            "Abdalla Abdalhalim",
            "Ali Amr Ali ",
            "Amany Gaber Okaila ",
            "Rawan Shawky Arafa ",
            "Khaled Nabawi Ahmed",
            "Yousef Maged Helal ",
            "Zeyad Abdo Ali"
    };

    public String[] slide_mails = {
            "abdallagaber323@gmail.com",
            "abdallaabdalhalem9@gmail.com",
            "aliamreducation@gmail.com",
            "amanygaber330@gmail.com",
            "rawanshawky66@gmail.com",
            "khalednabawi10@gmail.com",
            "youssef.maged237@gmail.com",
            "zeyad.abdo73@gmail.com"
    };

    public String[] slide_phones = {
            "01210324025",
            "01212743101",
            "01208310237",
            "01210369232",
            "01032510846",
            "01203045495",
            "01012150096",
            "01202913965"
    };


    @Override
    public int getCount() {
        return slide_names.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //layoutInflater = (layoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImage = (ImageView) view.findViewById(R.id.image);
        TextView slideName = (TextView) view.findViewById(R.id.name);
        TextView slideMail = (TextView) view.findViewById(R.id.mail);
        TextView slidePhone = (TextView) view.findViewById(R.id.phone);


        slideImage.setImageResource(slideImages[position]);
        slideName.setText(slide_names[position]);
        slideMail.setText(slide_mails[position]);
        slidePhone.setText(slide_phones[position]);

        container.addView(view);


        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}












