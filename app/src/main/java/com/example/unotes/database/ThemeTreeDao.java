package com.example.unotes.database;


import static com.example.unotes.constant.Constant.CREATE_TIME;
import static com.example.unotes.constant.Constant.DATABASE_NAME;
import static com.example.unotes.constant.Constant.DATABASE_VERSION;
import static com.example.unotes.constant.Constant.TABLE_ID;
import static com.example.unotes.constant.Constant.THEME_LOCATION;
import static com.example.unotes.constant.Constant.THEME_PARENT_ID;
import static com.example.unotes.constant.Constant.THEME_TABLE_NAME;
import static com.example.unotes.constant.Constant.THEME_TITLE;
import static com.example.unotes.constant.Constant.THEME_TYPE;
import static com.example.unotes.constant.Constant.UPDATA_TIME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.unotes.bean.Theme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author witer330
 * @date 2024/01/10
 * @parma THEME_TYPE 主题类型
 * @parma THEME_TITLE 主题标题名称
 * @parma THEME_LOCATION 主题所在位置
 * @parma THEME_PARENT_ID 主题关联父ID
 * @parma CREATE_TIME 创建时间
 * @parma UPDATA_TIME 修改时间
 */
public class ThemeTreeDao extends SQLiteOpenHelper {
    public static final String createsql = "CREATE TABLE IF NOT EXISTS " +
            THEME_TABLE_NAME + " (" +
            TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            THEME_TYPE + " TEXT , " +
            THEME_TITLE + " TEXT, " +
            THEME_LOCATION + " INTEGER, " +
            THEME_PARENT_ID + " INTEGER, " +
            CREATE_TIME + " TIMESTAMP, " +
            UPDATA_TIME + " TIMESTAMP)";

    public ThemeTreeDao(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tabs table
        db.execSQL(createsql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*
     * 删除数据
     * */
    public void deleteDataByid(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(THEME_TABLE_NAME, TABLE_ID + " =?", new String[]{String.valueOf(id)});
        db.close();
    }

    /*
     * 修改数据
     * */
    public void updateData(Theme theme) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(THEME_TYPE, theme.getType());
        values.put(THEME_TITLE, theme.getTitle());
        values.put(THEME_LOCATION, theme.getLocation());
        values.put(THEME_PARENT_ID, theme.getPartentId());
        values.put(UPDATA_TIME, theme.getUpdateTime().getTime());
        db.update(THEME_TABLE_NAME, values, TABLE_ID + " =?", new String[]{String.valueOf(theme.getId())});
        db.close();
    }

    /*
     * 插入数据
     * */
    public void insertData(Theme theme) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(THEME_TYPE, theme.getType());
        values.put(THEME_TITLE, theme.getTitle());
        values.put(THEME_LOCATION, theme.getLocation());
        values.put(THEME_PARENT_ID, theme.getPartentId());
        values.put(UPDATA_TIME, theme.getUpdateTime().getTime());
        values.put(CREATE_TIME, theme.getCreateTime().getTime());
        db.insert(THEME_TABLE_NAME, null, values);
        db.close();
    }

    /*
     * 通过传入的参数进行查询
     **/
    @SuppressLint({"Recycle", "Range"})
    public List<Theme> querDataBy(Object params) {
        List<Theme> themeList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if (params instanceof String) {
            String selectQueryByTitle = "SELECT " + THEME_TITLE + "FROM " + THEME_TABLE_NAME;
            cursor = db.rawQuery(selectQueryByTitle, null);
        } else if (params instanceof Integer) {
            String selectQuery = "SELECT " + THEME_LOCATION + "FROM " + THEME_TABLE_NAME;
            cursor = db.rawQuery(selectQuery, null);
        }
        assert cursor != null;
        if (cursor.moveToFirst()) {
            do {
                Theme themeData = new Theme();
                themeData.setId(cursor.getInt(cursor.getColumnIndex(TABLE_ID)));
                themeData.setType(cursor.getString(cursor.getColumnIndex(THEME_TYPE)));
                themeData.setTitle(cursor.getString(cursor.getColumnIndex(THEME_TITLE)));
                themeData.setLocation(cursor.getInt(cursor.getColumnIndex(THEME_LOCATION)));
                themeData.setPartentId(cursor.getInt(cursor.getColumnIndex(THEME_PARENT_ID)));
                // Convert long to Date
                long createTimeMillis = cursor.getLong(cursor.getColumnIndex(CREATE_TIME));
                long updateTimeMillis = cursor.getLong(cursor.getColumnIndex(UPDATA_TIME));
                themeData.setCreateTime(new Date(createTimeMillis));
                themeData.setUpdateTime(new Date(updateTimeMillis));

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return themeList;
    }

    /*
     * 直接查询
     * */
    @SuppressLint("Range")
    public List<Theme> querData() {
        List<Theme> themeList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + THEME_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Theme themeData = new Theme();
                themeData.setId(cursor.getInt(cursor.getColumnIndex(TABLE_ID)));
                themeData.setType(cursor.getString(cursor.getColumnIndex(THEME_TYPE)));
                themeData.setTitle(cursor.getString(cursor.getColumnIndex(THEME_TITLE)));
                themeData.setLocation(cursor.getInt(cursor.getColumnIndex(THEME_LOCATION)));
                themeData.setPartentId(cursor.getInt(cursor.getColumnIndex(THEME_PARENT_ID)));
                // Convert long to Date
                long createTimeMillis = cursor.getLong(cursor.getColumnIndex(CREATE_TIME));
                long updateTimeMillis = cursor.getLong(cursor.getColumnIndex(UPDATA_TIME));
                themeData.setCreateTime(new Date(createTimeMillis));
                themeData.setUpdateTime(new Date(updateTimeMillis));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return themeList;
    }
}
