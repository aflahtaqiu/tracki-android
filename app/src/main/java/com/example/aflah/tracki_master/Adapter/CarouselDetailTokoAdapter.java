package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarouselDetailTokoAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.logo, R.drawable.img_menu_default, R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
    private List<String> urlList;

    public CarouselDetailTokoAdapter(Context context, List<String> urlList) {
        this.context = context;
        this.urlList = urlList;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_carousel_detailtoko, null);
//        ImageView imageView_carousel = (ImageView) view.findViewById(R.id.iv_carousel_detail_toko);
//        imageView_carousel.setImageResource(images[position]);

        Picasso.get().load(urlList.get(position)).fit().into((ImageView) view.findViewById(R.id.iv_carousel_detail_toko));

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
