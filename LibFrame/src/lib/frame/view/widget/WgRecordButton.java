package lib.frame.view.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import lib.frame.R;
import lib.frame.base.AppBase;
import lib.frame.base.IdConfigBase;
import lib.frame.utils.Log;


@SuppressLint("NewApi")
public class WgRecordButton extends AppCompatTextView {

    private Context mContext;
    private String TAG = "RecordButton";

    private AppBase mAppBase;

    private String mFileName = null;

    private OnFinishedRecordListener finishedListener;

    private static final int MIN_INTERVAL_TIME = 1000;// 1s
    private long startTime;

    /**
     * 取消语音发送
     */
    private Dialog recordIndicator;

    private static int[] res = {R.drawable.mic_2, R.drawable.mic_3,
            R.drawable.mic_4, R.drawable.mic_5};
    private static ImageView view;

    private MediaRecorder recorder;

    private ObtainDecibelThread thread;

    private Handler volumeHandler;

    public final int MAX_TIME = 60000;// 最长录音时间's

    private String downStr = "按住说话";
    private String upStr = "松开发送";
    private String outsideStr = "取消发送";

    private Timer mTimer;
    private boolean isRecording;
    private int wgW = 0;
    private int wgH = 0;

    public WgRecordButton(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public WgRecordButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public WgRecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            setText(downStr);
        }
    }

    public void setSavePath(String path) {
        mFileName = path;
    }

    public void setOnFinishedRecordListener(OnFinishedRecordListener listener) {
        finishedListener = listener;
    }

    private void init() {
        mAppBase = (AppBase) mContext.getApplicationContext();
        setSavePath(IdConfigBase.RECORD_FILE);
        volumeHandler = new ShowVolumeHandler();
        setGravity(Gravity.CENTER);
    }

    public void setPressText(String upStr, String downStr, String outsideStr) {
        this.downStr = downStr;
        this.upStr = upStr;
        this.outsideStr = outsideStr;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mFileName == null)
            return false;

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isRecording = true;
                // mApp.vibrate();
                if (!TextUtils.isEmpty(downStr)) {
                    setText(downStr);
                    setBackgroundResource(R.drawable.chat_say_pressed);
                }
                // setPadding(UIHelper.dp2px(mContext, 10f),
                // UIHelper.dp2px(mContext, 7f),
                // UIHelper.dp2px(mContext, 10f), UIHelper.dp2px(mContext, 7f));
                initDialogAndStartRecord();
                break;
            case MotionEvent.ACTION_UP:
                if (isRecording) {
                    if (event.getX() < 0 || event.getX() > wgW || event.getY() < 0
                            || event.getY() > wgH) {
                        cancelRecord();
                    } else {
                        finishRecord();
                    }
                    if (!TextUtils.isEmpty(downStr)) {
                        setText(downStr);
                        setBackgroundResource(R.drawable.chat_say);
                    }
                    // setPadding(UIHelper.dp2px(mContext, 10f),
                    // UIHelper.dp2px(mContext, 7f),
                    // UIHelper.dp2px(mContext, 10f),
                    // UIHelper.dp2px(mContext, 7f));
                }
                break;
            case MotionEvent.ACTION_CANCEL:// 当手指移动到view外面，会cancel
                if (isRecording) {
                    cancelRecord();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecording) {
                    if (event.getX() < 0 || event.getX() > wgW || event.getY() < 0
                            || event.getY() > wgH) {
                        setText("松开取消录音");
                    } else {
                        setText(upStr);
                    }
                }
                break;
        }

        return true;
    }

    private void initDialogAndStartRecord() {
        wgW = getWidth();
        wgH = getHeight();
        startTime = System.currentTimeMillis();
        setSavePath(IdConfigBase.RECORD_FILE);
        mFileName += startTime;
        recordIndicator = new Dialog(getContext(),
                R.style.like_toast_dialog_style);
        view = new ImageView(getContext());
        view.setImageResource(R.drawable.mic_2);
        recordIndicator.setContentView(view, new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        recordIndicator.setOnDismissListener(onDismiss);
        LayoutParams lp = recordIndicator.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;

        startRecording();
        recordIndicator.show();
    }

    private void finishRecord() {
        isRecording = false;
        stopRecording();
        recordIndicator.dismiss();
        File file = new File(mFileName);
        long intervalTime = System.currentTimeMillis() - startTime;
        if (intervalTime < MIN_INTERVAL_TIME) {
            Toast.makeText(getContext(), "时间太短！", Toast.LENGTH_SHORT).show();
            file.delete();
            return;
        }
        if (file.length() < 500) {
            Toast.makeText(getContext(), "录音失败，请重试", Toast.LENGTH_SHORT).show();
            file.delete();
            return;
        }
        if (finishedListener != null)
            finishedListener.onFinishedRecord(mFileName, (int) (intervalTime / 1000));
    }

    private void cancelRecord() {
        isRecording = false;
        stopRecording();
        recordIndicator.dismiss();

        Toast.makeText(getContext(), "取消录音！", Toast.LENGTH_SHORT).show();
        File file = new File(mFileName);
        file.delete();

        setText(upStr);
        setBackgroundResource(R.drawable.chat_say);
    }

    private Handler hRecord = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 123) {
                if (!TextUtils.isEmpty(upStr)) {
                    setText(upStr);
                    setBackgroundResource(R.drawable.chat_say);
                }
                // setPadding(UIHelper.dp2px(mContext, 10f),
                // UIHelper.dp2px(mContext, 7f),
                // UIHelper.dp2px(mContext, 10f),
                // UIHelper.dp2px(mContext, 7f));
                finishRecord();
            }
        }

        ;
    };

    private void startRecording() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 123;
                hRecord.sendMessage(msg);
            }
        }, MAX_TIME);

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setAudioChannels(1);
        recorder.setAudioEncodingBitRate(4000);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        // recorder.setVideoFrameRate(4000);

        recorder.setOutputFile(mFileName);

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            recorder.start();
            thread = new ObtainDecibelThread();
            thread.start();
        } catch (Exception e) {
        }
    }

    private void stopRecording() {
        mTimer.cancel();
        if (thread != null) {
            thread.exit();
            thread = null;
        }
        if (recorder != null) {
            // FIXED partly Lear 2014.4.26
            try {
                recorder.stop();
                recorder.release();
            } catch (Exception e) {
                Log.e("lwxkey", "RecordButton: " + e.toString());
            } finally {
                recorder = null;
            }
        }
    }

    private class ObtainDecibelThread extends Thread {

        private volatile boolean running = true;

        public void exit() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (recorder == null || !running) {
                    break;
                }
                int x = recorder.getMaxAmplitude();
                if (x != 0) {
                    int f = (int) (10 * Math.log(x) / Math.log(10));
                    if (f < 26)
                        volumeHandler.sendEmptyMessage(0);
                    else if (f < 32)
                        volumeHandler.sendEmptyMessage(1);
                    else if (f < 38)
                        volumeHandler.sendEmptyMessage(2);
                    else
                        volumeHandler.sendEmptyMessage(3);
                }
            }
        }
    }

    private OnDismissListener onDismiss = new OnDismissListener() {

        @Override
        public void onDismiss(DialogInterface dialog) {
            stopRecording();
        }
    };

    private class ShowVolumeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            view.setImageResource(res[msg.what]);
        }
    }

    public interface OnFinishedRecordListener {
        public void onFinishedRecord(String audioPath, int time);
    }

    // private boolean
}
