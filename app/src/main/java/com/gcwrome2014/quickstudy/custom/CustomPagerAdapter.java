package com.gcwrome2014.quickstudy.custom;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gcwrome2014.quickstudy.R;


/**
 * Created by Alessio on 22/03/2015.
 */
public class CustomPagerAdapter extends PagerAdapter {
    Context context;
    int[] imageId = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};

    public CustomPagerAdapter(Context context){
        this.context = context;

    }

    @Override
    public int getCount() {
        return imageId.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.image_item, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);
        imageView.setImageResource(imageId[position]);
        ((ViewPager)container).addView(viewItem);

        return viewItem;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }
}
