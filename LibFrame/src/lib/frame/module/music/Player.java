package lib.frame.module.music;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import lib.frame.utils.FileUtils;
import lib.frame.utils.Log;

public class Player implements Runnable {

    private String urlPath;
    private String localPath;
    public static long musicId;

    private NlMediaPlayer nlMediaPlayer;
    private int position;
    private boolean isPlaying;


    private int status = 0;// 0：正常状态，1：网络歌曲播放下载中；2：网络歌曲下载完成；3：终止下载

    public static final int TYPE_DOWNING = 1;// 下载进行中
    public static final int TYPE_FINISH = 2;// 下载结束
    public static final int TYPE_CANCEL = 3;// 终止下载


    public Player(long musicId, String urlPath, String localPath, NlMediaPlayer nlMediaPlayer) {
        this.urlPath = urlPath;
        this.nlMediaPlayer = nlMediaPlayer;
        this.localPath = localPath;
        this.musicId = musicId;
    }

    private void play() {
        if (urlPath.startsWith("/") || urlPath.startsWith("http")) {
            isPlaying = true;
            nlMediaPlayer.setOnPreparedListener(new OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer arg0) {
                    if (status != TYPE_CANCEL) {
                        nlMediaPlayer.start();
                        if (position > 0) {
                            nlMediaPlayer.seekTo(position);
                        }
                        nlMediaPlayer.setPreparing(false);
                    }
                }
            });
            try {
                nlMediaPlayer.setPreparing(true);
                nlMediaPlayer.reset();
                Log.i("sbsbs", urlPath);
                nlMediaPlayer.setDataSource(urlPath);
                nlMediaPlayer.prepareAsync();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startPlay(int p) {
        this.position = p;
        isPlaying = false;
        if (urlPath.startsWith("/")) {// 本地歌曲
            play();
        } else if (urlPath.startsWith("http")) {// 网络歌曲
            new Thread(this).start();
        }
    }

    public void stop() {
        if (status == TYPE_DOWNING) {// 正在下载
            FileUtils.delFile(localPath);
        }
        status = TYPE_CANCEL; // 标识终止下载
    }

    @Override
    public void run() {
        String tmp_path = localPath;
        File tmp = new File(tmp_path);
        URL mUrl;
        try {
            status = TYPE_DOWNING;// 开始下载
            mUrl = new URL(urlPath);
            RandomAccessFile fos = new RandomAccessFile(tmp, "rw");
            HttpURLConnection con = (HttpURLConnection) mUrl.openConnection();
            InputStream is = con.getInputStream();
            fos.setLength(con.getContentLength());
            fos.seek(0);
            byte[] buffer = new byte[1024];
            int length = 0;
            int filesize = 0;
            while ((length = is.read(buffer)) != -1) {
                if (status == TYPE_CANCEL) {// 终止下载
                    nlMediaPlayer.reset();
                    con.disconnect();
                    is.close();
                    fos.close();
//					if (mIUniversal != null) {
//						Object[] objects = { musicId, filesize, status,
//								localPath, urlPath };
//						mIUniversal.action(status, objects);
//					}
                    return;
                }
                fos.write(buffer, 0, length);
                filesize += length;
//				if (mIUniversal != null) {
//					Object[] objects = { musicId, filesize, status, localPath,
//							urlPath };
//					mIUniversal.action(status, objects);
//				}
                if (filesize > 1024 * 10) {
                    if (!isPlaying) {
                        play();
                    }
                }
            }
            status = TYPE_FINISH;// 下载结束
//			if (mIUniversal != null) {
//				Object[] objects = { musicId, filesize, status, localPath,
//						urlPath };
//				mIUniversal.action(status, objects);
//			}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
