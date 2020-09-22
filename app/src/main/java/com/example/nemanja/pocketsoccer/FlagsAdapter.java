package com.example.nemanja.pocketsoccer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FlagsAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    int pozicija = 0;

    public FlagsAdapter(Context context){
        this.context = context;
    }

    public int[] flag_images = {
            R.drawable.t0,
            R.drawable.t1,
            R.drawable.t2,
            R.drawable.t3,
            R.drawable.t4,
            R.drawable.t5,
            R.drawable.t6,
            R.drawable.t7,
            R.drawable.t8,
            R.drawable.t9,
            R.drawable.t10,
            R.drawable.t11,
            R.drawable.t12,
            R.drawable.t13,
            R.drawable.t14,
            R.drawable.t18,
            R.drawable.t26,
    };




    @Override
    public int getCount() {
        return flag_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    public int getDrawable(){
        return flag_images[pozicija];
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.flag_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.flag_image);


        slideImageView.setImageResource(flag_images[position]);
        pozicija = position;


        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

    public int getSlideImage(int position){
        return flag_images[position];
    }

    public int getPositionImage(int id){
        for(int i = 0; i< flag_images.length ;i++){
            if (flag_images[i] == id){
                return i;
            }
        }
        return 0;
    }
}
