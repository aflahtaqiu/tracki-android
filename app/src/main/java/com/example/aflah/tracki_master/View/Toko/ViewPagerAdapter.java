package com.example.aflah.tracki_master.View.Toko;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aflah.tracki_master.R;


/**
 * Created by Ennobel on 9/19/2018.
 */

public class ViewPagerAdapter extends PagerAdapter {


    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] image = {android.R.drawable.spinner_background,android.R.drawable.alert_light_frame,android.R.drawable.dialog_frame};


    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view =layoutInflater.inflate(R.layout.custom_layout,null);
        ImageView imageView =   (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(image[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp =(ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
