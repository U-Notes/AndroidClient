package com.example.unotes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @author witer330
 * @date 2024/01/10
 */
public class ThemeTreeDao extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDB";

    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_ID = "_id";
    public static final String THEME_TABLE_NAME = "theme_tree";
    public static final String THEME_TABLE_ID = "_t_id";//主题库
    public static final String THEME_SUBZONE_TABLE_ID = "_ts_id";//主题
    public static final String SORT_TABLE_ID = "_so_id";//分类
    public static final String SUJECT_TABLE_ID = "_su_id";//科目
    public static final String THEME_TABLE_TITLE = "t_title";
    public static final String THEME_SUBZONE_TABLE_TITLE = "ts_title";
    public static final String SORT_TABLE_TITLE = "so_title";
    public static final String SUJECT_TABLE_TITLE = "su_title";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATA_TIME = "updata_time";

    public ThemeTreeDao(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tabs table
        db.execSQL("CREATE TABLE " + THEME_TABLE_NAME + " (" +
                TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                THEME_TABLE_ID + " INTEGER , " +
                THEME_TABLE_TITLE + " TEXT, " +
                THEME_SUBZONE_TABLE_ID + " INTEGER, " +
                THEME_SUBZONE_TABLE_TITLE + " TEXT, " +
                SORT_TABLE_ID + " INTEGER, " +
                SUJECT_TABLE_ID + " TEXT, " +
                SORT_TABLE_TITLE + " INTEGER, " +
                SUJECT_TABLE_TITLE + " TEXT, " +
                CREATE_TIME + " TIMESTAMP, " +
                UPDATA_TIME + " TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
