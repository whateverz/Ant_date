package lib.frame.module.music;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import org.greenrobot.eventbus.EventBus;

import lib.frame.base.IdConfigBase;
import lib.frame.bean.EventBase;

public class NlMediaPlayer extends MediaPlayer {

    private OnNlMediaPlayerListener mListener;

    private boolean isPreparing = true;// 标识正在准备
    private boolean isReseted;// 标识复位
    private int tag = 0;

    public NlMediaPlayer() {
        setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                EventBus.getDefault().post(new EventBase(IdConfigBase.EVENT_MUSIC_STOP));
                if (mListener != null) {
                    mListener.onMpStop();
                }
            }
        });
    }

    public void setPreparing(boolean isPreparing) {
        this.isPreparing = isPreparing;
    }

    public boolean isPreparing() {
        return isPreparing;
    }

    public boolean isReseted() {
        return isReseted;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == tag) {
                isReseted = false;
            }
        }
    };


    @Override
    public void stop() throws IllegalStateException {
        if (isPlaying()) {
            super.stop();
        }
        if (mListener != null) {
            mListener.onMpStop();
        }
        // SysUtils.keepScreenOn(mContext, false);
        EventBus.getDefault().post(new EventBase(IdConfigBase.EVENT_MUSIC_STOP));
    }

    @Override
    public void start() throws IllegalStateException {
        super.start();
        // SysUtils.keepScreenOn(mContext, true);
        if (mListener != null) {
            mListener.onMpStart();
        }
        EventBus.getDefault().post(new EventBase(IdConfigBase.EVENT_MUSIC_PLAY));
    }

    @Override
    public void reset() {
        super.reset();
        isReseted = true;
        tag++;
        handler.sendEmptyMessageDelayed(tag, 1000);
    }

    @Override
    public void release() {
        super.release();
        // mContext = null;
    }

    @Override
    public void pause() throws IllegalStateException {
        super.pause();
        if (mListener != null) {
            mListener.onMpPuse();
        }
        // SysUtils.keepScreenOn(mContext, false);
        EventBus.getDefault().post(new EventBase(IdConfigBase.EVENT_MUSIC_PAUSE));
    }

    public interface OnNlMediaPlayerListener {
        void onMpPuse();

        void onMpStop();

        void onMpStart();
    }

    public void setOnWgMediaPlayerListener(OnNlMediaPlayerListener l) {
        mListener = l;
    }
}
