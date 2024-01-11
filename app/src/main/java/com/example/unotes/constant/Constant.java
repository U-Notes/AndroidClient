package com.example.unotes.constant;

/**
 * 常数
 *
 * @author witer
 * @date 2023/08/02
 */
public class Constant {
    //启动相机标识
    public static final int TAKE_PHOTO = 1;
    //启动相册标识
    public static final int SELECT_PHOTO = 2;
    //权限请求上限
    public static final int PERMISSION_REQUEST_CODE = 100;
    //是否成功登录的凭证
    public static final int LOGIN_STATU_ON = 0;
    public static final int LOGIN_STATU_OFF = 1;
    public static final String DATABASE_NAME = "myDB";

    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_ID = "_id";
    public static final String THEME_TABLE_NAME = "theme_tree";
    public static final String THEME_TYPE = "type";//{主题库,主题,分类,科目}
    public static final String THEME_TITLE = "title";
    public static final String THEME_LOCATION = "location";
    public static final String THEME_PARENT_ID = "parent_id";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATA_TIME = "updata_time";
    //    public static final String THEME_SUBZONE_TABLE_ID = "_ts_id";
    //    public static final String SORT_TABLE_ID = "_so_id";
    //    public static final String SUJECT_TABLE_ID = "_su_id";
    //    public static final String THEME_SUBZONE_TABLE_TITLE = "ts_title";
    //    public static final String SORT_TABLE_TITLE = "so_title";
    //    public static final String SUJECT_TABLE_TITLE = "su_title";
    //    public static final String THEME_SUBZONE_TABLE_LOCATION = "ts_location";
//    public static final String SORT_TABLE_LOCATION = "so_location";
//    public static final String SUJECT_TABLE_LOCATION= "su_location";

}
