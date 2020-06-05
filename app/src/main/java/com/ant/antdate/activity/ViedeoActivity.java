package com.ant.antdate.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.base.BaseFragment;
import com.ant.antdate.fragment.MailListFragment;
import com.ant.antdate.fragment.MineFragment;
import com.ant.antdate.fragment.SquareFragment;
import com.bumptech.glide.Glide;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import lib.frame.module.ui.BindView;

public class ViedeoActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.activity_videoview;
        //init();
    }

    @Override
    protected void loadData() {
        super.loadData();
        // LogicRequest.sendSMS(1, "13581714368", 1, getHttpHelper());
        //RegisterRequest.Register(2, "13581714368", "1696", getHttpHelper());
    }

    @Override
    public void handleObject(String tag, Object... objects) {
        super.handleObject(tag, objects);
        JCVideoPlayerStandard player = findViewById(R.id.player_list_video);
        if (tag=="imageurl"){
            Glide.with(ViedeoActivity.this).load(objects.toString()).into(player.thumbImageView);
        }
      /*  boolean setUp = player.setUp("http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4", JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
        if (setUp) {

        }*/
    }

    @Override
    protected void initView() {
        super.initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



    }




    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {

        }

    }
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
