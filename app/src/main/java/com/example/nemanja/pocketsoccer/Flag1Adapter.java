package com.example.nemanja.pocketsoccer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Flag1Adapter  extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    int pozicija = 0;

    public Flag1Adapter(Context context, int pozicija){
        this.context = context;
        this.pozicija = pozicija;
    }






    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.flag_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.flag_image);


        slideImageView.setImageResource(pozicija);



        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
