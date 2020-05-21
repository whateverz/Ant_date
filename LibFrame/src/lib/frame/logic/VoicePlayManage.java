package lib.frame.logic;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

/**
 * Created by shuaqq on 2017/3/13.
 */

public class VoicePlayManage {
    private static VoicePlayManage mInstance;

    private VoicePlayManage() {
    }

    public static VoicePlayManage getInstance() {
        if (mInstance == null) {
            mInstance = new VoicePlayManage();
        }

        return mInstance;
    }

    public static VoicePlayManage init() {
        return new VoicePlayManage();
    }

    public void openSpeaker(Context var1) {
        try {
            AudioManager var3 = (AudioManager) var1.getSystemService(Context.AUDIO_SERVICE);
            var3.setMode(AudioManager.MODE_NORMAL);
            if (!var3.isSpeakerphoneOn()) {
                var3.setSpeakerphoneOn(true);
            }

        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    public MediaPlayer startAlarm(Context var1, boolean var2, int var3, final VoicePlayManage.VoicePlayListener var4) {
        this.openSpeaker(var1);
        MediaPlayer var5 = MediaPlayer.create(var1, var3);
        var5.setLooping(var2);

        try {
            var5.start();
            var5.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (var4 != null) {
                        var4.startPlay(mp);
                    }
                }
            });
            var5.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (var4 != null) {
                        var4.completePlay(mp);
                    }
                }
            });
            return var5;
        } catch (Exception var7) {
            var7.printStackTrace();
            return var5;
        }
    }

    public MediaPlayer startAlarm(Context var1, boolean var2, String var3, final VoicePlayManage.VoicePlayListener var4) {
        this.openSpeaker(var1);
        MediaPlayer var5 = new MediaPlayer();
        var5.setLooping(var2);

        try {
            var5.setDataSource(var3);
            var5.prepareAsync();
            var5.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (var4 != null) {
                        var4.startPlay(mp);
                    }
                }
            });
            var5.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (var4 != null) {
                        var4.completePlay(mp);
                    }
                }
            });
            return var5;
        } catch (Exception var7) {
            var7.printStackTrace();
            return var5;
        }
    }

    public void stopAlarm(MediaPlayer var1) {
        if (var1 != null) {
            try {
                if (var1.isPlaying()) {
                    var1.stop();
                }
                var1.release();
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }
    }

    public interface VoicePlayListener {
        void completePlay(MediaPlayer var1);

        void startPlay(MediaPlayer var1);
    }
}
