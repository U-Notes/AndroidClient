package com.example.unotes.database;

import static com.example.unotes.constant.SqliteName.PAGERTABLENAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.example.unotes.bean.Pager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WTL
 * @date 2023/11/03
 */
public class PagerSqlite extends SQLiteOpenHelper {
    public static final String createPager = "CREATE TABLE IF NOT EXISTS " +
            PAGERTABLENAME +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "pagerName text," +
            "pagerNum INTEGER," +
            "createdTime INTERGER," +
            "updateTime INTEGER)";

    public PagerSqlite(@Nullable Context context) {
        super(context, "unotes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createPager);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 插入数据
     *
     * @param pager 请注意
     * @return {@link Long}
     */
    public Long insertData(Pager pager) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pagerName", pager.getPagerName());
        values.put("pagerNum", pager.getPagerNum());
        values.put("create_time", pager.getCreateTime().getTime());
        return db.insert(PAGERTABLENAME, null, values);
    }

    public List<Pager> queryAllFromDB() {
        SQLiteDatabase db = getWritableDatabase();
        List<Pager> PagerList = new ArrayList<>();
        Cursor cursor = db.query(PAGERTABLENAME, null, null, null, null, null, "create_time DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("pagerName"));
                @SuppressLint("Range") int num = cursor.getInt(cursor.getColumnIndex("pagerNum"));
                @SuppressLint("Range") Long createTimestamp = cursor.getLong(cursor.getColumnIndex("create_time"));
                @SuppressLint("Range") Long updateTimestamp = cursor.getLong(cursor.getColumnIndex("update_time"));
                Pager pager = Pager.builder().id(id).pagerName(name).pagerNum(num).createTime(new Date(createTimestamp)).updateTime(new Date(updateTimestamp)).build();
                PagerList.add(pager);
            }
        }
        cursor.close();
        db.close();
        return PagerList;
    }

    public int updateDate(Pager Pager) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pagerName", Pager.getPagerName());
        values.put("pagerNum", Pager.getPagerNum());
        values.put("update_time", Pager.getCreateTime().getTime());
        return db.update(PAGERTABLENAME, values, "id = ?", new String[]{String.valueOf(Pager.getId())});
    }

    public int deleteDataFromid(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(PAGERTABLENAME, "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Pager> quearyFromDbByTitle(String name) {
        if (TextUtils.isEmpty(name)) {        //判空，不输入则查询所有
            return queryAllFromDB();
        }
        SQLiteDatabase db = getWritableDatabase();
        List<Pager> PagerList = new ArrayList<>();
        Cursor cursor = db.query(PAGERTABLENAME, null, "pagerName like ?", new String[]{"%" + name + "%"}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name1 = cursor.getString(cursor.getColumnIndex("pagerName"));
                @SuppressLint("Range") int pagerNum1 = cursor.getInt(cursor.getColumnIndex("pagerNum"));
                @SuppressLint("Range") Long createTimestamp = cursor.getLong(cursor.getColumnIndex("create_time"));
                @SuppressLint("Range") Long updateTimestamp = cursor.getLong(cursor.getColumnIndex("update_time"));
                Pager pager = Pager.builder().id(id).pagerName(name1).pagerNum(pagerNum1).createTime(new Date(createTimestamp)).updateTime(new Date(updateTimestamp)).build();
                PagerList.add(pager);
            }
        }
        cursor.close();
        db.close();
        return PagerList;
    }
}
