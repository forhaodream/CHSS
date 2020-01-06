package com.sheepshop.businessside.ui.centersetting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sheepshop.businessside.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.base.BaseActivity;

/**
 * Created by CH
 * on 2020/1/6 13:13
 */
public class FeedbackActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private TagFlowLayout mFeedbackTag;
    private EditText mEdFeedbackContent;
    private TextView mNowCount;
    private RelativeLayout mLayoutEdit;
    private ImageView mImageFeedbackCamera;
    private RecyclerView mFeedbackPhotoRecycler;
    private Button mFeedbackCommit;
    private TagAdapter mTagAdapter;
    private FeedbackActivity me;
    private TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        me = this;
    }

    @Override
    public int initLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mFeedbackTag = findViewById(R.id.feedback_tag);
        mEdFeedbackContent = findViewById(R.id.ed_feedback_content);
        mNowCount = findViewById(R.id.now_count);
        mLayoutEdit = findViewById(R.id.layout_edit);
        mImageFeedbackCamera = findViewById(R.id.image_feedback_camera);
        mFeedbackPhotoRecycler = findViewById(R.id.feedback_photo_recycler);
        mFeedbackCommit = findViewById(R.id.feedback_commit);
        mTitleBack.setOnClickListener(this);
        mImageFeedbackCamera.setOnClickListener(this);
        mFeedbackCommit.setOnClickListener(this);
        LayoutInflater mInflater = LayoutInflater.from(me);
        List<String> lists = new ArrayList<>();
        lists.add("功能异常");
        lists.add("体验异常");
        lists.add("新功能建议");
        lists.add("其他");
        mFeedbackTag.setAdapter(mTagAdapter = new TagAdapter<String>(lists) {
            @Override
            public View getView(FlowLayout parent, int position, String user) {
                mText = (TextView) mInflater.inflate(R.layout.tv, mFeedbackTag, false);
                mText.setText(lists.get(position));
                return mText;
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.image_feedback_camera:
                break;
            case R.id.feedback_commit:
                feedbackComomit();
                break;
        }
    }

    private void feedbackComomit() {

    }
}
