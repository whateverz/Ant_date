package lib.frame.base;

import android.os.Environment;

/**
 * Created by lwxkey on 16/5/8.
 */
public class IdConfigBase {

    public static int cOsType = 1;//1:android;2:ios
    public static int cVersion = 0;//版本号
    public static double lat;
    public static double lon;
    public static String city = "";
    public static String country = "";
    public static String province = "";
    public static String address;
    public static String language = "";

    public static final int RUNNING_MAIN = 1;//在主页面运行
    public static final int RUNNING_BACKGROUND = 2;//在后台运行
    public static final int RUNNING_OTHER = 3;//在别的页面运行
    public static final int RUNNING_SINGLE_CHAT = 4;//在单聊界面
    public static final int RUNNING_GROUP_CHAT = 5;//在群聊界面

    public static final int PAGESIZE = 10;

    public static final String INTENT_TAG = "INTENT_TAG";

    public static final int REFRESH = -1;
    public static final int LOADMORE = 200;

    public static final int DEFAULT_HEADER_SIZE = 480;

    public static String BASE_FILE_NAME = Environment.getExternalStorageDirectory().getPath() + "/JM/";// 所有软件数据在SD卡的存储路径
    public static String APP_FILE_NAME = "";// APP基础文件夹路径，在软件初始化的时候设置

    public static String APP_FILE = "";// BASE_FILE_NAME + "/" +
    // APP_FILE_NAME;// 存放图片的文件夹
    public static String IMGS_FILE = BASE_FILE_NAME;// APP_FILE + "/IMGS";// 存放图片的文件夹
    public static String CROP_FILE = "";// APP_FILE + "/CROP";// 存放裁剪图片的文件夹
    public static String TEMP_FILE = "";// APP_FILE + "/TEMP";// 存放临时文件的文件夹
    public static String MUSIC_FILE = "";// APP_FILE + "/MUSIC";// 存放临时文件的文件夹
    public static String RECORD_FILE = "";// APP_FILE + "/RECORD";// 存放录音文件的文件夹
    public static String CACHE_FILE = "";// APP_FILE + "/CACHE";// 缓存的文件夹
    public static String DOWNLOAD_FILE = "";// APP_FILE + "/DOWNLOAD";// 下载的文件夹
    public static String PATCH_FILE = "";// APP_FILE + "/PATCH";// 增量更新下载的文件夹

    public static final int NEED_RELOGIN = 1;//需要重新登录
    public static final int NEED_REGIST = 2;//需要重新登录
    public static final int NEED_ACTIVITY = 3;//需要激活
    public static final int PASS_RECORD_PATH = 4;//发送拍摄录制结束后文件的路劲
    public static final int PASS_IMG_PATH = 5;//发送图片处理完成后文件的路劲
    public static final int NETWORK_CHANGED = 6;//网络条件变化

    public static final int EVENT_NEED_RELOGIN = 1;//标记需要重新登录
    public static final int EVENT_NETWORK_CHANGE = 2;//网络变化

    public static final int SHOW_PROCESS_BAR = 100;//页面显示圈圈
    public static final int DISMISS_PROCESS_BAR = 101;//页面取消圈圈
    public static final int EXPRESSION_CLICK = 102;//表情点击事件

    public static final int EVENT_UPDATE_LIST = 201;//页面刷新
    public static final int EVENT_MUSIC_PLAY = 202;//音乐播放
    public static final int EVENT_MUSIC_STOP = 203;//音乐停止
    public static final int EVENT_MUSIC_PAUSE = 204;//音乐暂停
    public static final int EVENT_SELECTED_VIDEO = 300;//视频选择

    public static final int EVENT_NOTICE_NEW_MSG = 400;//收到新的消息

    public static final int REQ_ID_GET_IMG = 1701;//从上一层传递的数据

//    public static final String SP_UNREAD_NOTICE = "SP_UNREAD_NOTICE";//存储未读消息通知

    public static final int ACTION_OPTION_CLICK = 100;//页面选项点击
    public static final int ACTION_UPDATE_ITEM = 101;//更新item
    public static final int ACTION_ITEM_CLICK = 102;//item点击
    public static final int ACTION_IMG_PICK = 103;//选择图片
    public static final int ACTION_KEYBOARD_SHOW = 104;//输入法显示
    public static final int ACTION_KEYBOARD_HIDE = 105;//输入法隐藏
    public static final int ACTION_PASS_MSG = 106;//IM消息传递
    public static final int ACTION_OPTION_0 = 200;//通用选项
    public static final int ACTION_OPTION_1 = 201;//通用选项
    public static final int ACTION_OPTION_2 = 202;//通用选项
    public static final int ACTION_OPTION_3 = 203;//通用选项


    public static String CHANNEL = "NL";

}
