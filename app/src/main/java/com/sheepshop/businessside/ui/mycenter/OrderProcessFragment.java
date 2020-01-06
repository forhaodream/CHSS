package com.sheepshop.businessside.ui.mycenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.EvaluateBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.utils.RecyclerAdapter;

/**
 * Created by CH
 * on 2019/12/12 16:11
 */
public class OrderProcessFragment extends Fragment {
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_evaluate, null);

        return view;
    }
}