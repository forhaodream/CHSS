package com.sheepshop.businessside.ui.myshop;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.sheepshop.businessside.R;
import com.sheepshop.businessside.ui.adapter.HasChangeAdapter;

/**
 * Created by CH
 * on 2019/11/6 17:04
 */
public class ExpandlistActivity extends Activity {
    private ExpandableListView ex;
    //声明一个ExpandableListView 用的数据源
    private List<ExpandInfo> list = new ArrayList<ExpandInfo>();
    private HasChangeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandlist);
        ex = findViewById(R.id.expand);
        //初始化数据源
        initList();
        adapter = new HasChangeAdapter(ExpandlistActivity.this, list);
        ex.setAdapter(adapter);
        int groupCount = ex.getCount();

        for (int i = 0; i < groupCount; i++) {
            ex.expandGroup(i);
        }

        //ExpandableListView子条目点击事件
        ex.setOnChildClickListener(new OnChildClickListener() {

            @SuppressLint("WrongConstant")
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String str = ((ChildInfo) adapter.getChild(groupPosition, childPosition)).NickName;
                Toast.makeText(ExpandlistActivity.this, str, 0).show();
                return false;
            }
        });
    }

    //初始化数据源
    private void initList() {
        for (int i = 0; i < 6; i++) {
            //创建组对象
            ExpandInfo info = new ExpandInfo();
            //循环添加组名
            info.title = "ExpandGroup" + i;
            //创建子条目数据源
            List<ChildInfo> clist = new ArrayList<ChildInfo>();
            for (int j = 0; j < 10; j++) {
                //创建子对象
                ChildInfo childinfo = new ChildInfo();
                //循环添加用户头像和昵称
                childinfo.headID = R.mipmap.logo_shop;
                childinfo.NickName = "ExpandChild" + j;
                //将子对象添加到子数据源
                clist.add(childinfo);
            }
            //将子数据源赋值给组对象
            info.childList = clist;
            //将组对象添加到总数据源
            list.add(info);
        }
    }
}