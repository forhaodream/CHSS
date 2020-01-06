package com.sheepshop.businessside.weight;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sheepshop.businessside.R;


/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/01
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TitleBar extends RelativeLayout {

    private RelativeLayout mNaviTitleContent;
    private RelativeLayout view_title_bar_layout;
    private RelativeLayout mFrameLeft;
    private RelativeLayout mFrameRight;
    private Button mNaviButtonLeft;
    private TextView mNaviButtonRight;
    private TextView mNaviTitle,title2,tv_right;
    private ImageView mMiddleImage, mLeftImage, mRightImage, mRightImageTwo;
    private Activity mActivity;
    private View topView, bottomView;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public TitleBar(Context context) {
        super(context);
        initialize();
    }

    /**
     *
     */
    private void initialize() {
        inflate();
        findViews();
    }

    /**
     *
     */
    private void inflate() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_title_bar, this, true);
    }

    /**
     *
     */
    private void findViews() {
        topView = findViewById(R.id.top_view);
        mNaviTitle = findViewById(R.id.title);
        title2 = findViewById(R.id.title2);
        view_title_bar_layout = findViewById(R.id.view_title_bar_layout);
        mNaviTitleContent = findViewById(R.id.titleContent);
        mNaviButtonLeft = findViewById(R.id.naviButtonLeft);
        mNaviButtonRight = findViewById(R.id.ButtonRight);
        mFrameLeft = findViewById(R.id.naviFrameLeft);
        mFrameRight = findViewById(R.id.naviFrameRight);
        tv_right = findViewById(R.id.tv_right);
        mMiddleImage = findViewById(R.id.imageView_titleContent);
        mLeftImage = findViewById(R.id.imageView_left_titlebar);
        mRightImage = findViewById(R.id.imageView_right_titleBar);
        mRightImageTwo = findViewById(R.id.imageView_right_titleBar_two);
        bottomView = findViewById(R.id.bottom_view);
    }

    public void goneBottomView() {
        bottomView.setVisibility(GONE);
    }

    public View getTopView() {
        return topView;
    }
    //设置头部颜色
    public void setTitleColor(int id){
        view_title_bar_layout.setBackgroundColor(getResources().getColor(id));
    }
    /**
     * @param res
     */
    public void setRightText(int res) {
        mNaviButtonRight.setText(res);
    }
    public void setRight2Text(CharSequence text) {
        tv_right.setText(text);
    }
    public void setRightText(CharSequence text) {
        mNaviButtonRight.setText(text);
    }

    //设置右侧文字颜色
    public void setRightTextColor(int color) {
        mNaviButtonRight.setTextColor(color);
    }

    /**
     * @param res
     */
    public void setTitleText(int res) {
        mNaviTitle.setText(res);
    }

    public void setTitleText(SpannableString textSpan) {
        title2.setVisibility(View.GONE);
        mNaviTitle.setText(textSpan);
    }
    /**
     * @param text
     */
    public void setTitleText(String text) {
        mNaviTitle.setText(text);
    }
    public void setTitle2(){
        title2.setVisibility(View.VISIBLE);
    }
    public void setTitle2text(String txt){
        title2.setVisibility(View.VISIBLE);
        title2.setText(txt);
    }
    public void setTitleTextColor(int color) {
        mNaviTitle.setTextColor(color);
    }

    public void setMiddleImageResource(int res) {
        mMiddleImage.setImageResource(res);
    }

    public void setRightImageResource(int res) {
        mRightImage.setImageResource(res);
    }

    /**
     * 设置右侧图片的宽高
     *
     * @param w 宽 dp
     * @param h 高 dp
     */
    public void setRightImageResourceSize(int w, int h) {
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mRightImage.getLayoutParams();
        layoutParams.width = (int) (0.5f + w * Resources.getSystem().getDisplayMetrics().density);
        layoutParams.height = (int) (0.5f + h * Resources.getSystem().getDisplayMetrics().density);
        mRightImage.setLayoutParams(layoutParams);
    }

    public void setLeftImageResource(int res) {
        mLeftImage.setImageResource(res);
    }

    /**
     * 设置左侧图片的宽高
     *
     * @param w 宽 dp
     * @param h 高 dp
     */
    public void setLeftImageResourceSize(int w, int h) {
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mLeftImage.getLayoutParams();
        layoutParams.width = (int) (0.5f + w * Resources.getSystem().getDisplayMetrics().density);
        layoutParams.height = (int) (0.5f + h * Resources.getSystem().getDisplayMetrics().density);
        mLeftImage.setLayoutParams(layoutParams);
    }

    /**
     *
     */
    public void hideLeft() {
        mNaviButtonLeft.setVisibility(View.INVISIBLE);
        mLeftImage.setVisibility(INVISIBLE);
    }

    /**
     *
     */
    public void showTitle() {
        mNaviTitle.setVisibility(View.VISIBLE);
    }

    /**
     * @param l
     */
    public void setLeftClick(OnClickListener l) {
        mFrameLeft.setOnClickListener(l);
    }

    /**
     * @param l
     */
    public void setRightClick(OnClickListener l) {
        mFrameRight.setOnClickListener(l);
    }

    /**
     * @param l
     */
    public void setTitleClick(OnClickListener l) {
        mNaviTitle.setOnClickListener(l);
    }

    public void setLeftImageClick(OnClickListener l) {
        mLeftImage.setOnClickListener(l);
    }

    public void setRightImageClick(OnClickListener l) {
        mRightImage.setOnClickListener(l);
    }
    public void setRightTextClick(OnClickListener l){
        mNaviButtonRight.setOnClickListener(l);
    }
    public void setRightText2Click(OnClickListener l){
        tv_right.setOnClickListener(l);
    }
    public void enableLeftImageBackOnClick() {
        if (mFrameLeft != null)
            mFrameLeft.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    final Activity act = mActivity;
                    if (act != null)
                        act.finish();
                }
            });
    }

    public void setActivity(Activity act) {
        this.mActivity = act;
    }

}
