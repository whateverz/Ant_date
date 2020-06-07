package com.ant.antdate.topic;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ant.antdate.R;
import com.ant.antdate.activity.SendVetifyCodeActivity;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.bean.ThemesInfo;
import com.ant.antdate.logic.LogicRequest;
import com.ant.antdate.utils.OtherUtils;
import com.ant.antdate.view.PopuChoooseWindow;
import com.ant.antdate.view.item.ItemImage;
import com.squareup.picasso.Picasso;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lib.frame.adapter.WgAdapter;
import lib.frame.module.http.HttpHelper;
import lib.frame.module.http.HttpResult;
import lib.frame.module.ui.OnClick;
import lib.frame.view.item.ItemBase;
import lib.frame.view.recyclerView.RecyclerView;


public class ShorttopicActivity extends BaseActivity {
    private static final int    REQUEST_CAMERA   = 1;
    private static final String TAG              = "vivi";
    private static final int    REQUEST_ALBUM_OK = 2;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.right_tv)
    TextView right_tv;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout id_flowlayout;

    private RecyclerView recyclerView;
    @BindView(R.id.btn_gochoose)
    Button mBtnGoChoose;
    @BindView(R.id.ivDispaly)
    ImageView    mIvDispaly;
    private File mTmpFile;
    private ArrayList<String> mDatas = new ArrayList<>();
    /**
     * 设置最大数量
     */
    private int mMaxNum=6;

    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.activity_shorttopic;

    }

    @Override
    protected void initView() {
        super.initView();
        title_tv = findViewById(R.id.title_tv);
        right_tv = findViewById(R.id.right_tv);
        back_iv = findViewById(R.id.back_iv);
        id_flowlayout = findViewById(R.id.id_flowlayout);
        title_tv.setText("短话题");
        right_tv.setVisibility(View.VISIBLE);
        right_tv.setText("发布");
        back_iv.setOnClickListener(this);
        recyclerView = findViewById(R.id.rv_image);


        initRecycleView();
    }

    @Override
    public <T> void onHttpErrorCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {
        super.onHttpErrorCallBack(resultType, reqId, resContent, reqObject, httpResult);

    }

    @Override
    public <T> void onHttpCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {
        super.onHttpCallBack(resultType, reqId, resContent, reqObject, httpResult);
       List<ThemesInfo> themesInfo = (List<ThemesInfo>) httpResult.getResults();
        List<String> list = new ArrayList<>();
       for (int i=0;i<themesInfo.size();i++){
           String theme = themesInfo.get(i).getTheme_name();
           list.add(theme);
       }



        if (reqId==1){
            final LayoutInflater mInflater = LayoutInflater.from(ShorttopicActivity.this);
            id_flowlayout.setAdapter(new TagAdapter<String>(list)
            {
                @Override
                public View getView(FlowLayout parent, int position, String s)
                {
                    TextView tv = (TextView) mInflater.inflate(R.layout.flow_layout_item,
                            id_flowlayout, false);
                    tv.setText(s);
                    return tv;
                }
            });
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        LogicRequest.Themes(1,getHttpHelper());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;


        }
    }

    private void initRecycleView() {
        recyclerView.setAdapter(new WgAdapter<String>(mContext) {

            @Override
            protected ItemBase<String> createItem(@NonNull Context context) {
                return new ItemImage(context);
            }
        });
    }




    @OnClick(R.id.btn_gochoose)
    public void onClick() {

    }


    private void lightOff() {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.3f;
        getWindow().setAttributes(layoutParams);

    }

    private void lightOn() {

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 1.0f;
        getWindow().setAttributes(layoutParams);
    }

    /**
     * 选择相机
     */

    private void showCamera() {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            mTmpFile = OtherUtils.createFile(getApplicationContext());

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {
            Toast.makeText(getApplicationContext(), "没找到相机", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CAMERA:
                //请求相机
                if (mTmpFile != null) {
                    Log.d(TAG, "onActivityResult: 请求相机： " + mTmpFile.getAbsolutePath());

                    Picasso.with(this).load(mTmpFile).centerCrop().resize(OtherUtils.dip2px(this,100),OtherUtils.dip2px(this,100))
                            .error(R.mipmap.icon_camera).into(mIvDispaly);
                }
                mDatas.add(mTmpFile.getAbsolutePath());
                break;
            case REQUEST_ALBUM_OK:
                Log.d(TAG, "onActivityResult:相册 " + data.getData().toString());
                ContentResolver resolver = getContentResolver();
//
//                try {
//                    InputStream inputStream = resolver.openInputStream(data.getData());
//
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//
//                    mIvDispaly.setImageBitmap(bitmap);
//
//                } catch (FileNotFoundException e) {
//
//
//                    e.printStackTrace();
//                }

                Cursor query = resolver.query(data.getData(), null, null, null, null);

                String str = null;
                while (query.moveToNext()) {
                    Log.d(TAG, "onActivityResult:数量 " + query.getCount());
                    str =query.getString(query.getColumnIndex(MediaStore.Images.Media.DATA));
                    Log.d(TAG, "onActivityResult: 列名" + query.getString(query.getColumnIndex(MediaStore.Images.Media.DATA)));
                }
                query.close();
                Picasso.with(this).load(new File(str)).centerCrop().resize(OtherUtils.dip2px(this,100),OtherUtils.dip2px(this,100))
                        .error(R.mipmap.icon_camera).into(mIvDispaly);
                mDatas.add(str);

                break;

        }


    }


}
