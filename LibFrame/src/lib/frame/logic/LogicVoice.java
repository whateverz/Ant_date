package lib.frame.logic;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import lib.frame.utils.Log;

/**
 * Created by shuaqq on 2017/3/13.
 */

public class LogicVoice {
    private final static String TAG = "LogicRecorder";
    private static LogicVoice mInstance;
    private CountDownTimer mCountDownTimer;
    private long mCurrentPlayId = -1;
    private List<LogicVoice.PlayTriggerListener> mPlayTriggerListeners = new ArrayList<>();
    private int mTotalTime;
    private MediaPlayer mVoiceMedia;

    private LogicVoice() {
    }

    public static LogicVoice getInstance() {
        if (mInstance == null) {
            mInstance = new LogicVoice();
        }

        return mInstance;
    }

    private void startAlarm(final long var1, MediaPlayer var2, final int var3) {
        var2.start();

        for (int var4 = 0; var4 < this.mPlayTriggerListeners.size(); ++var4) {
            this.mPlayTriggerListeners.get(var4).startPlay(var1, var3);
        }

        this.mCountDownTimer = (new CountDownTimer((long) (var3 * 1000), 500) {
            public void onFinish() {
            }

            public void onTick(long var1x) {
                for (int var3x = 0; var3x < mPlayTriggerListeners.size(); ++var3x) {
                    Log.d(TAG, "CountDownVoicePlayManage_countDown == " + var1x);
                    mPlayTriggerListeners.get(var3x).countDown(var1, var3, (int) (var1x / 1000L));
                }

            }
        }).start();
    }

    private void stopAlarm(long var1) {
        if (this.mCountDownTimer != null) {
            this.mCountDownTimer.cancel();
        }

        VoicePlayManage.getInstance().stopAlarm(this.mVoiceMedia);
        mVoiceMedia = null;

        for (int var2 = 0; var2 < this.mPlayTriggerListeners.size(); ++var2) {
            this.mPlayTriggerListeners.get(var2).stopPlay(var1, this.mTotalTime);
        }

        this.mCurrentPlayId = -1;
    }

    public void playTrigger(Context context, final long playId, String path, final int totalTime) {
        if (path != null && !TextUtils.isEmpty(path)) {
            if (this.mCurrentPlayId == playId) {
                this.stopAlarm(playId);
            } else {
                if (mCurrentPlayId != -1)
                    this.stopAlarm(mCurrentPlayId);
                this.mCurrentPlayId = playId;
                this.mVoiceMedia = VoicePlayManage.getInstance().startAlarm(context, false, path, new VoicePlayManage.VoicePlayListener() {
                    public void completePlay(MediaPlayer var1) {
                        Log.i(TAG, "IreneBond： completePlay");
                        stopAlarm(mCurrentPlayId);
                    }

                    public void startPlay(MediaPlayer var1) {
                        Log.i(TAG, "IreneBond： startPlay");
                        mTotalTime = totalTime;
                        startAlarm(playId, var1, totalTime);
                    }
                });
            }
        }
    }

    public void registPlayTriggerListener(PlayTriggerListener var1) {
        if (var1 != null && !this.mPlayTriggerListeners.contains(var1)) {
            this.mPlayTriggerListeners.add(var1);
        }
    }

    public void stopPlay() {
        this.stopAlarm(this.mCurrentPlayId);
    }

    public void unregistPlayTriggerListener(PlayTriggerListener var1) {
        this.mPlayTriggerListeners.remove(var1);
    }

    public interface PlayTriggerListener {
        void countDown(long id, int total, int count);

        void startPlay(long id, int total);

        void stopPlay(long id, int total);
    }
}
