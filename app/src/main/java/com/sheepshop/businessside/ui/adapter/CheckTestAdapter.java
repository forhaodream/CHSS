package com.sheepshop.businessside.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sheepshop.businessside.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by CH
 * on 2019/11/6 14:26
 */
public class CheckTestAdapter extends Activity implements View.OnClickListener {

    private RecyclerView mRvView;
    private TextView mAll;
    private TextView mCancel;
    private TextView mInvert;
    private CheckAdapter checkAdapter;
    private List<String> lists;
    private List<String> listDatas = new ArrayList<>();
    private TextView mGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        lists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            lists.add("000" + i);
        }

        initView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvView.setLayoutManager(linearLayoutManager);
        checkAdapter = new CheckAdapter(this, lists);
        mRvView.setAdapter(checkAdapter);

        //子条目监听
        checkAdapter.setItemClickListener(new RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(CheckTestAdapter.this, "If you are happy - " + position, Toast.LENGTH_SHORT).show();
                //设置选中的项
                checkAdapter.setSelectItem(position);
            }
        });
    }

    private void initView() {
        mRvView = findViewById(R.id.rv_view);
        mAll = findViewById(R.id.tv_all);
        mCancel = findViewById(R.id.tv_cancel);
        mInvert = findViewById(R.id.tv_invert);
        mGetData = findViewById(R.id.tv_getData);
        mAll.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mInvert.setOnClickListener(this);
        mGetData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_getData:
                String content = "";
                listDatas.clear();

                Toast.makeText(CheckTestAdapter.this, "获取我们选取的数据", Toast.LENGTH_SHORT).show();
                Log.e("TAG", mGetData.getText().toString());

                Map<Integer, Boolean> map = checkAdapter.getMap();
                for (int i = 0; i < lists.size(); i++) {
                    if (map.get(i)) {
                        listDatas.add(lists.get(i));
                    }
                }

                //这里是为了测试我们的结果 ，可忽略！
                for (int j = 0; j < listDatas.size(); j++) {
                    Log.e("TAG", listDatas.get(j));
                    content += listDatas.get(j);
                }
                mGetData.setText(content);
                Log.e("TAG", content);
                break;

            //以下三个功能后续扩展
            case R.id.tv_all:
                Toast.makeText(CheckTestAdapter.this, "全选", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_cancel:
                Toast.makeText(CheckTestAdapter.this, "取消全选", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_invert:
                Toast.makeText(CheckTestAdapter.this, "反选", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    //Adapter数据填充
    class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.CheckViewHolder> {
        private Context mContext;
        private List<String> lists;
        private HashMap<Integer, Boolean> Maps = new HashMap<Integer, Boolean>();
        private HashMap<Integer, Boolean> AllMaps = new HashMap<Integer, Boolean>();
        public RecyclerViewOnItemClickListener onItemClickListener;

        //成员方法，初始化checkBox的状态，默认全部不选中
        public CheckAdapter(Context context, List<String> lists) {
            this.mContext = context;
            this.lists = lists;
            initMap();
        }

        //初始化map内的数据状态，全部重置为false，即为选取状态
        private void initMap() {
            for (int i = 0; i < lists.size(); i++) {
                Maps.put(i, false);
            }
        }


        //获取最终的map存储数据
        public Map<Integer, Boolean> getMap() {
            return Maps;
        }

        //后续扩展 - 获取最终的map存储数据
        public Map<Integer, Boolean> getAllMap() {
            return AllMaps;
        }

        //点击item选中CheckBox
        public void setSelectItem(int position) {
            //对当前状态取反
            if (Maps.get(position)) {
                Maps.put(position, false);
            } else {
                Maps.put(position, true);
            }
            notifyItemChanged(position);
        }

        @Override
        public CheckAdapter.CheckViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CheckViewHolder checkViewHolder = new CheckViewHolder(LayoutInflater.from(CheckTestAdapter.this).inflate(R.layout.item_add_voucher, parent, false), onItemClickListener);
            return checkViewHolder;
        }

        @Override
        public void onBindViewHolder(CheckAdapter.CheckViewHolder holder, final int position) {


            holder.mName.setText(lists.get(position));

            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Maps.put(position, isChecked);
                }
            });

            if (Maps.get(position) == null) {
                Maps.put(position, false);
            }
            //没有设置tag之前会有item重复选框出现，设置tag之后，此问题解决
            holder.mCheckBox.setChecked(Maps.get(position));


            //之后扩展使用
            AllMaps.put(position, true);
        }


        @Override
        public int getItemCount() {
            return lists == null ? 0 : lists.size();
        }

        public void setItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        class CheckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private RecyclerViewOnItemClickListener mListener;
            private TextView mName;
            private CheckBox mCheckBox;

            public CheckViewHolder(View itemView, RecyclerViewOnItemClickListener onItemClickListener) {
                super(itemView);
                this.mListener = onItemClickListener;
                itemView.setOnClickListener(this);
                mName = itemView.findViewById(R.id.item_add_voucher_title);
                mCheckBox = itemView.findViewById(R.id.item_add_voucher_check);
            }

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClickListener(v, getPosition());
                }
            }
        }

    }

    public interface RecyclerViewOnItemClickListener {
        void onItemClickListener(View view, int position);
    }

}
