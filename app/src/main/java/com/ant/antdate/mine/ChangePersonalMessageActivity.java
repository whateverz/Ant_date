package com.ant.antdate.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.ant.antdate.BuildConfig;
import com.ant.antdate.R;
import com.ant.antdate.activity.LoginActivity;
import com.ant.antdate.activity.MainActivity;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.bean.LoginInfo;
import com.ant.antdate.view.widget.dialog.ActionSheetDialog;
import com.bumptech.glide.Glide;

import java.io.File;

import lib.frame.module.http.HttpResult;
import lib.frame.module.ui.BindView;

public class ChangePersonalMessageActivity extends BaseActivity implements View.OnClickListener {
    private LoginInfo loginInfo;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.btn_btn2)
    Button btn_btn2;
    @BindView(R.id.realname_linl)
    LinearLayout realname_linl;
    @BindView(R.id.tv_realname)
    TextView tv_realname;
    @BindView(R.id.Nikename_linl)
    LinearLayout Nikename_linl;
    @BindView(R.id.nikename_tv)
    TextView nikename_tv;
    @BindView(R.id.old_linl)
    LinearLayout old_linl;
    @BindView(R.id.old_tv)
    TextView old_tv;
    @BindView(R.id.weight_linl)
    LinearLayout weight_linl;
    @BindView(R.id.weight_tv)
    TextView weight_tv;
    @BindView(R.id.hight_linl)
    LinearLayout height_linl;
    @BindView(R.id.hight_tv)
    TextView hight_tv;
    @BindView(R.id.ll_live)
    LinearLayout ll_live;
    @BindView(R.id.live_place)
    TextView live_place;
    @BindView(R.id.ll_hujidi)
    LinearLayout ll_hujidi;
    @BindView(R.id.tv_huji)
    TextView tv_huji;
    @BindView(R.id.ll_married)
    LinearLayout ll_married;
    @BindView(R.id.tv_ifmarried)
    TextView tv_ifmarried;
    @BindView(R.id.ll_education)
    LinearLayout ll_education;
    @BindView(R.id.tv_education)
    TextView tv_education;
    @BindView(R.id.ll_school)
    LinearLayout ll_school;
    @BindView(R.id.tv_school)
    TextView tv_school;
    @BindView(R.id.photo_linl)
    LinearLayout photo_linl;
    @BindView(R.id.my_photo)
    ImageView my_photo;

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private Bitmap photo;
    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.changepersonal;
    }

    @Override
    protected void initView() {
        super.initView();
        title_tv.setText("个人资料");
        btn_btn2.setVisibility(View.VISIBLE);
        back_iv.setOnClickListener(this::onClick);
        realname_linl.setOnClickListener(this::onClick);
        Nikename_linl.setOnClickListener(this::onClick);
        height_linl.setOnClickListener(this::onClick);
        weight_linl.setOnClickListener(this::onClick);
        old_linl.setOnClickListener(this::onClick);
        photo_linl.setOnClickListener(this::onClick);
    }


    @Override
    protected void loadData() {
        super.loadData();
    }

    @Override
    public <T> void onHttpCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {
        super.onHttpCallBack(resultType, reqId, resContent, reqObject, httpResult);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_btn2:
                commit();
                break;
            case R.id.realname_linl:
                dialogIntput(tv_realname,"真实姓名","请输入真实姓名");
                break;
            case R.id.Nikename_linl:
                dialogIntput(nikename_tv,"昵称","请输入昵称");
                break;
            case R.id.old_linl:
                dialogIntput(old_tv,"年龄","请输入年龄");
                break;
            case R.id.hight_linl:
                dialogIntput(hight_tv,"身高","请输入身高(cm)");
                break;
            case R.id.weight_linl:
                dialogIntput(weight_tv,"体重","请输入体重（kg）");
                break;
            case R.id.photo_linl:
               changeAvatar();
                break;
        }
    }

    private void changeAvatar() {
        new ActionSheetDialog(ChangePersonalMessageActivity.this)
                .builder()
                .setCancelable(true)
                .setCancelClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                    }
                })
                .setCanceledOnTouchOutside(true)
                .addSheetItem("查看大图", ActionSheetDialog.SheetItemColor.Black,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                /*查看大图*/
                                lookBigPhoto();
                            }
                        })
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Black,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //拍照
                                choseHeadImageFromCameraCapture();
                            }
                        })
                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.Black,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                                //相册
                                //album();
                                choseHeadImageFromGallery();
                            }
                        })
                .prepare().show();

    }

    private void lookBigPhoto() {

            String avatarurl ="";
            LayoutInflater inflater = LayoutInflater.from(this);
            View imgEntryView = inflater.inflate(R.layout.dialog_photo, null); // 加载自定义的布局文件
            final AlertDialog dialog = new AlertDialog.Builder(this).create();
            ImageView img = imgEntryView.findViewById(R.id.large_image);
            //ImageUtils.get().loadCircle(img,R.drawable.icon_camera,R.drawable.icon_camera, avatarurl);
            Glide.with(this).load(avatarurl).error(R.mipmap.icon_camera).into(img);
            // imageDownloader.download("图片地址",img); // 这个是加载网络图片的，可以是自己的图片设置方法
            dialog.setView(imgEntryView); // 自定义dialog
            dialog.show();
            imgEntryView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramView) {
                    dialog.cancel();
                }
            });
        }
    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(Environment
                        .getExternalStorageDirectory(), IMAGE_FILE_NAME));
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            } else {
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
            }
        }
        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }
    /**
     * 判断SD卡是否可用
     * @return SD卡可用返回true
     */
    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(status);
    }
    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK);
        // 设置文件类型
        intentFromGallery.setType("image/*");
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }
    private void commit() {

    }

    public void dialogIntput(TextView v,String title,String hint){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                 builder.setTitle(title);
                final EditText et = new EditText(this);
                 et.setHint(hint);
                 et.setSingleLine(true);
                 builder.setView(et);
                 builder.setNegativeButton("取消",null);
                 builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                                String content = et.getText().toString();
                                v.setText(content);
                                 /*if (password.equals("123456")) {
                                       Toast.makeText(ChangePersonalMessageActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(ChangePersonalMessageActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                    }*/
                            }
                     });
                       AlertDialog alertDialog = builder.create();
                       alertDialog.show();

               }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            // Toast.makeText(this,"取消",Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(ChangePersonalMessageActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(Environment
                                .getExternalStorageDirectory(), IMAGE_FILE_NAME));
                        cropRawPhoto(contentUri);
                    } else {
                        cropRawPhoto(Uri.fromFile(tempFile));
                    }
                } else {
                    //Toast.makeText(this,"没有SDCard!",Toast.LENGTH_LONG).show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (extras != null) {
            photo = extras.getParcelable("data");
            my_photo.setImageBitmap(photo);
        }
    }

}
