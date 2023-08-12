package com.example.unotes.fragment;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.unotes.R;
import com.example.unotes.utils.BitmapUtils;
import com.example.unotes.utils.CameraUtils;
import com.example.unotes.utils.PermissionRequester;
import com.example.unotes.utils.SPUtils;
import com.example.unotes.utils.ToastUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.io.Console;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.unotes.constant.Constant.PERMISSION_REQUEST_CODE;
import static com.example.unotes.constant.Constant.SELECT_PHOTO;
import static com.example.unotes.constant.Constant.TAKE_PHOTO;

/**
 * 用户片段
 *
 * @author witer
 * @date 2023/07/29
 */
public class UserFragment extends Fragment {
    ToastUtils toastUtils;
    View v;
    //底部弹窗
    private BottomSheetDialog bottomSheetDialog;
    //弹窗视图
    private View bottomView;
    //权限请求
    private RxPermissions rxPermissions;
    //是否拥有权限
    private boolean hasPermissions = true;
    //存储拍完照后的图片
    private File outputImagePath;
    ShapeableImageView iv_userIcon;
    private String base64Pic;
    //拍照和相册获取图片的Bitmap
    private Bitmap orc_bitmap ;
    //Glide请求图片选项配置
    private PermissionRequester permissionRequester;
    String[] permissions = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(R.layout.userfragment, container, false);
            iv_userIcon = v.findViewById(R.id.iv_userIcon);
            permissionRequester = PermissionRequester.with(this, new PermissionRequester.PermissionCallback() {
                @Override
                public void onPermissionGranted() {

                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {

                }
            },PERMISSION_REQUEST_CODE);
            permissionRequester.request(permissions);


//            rxPermissions = new RxPermissions(getActivity());
            //取出缓存
            String imageUrl = SPUtils.getString("imageUrl",null,getActivity());
            if(imageUrl != null){
                Glide.with(this).load(imageUrl).apply(requestOptions).into(iv_userIcon);
            }

            iv_userIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    checkVersion();
                    changeAvatar();

                }
            });
        }
        return v;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionRequester.onRequestPermissionsResult(requestCode,permissions,grantResults);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //拍照后返回
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //显示图片
                    displayImage(outputImagePath.getAbsolutePath());
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    String imagePath = null;
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        //4.4及以上系统使用这个方法处理图片
                        imagePath = CameraUtils.getImageOnKitKatPath(data, getActivity());
                    } else {
                        imagePath = CameraUtils.getImageBeforeKitKatPath(data, getActivity());
                    }
                    //显示图片
                    displayImage(imagePath);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 通过图片路径显示图片
     */
    private void displayImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
            //显示图片
            Glide.with(this).load(imagePath).apply(requestOptions).into(iv_userIcon);
            //压缩图片
            orc_bitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));
            //转Base64
            base64Pic = BitmapUtils.bitmapToBase64(orc_bitmap);
            //本地缓存
            SPUtils.putString("imageUrl",imagePath,getActivity());
        } else {
            toastUtils.showToast(getContext(), "图片获取失败", 500);
        }
    }


    /**
     * 更换头像的dialog弹窗选项
     *
     * @param
     */
    public void changeAvatar() {
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        bottomSheetDialog.setContentView(bottomView);
//        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
        TextView tvTakePictures = bottomView.findViewById(R.id.tv_take_pictures);
        TextView tvOpenAlbum = bottomView.findViewById(R.id.tv_open_album);
        TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);

        //拍照
        tvTakePictures.setOnClickListener(v -> {
            takePhoto();
            toastUtils.showToast(getContext(), "拍照", 500);
            bottomSheetDialog.cancel();
        });
        //打开相册
        tvOpenAlbum.setOnClickListener(v -> {
            openAlbum();
            toastUtils.showToast(getContext(), "打开相册", 500);
            bottomSheetDialog.cancel();
        });
        //取消
        tvCancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
        bottomSheetDialog.show();
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        if (!hasPermissions) {
            toastUtils.showToast(getContext(), "未获取到权限", 500);
//            checkVersion();
            return;
        }
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        outputImagePath = new File(getActivity().getExternalCacheDir(),
                filename + ".jpg");
        Intent takePhotoIntent = CameraUtils.getTakePhotoIntent(getActivity(), outputImagePath);
        // 开启一个带有返回值的Activity，请求码为TAKE_PHOTO
        startActivityForResult(takePhotoIntent, TAKE_PHOTO);
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        if (!hasPermissions) {
            toastUtils.showToast(getContext(), "未获取到权限", 500);
//            checkVersion();
            return;
        }
        startActivityForResult(CameraUtils.getSelectPhotoIntent(), SELECT_PHOTO);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 检查版本

    private void checkVersion() {
        //Android6.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果你是在Fragment中，则把this换成getActivity()
//            rxPermissions = new RxPermissions(this);
            //权限请求

                rxPermissions
                        .request(Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(permission -> {
                            if (permission) {//申请成功
                                toastUtils.showToast(getContext(), "已获取权限", 500);
                                hasPermissions = true;

                            } else {//申请失败
                                toastUtils.showToast(getContext(), "权限未开启", 500);
                                hasPermissions = false;

                            }
                        }, e -> {
                            e.printStackTrace();
                        });

        } else {
            //Android6.0以下
            toastUtils.showToast(getContext(), "无需请求动态权限", 500);
        }
    }
*/
}
