package com.sheepshop.businessside.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.sheepshop.businessside.entity.CityEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class CityDao {

    private Context context;
    public CityDao(Context context) {
        this.context=context;
    }
//获得全部的省
    public List<CityEntity> getAllShen() {
        CityDBManager  mCityDBManager = new CityDBManager(context);
        mCityDBManager.openDatabase();
        SQLiteDatabase mSQLiteDatabase= mCityDBManager.getDatabase();
        List<CityEntity> mCityEntities=null;
        if (mCityEntities == null) {
            mCityEntities = new ArrayList<>();
            Cursor cursor = mSQLiteDatabase.rawQuery("select * from area where parent_id=?", new String[]{"100000"});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    CityEntity cityEntity=new CityEntity();
                    cityEntity.setName(cursor.getString(cursor.getColumnIndex("region_name")));
                    if("钓鱼岛".equals(cursor.getString(cursor.getColumnIndex("region_name")))){
                        continue;
                    }
                    cityEntity.setId(cursor.getString(cursor.getColumnIndex("id")));
                    cityEntity.setParent_id(cursor.getInt(cursor.getColumnIndex("parent_id")));
                    mCityEntities.add(cityEntity);
                }
            }
            cursor.close();
            mSQLiteDatabase.close();
        }
        return mCityEntities;
    }
    //获得全部的市
    public List<CityEntity> getAllShi(String parent_id){
        CityDBManager  mCityDBManager = new CityDBManager(context);
        mCityDBManager.openDatabase();
        SQLiteDatabase mSQLiteDatabase= mCityDBManager.getDatabase();
        List<CityEntity> mCityEntities=null;
        if (mCityEntities == null) {
            mCityEntities = new ArrayList<>();
            Cursor cursor = mSQLiteDatabase.rawQuery("select * from area where parent_id=?", new String[]{parent_id});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    CityEntity cityEntity=new CityEntity();
                    cityEntity.setName(cursor.getString(cursor.getColumnIndex("region_name")));
                    cityEntity.setId(cursor.getString(cursor.getColumnIndex("id")));
                    cityEntity.setParent_id(cursor.getInt(cursor.getColumnIndex("parent_id")));
                    mCityEntities.add(cityEntity);
                }
            }
            cursor.close();
            mSQLiteDatabase.close();
        }
        return mCityEntities;
    }
}
