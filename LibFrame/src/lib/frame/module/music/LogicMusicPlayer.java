package lib.frame.module.music;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

import lib.frame.base.AppBase;


public class LogicMusicPlayer {

    private String TAG = "LogicMusicPlayer";
    private Context mContext;

    private static LogicMusicPlayer logicMusicPlayer;
    private Player player;

    public NlMediaPlayer nlMediaPlayer;

    private String lastPlayUrl = "";// 最近播放的Url


    protected LogicMusicPlayer(Context context) {
        this.mContext = context;
        getNlMediaPlayer();
    }

    protected void mLog(Object object) {
        if (AppBase.DEBUG) {
            Log.i(TAG, TAG + "   " + object);
            Log.i(AppBase.TAG, TAG + "    " + object);
        }
    }

    public static LogicMusicPlayer getInstance(Context context) {
        if (logicMusicPlayer == null) {
            logicMusicPlayer = new LogicMusicPlayer(context);
        }
        return logicMusicPlayer;
    }

    public NlMediaPlayer getNlMediaPlayer() {
        if (nlMediaPlayer == null) {
            nlMediaPlayer = new NlMediaPlayer();
        }
        return nlMediaPlayer;
    }

    /**
     * @return 0:跳到指定播放位置；1：从0开始播放
     */
    public int play(long musicId, String url, String localPath, final int position) {
        if (TextUtils.isEmpty(url)) {
            return 0;
        }
        if (lastPlayUrl.equals(url) && nlMediaPlayer.isPlaying()) {
            nlMediaPlayer.seekTo(position);
            return 0;
        } else {
            lastPlayUrl = url;
            if (player != null) {
                player.stop();
                player = null;
            }
            // wkMediaPlayer.setDataSource(mContext, uriPath);
            File file = new File(localPath);
            if (file.exists()) {
                player = new Player(musicId, localPath, localPath, nlMediaPlayer);
            } else {
                player = new Player(musicId, url, localPath, nlMediaPlayer);
            }
            player.startPlay(position);
            return 1;
        }
    }

    public void pause() {
        if (player != null) {
            player.stop();
            player = null;
        }
        nlMediaPlayer.pause();
    }

}
