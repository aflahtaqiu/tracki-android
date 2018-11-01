package com.example.aflah.tracki_master.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aflah.tracki_master.DetailTokoActivity;
import com.example.aflah.tracki_master.Model.Advertisement;
import com.example.aflah.tracki_master.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarouselHomeAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.logo, R.drawable.img_menu_default, R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
    private List<String> urlList;
    private List<Advertisement> advertisements;

    public CarouselHomeAdapter(Context context, List<Advertisement> advertisements) {
        this.context = context;
        this.advertisements = advertisements;
    }

    @Override
    public int getCount() {
        return advertisements.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_carousel_home, null);
        ImageView imageView_carousel = (ImageView) view.findViewById(R.id.iv_carousel_home);

        Picasso.get().load(advertisements.get(position).getBanner()).fit().into(imageView_carousel);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), DetailTokoActivity.class);
                intent.putExtra("idTokoTerdekat", advertisements.get(position).getStore_id());
                context.startActivity(intent);
            }
        });

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
