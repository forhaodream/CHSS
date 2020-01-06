package com.sheepshop.businessside.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.ui.myshop.ShopManagementActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by CH
 * on 2019/12/17 09:30
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<Object> objectList = new ArrayList<>();
    private Context context;

    public ImageAdapter(Context context, List<Object> objectList) {
        this.objectList = objectList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(objectList.get(position)).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new XPopup.Builder(holder.itemView.getContext()).asImageViewer(holder.img, position, objectList, new OnSrcViewUpdateListener() {
//                    @Override
//                    public void onSrcViewUpdate(ImageViewerPopupView view, int pos) {
//                        RecyclerView rv = (RecyclerView) holder.itemView.getParent();
//                        view.updateSrcView((ImageView) rv.getChildAt(pos));
//                    }
//                }, new ImageLoader()).isShowSaveButton(false).show();
                new XPopup.Builder(context).asImageViewer(holder.img, objectList.get(position), new ImageLoader()).isShowSaveButton(false).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_image_img);
        }
    }

    class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object uri, @NonNull ImageView imageView) {
            Glide.with(imageView).load(uri).into(imageView);
        }

        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {
            return null;
        }

        //必须实现这个方法，返回uri对应的缓存文件，可参照下面的实现，内部保存图片会用到。如果你不需要保存图片这个功能，可以返回null。

    }
}
