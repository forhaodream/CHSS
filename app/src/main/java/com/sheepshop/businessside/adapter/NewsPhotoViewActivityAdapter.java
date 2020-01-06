package com.sheepshop.businessside.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.sheepshop.businessside.utils.ImageUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by ${dongjunshuai} on 2017/6/13.
 */

public class NewsPhotoViewActivityAdapter extends PagerAdapter {

    private List<String> imageUrls;
    private AppCompatActivity activity;

    public NewsPhotoViewActivityAdapter(List<String> imageUrls, AppCompatActivity activity) {
        this.imageUrls = imageUrls;
        this.activity = activity;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = imageUrls.get(position);
        try {
            String encode = URLEncoder.encode(url, "UTF-8");
            url = encode.replace("%3A", ":").replace("%2F", "/");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        PhotoView photoView = new PhotoView(activity);
        if (url.endsWith("gif")) {
            ImageUtils.loadGifImage(activity, url, photoView);
        } else {
            ImageUtils.loadNormalImage(activity, url, photoView);
        }
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                activity.finish();
            }
        });
        container.addView(photoView);
        return photoView;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
