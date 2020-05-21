package lib.frame.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lib.frame.R;


/**
 * @author lwxkey
 *         <p/>
 *         选项item
 */
public class WgSettingBase extends LinearLayout implements OnClickListener {

    protected LinearLayout vBody;// 控件rootView

    protected TextView vTitle;// 标题
    protected TextView vDetail;//介绍
    protected TextView vInput;// 输入栏
    protected TextView vSubTitle;// 右侧详情
    protected WgSwitchButton vSwitch;// 开关

    protected ImageView vImgLeft;// 左侧标签图标
    protected ImageView vImgCenter;// 中间标签图标
    protected ImageView vImgRight;// 右侧标签图标

    protected View vLine1;
    protected View vLine2;
    protected View vLine3;
    protected View vLine4;

    protected Context mContext;

    public static final int LINE_BOTH = 0;
    public static final int LINE_TOP = 1;
    public static final int LINE_CENTER = 2;
    public static final int LINE_BOTTOM = 3;
    public static final int LINE_NONE = 4;

    private int mLineStatus = LINE_BOTH;

    private String mTitle;
    private String mDetail;
    private String mHint;
    private String mSubTitle;

    private int imgLeftId = 0;
    private int imgCenterId = 0;
    private int imgRightId = 0;

    private boolean isInputBig = false;
    private boolean isInputPassword = false;

    private boolean isImgLeftBig = false;
    private boolean isImgCenterBig = false;
    private boolean isImgRightBig = false;

    private boolean isShowInput = false;
    private boolean isShowSwitch = false;
    private boolean isShowNext = false;

    public WgSettingBase(Context context) {
        super(context);
        mContext = context;
        initThis(null);
    }

    public WgSettingBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initThis(attrs);
    }

    public WgSettingBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initThis(attrs);
    }

    public LinearLayout getBody() {
        return vBody;
    }

    public String getHint() {
        return mHint;
    }

    public ImageView getImgCenterView() {
        return vImgCenter;
    }

    public int getImgCenterId() {
        return imgCenterId;
    }

    public ImageView getImgLeftView() {
        return vImgLeft;
    }

    public int getImgLeftId() {
        return imgLeftId;
    }

    public ImageView getImgRightView() {
        return vImgRight;
    }

    public int getImgRightId() {
        return imgRightId;
    }

    public TextView getInputEditTextView() {
        return vInput;
    }

    public String getText() {
        return vInput.getText().toString();
    }

    public void setText(String text) {
        vInput.setText(text);
    }

    public String getSubTitle() {
        return mSubTitle;
    }

    public WgSwitchButton getSwitchBtn() {
        return vSwitch;
    }

    public String getTitle() {
        return mTitle;
    }

    public TextView getSubTitleTextView() {
        return vSubTitle;
    }

    public TextView getTitleTextView() {
        return vTitle;
    }

    protected int getRootview() {
        return R.layout.wg_setting_base;
    }

    protected void initThis(AttributeSet attrs) {
        LayoutInflater.from(mContext).inflate(getRootview(), this);

        // 主体
        vBody = (LinearLayout) findViewById(R.id.wg_setting_body);

        // 大标题
        vTitle = (TextView) findViewById(R.id.wg_setting_title);
        //副标题
        vDetail = (TextView) findViewById(R.id.wg_setting_detail);
        // 输入框
        vInput = (EditText) findViewById(R.id.wg_setting_input);
        // 小标题
        vSubTitle = (TextView) findViewById(R.id.wg_setting_sub_title);
        // 开关
        vSwitch = (WgSwitchButton) findViewById(R.id.wg_setting_btn_switch);

        // 图片
        vImgLeft = (ImageView) findViewById(R.id.wg_setting_img_left);
        vImgCenter = (ImageView) findViewById(R.id.wg_setting_img_center);
        vImgRight = (ImageView) findViewById(R.id.wg_setting_img_right);

        // 线条
        vLine1 = findViewById(R.id.wg_setting_line1);
        vLine2 = findViewById(R.id.wg_setting_line2);
        vLine3 = findViewById(R.id.wg_setting_line3);
        vLine4 = findViewById(R.id.wg_setting_line4);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs,
                R.styleable.WgSettingBase);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int itemId = ta.getIndex(i);
            if (itemId == R.styleable.WgSettingBase_lineStatus) {
                mLineStatus = ta.getInteger(itemId, 0);
            } else if (itemId == R.styleable.WgSettingBase_settingTitle) {
                mTitle = ta.getString(itemId);
            } else if (itemId == R.styleable.WgSettingBase_detail) {
                mDetail = ta.getString(itemId);
            } else if (itemId == R.styleable.WgSettingBase_hint) {
                mHint = ta.getString(itemId);
            } else if (itemId == R.styleable.WgSettingBase_subTitle) {
                mSubTitle = ta.getString(itemId);
            } else if (itemId == R.styleable.WgSettingBase_showInput) {
                isShowInput = ta.getBoolean(itemId, false);
            } else if (itemId == R.styleable.WgSettingBase_showSwitch) {
                isShowSwitch = ta.getBoolean(itemId, false);
            } else if (itemId == R.styleable.WgSettingBase_showNext) {
                isShowNext = ta.getBoolean(itemId, false);
            } else if (itemId == R.styleable.WgSettingBase_imgLeft) {
                imgLeftId = ta.getResourceId(itemId, 0);
            } else if (itemId == R.styleable.WgSettingBase_imgCenter) {
                imgCenterId = ta.getResourceId(itemId, 0);
            } else if (itemId == R.styleable.WgSettingBase_imgRight) {
                imgRightId = ta.getResourceId(itemId, 0);
            } else if (itemId == R.styleable.WgSettingBase_isPassword) {
                isInputPassword = ta.getBoolean(itemId, false);
            } else if (itemId == R.styleable.WgSettingBase_isInputBig) {
                isInputBig = ta.getBoolean(itemId, false);
            } else if (itemId == R.styleable.WgSettingBase_isImgLeftBig) {
                isImgLeftBig = ta.getBoolean(itemId, false);
            } else if (itemId == R.styleable.WgSettingBase_isImgCenterBig) {
                isImgCenterBig = ta.getBoolean(itemId, false);
            } else if (itemId == R.styleable.WgSettingBase_isImgRightBig) {
                isImgRightBig = ta.getBoolean(itemId, false);
            }
        }
        ta.recycle();
        updateLineStatus(mLineStatus);
        setTitle(mTitle);
        setDetail(mDetail);
        setHint(mHint);
        setSubTitle(mSubTitle);

        setImgLeft(imgLeftId);
        setImgCenter(imgCenterId);
        setImgRight(imgRightId);

        setImgLeftBig(isImgLeftBig);
        setImgCenterBig(isImgCenterBig);
        setImgRightBig(isImgRightBig);

        setShowInput(isShowInput);
        setShowNext(isShowNext);
        setShowSwitch(isShowSwitch);

        setInputPassword(isInputPassword);
        setInputBig(isInputBig);
    }

    public boolean isImgCenterBig() {
        return isImgCenterBig;
    }

    public boolean isImgLeftBig() {
        return isImgLeftBig;
    }

    public boolean isImgRightBig() {
        return isImgRightBig;
    }

    public boolean isInputBig() {
        return isInputBig;
    }

    public boolean isInputPassword() {
        return isInputPassword;
    }

    public boolean isShowInput() {
        return isShowInput;
    }

    public boolean isShowNext() {
        return isShowNext;
    }

    public boolean isShowSwitch() {
        return isShowSwitch;
    }

    @Override
    public void onClick(View v) {

    }

    public void setImgCenter(int id) {
        if (id > 0) {
            vImgCenter.setBackgroundResource(id);
            vImgCenter.setVisibility(VISIBLE);
        } else {
            vImgCenter.setVisibility(GONE);
        }
    }

    public void setImgCenterBig(boolean isImgCenterBig) {
        this.isImgCenterBig = isImgCenterBig;

        if (isImgCenterBig) {
            LayoutParams lp = new LayoutParams(
                    getResources().getDimensionPixelSize(
                            R.dimen.wg_single_item_big_face), getResources()
                    .getDimensionPixelSize(
                            R.dimen.wg_single_item_big_face));
            lp.gravity = Gravity.CENTER;
            int p20 = getResources().getDimensionPixelSize(
                    R.dimen.wg_single_item_padding);
            lp.setMargins(0, p20, p20, p20);
            vImgCenter.setLayoutParams(lp);
        }
    }

    public void setImgLeft(int id) {
        if (id > 0) {
            vImgLeft.setBackgroundResource(id);
            vImgLeft.setVisibility(VISIBLE);
        } else {
            vImgLeft.setVisibility(GONE);
        }
    }

    public void setImgLeftBig(boolean isImgLeftBig) {
        this.isImgLeftBig = isImgLeftBig;

        if (isImgLeftBig) {
            LayoutParams lp = new LayoutParams(
                    getResources().getDimensionPixelSize(
                            R.dimen.wg_single_item_big_face), getResources()
                    .getDimensionPixelSize(
                            R.dimen.wg_single_item_big_face));
            lp.gravity = Gravity.CENTER;
            int p20 = getResources().getDimensionPixelSize(
                    R.dimen.wg_single_item_padding);
            lp.setMargins(0, p20, p20, p20);
            vImgLeft.setLayoutParams(lp);

            LayoutParams lp2 = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            vBody.setLayoutParams(lp2);
        }
    }

    public void setImgLeftSize(int size) {
        vImgLeft.setVisibility(VISIBLE);
        LayoutParams lp = new LayoutParams(size, size);
        lp.gravity = Gravity.CENTER;
        int p20 = getResources().getDimensionPixelSize(
                R.dimen.wg_single_item_padding);
        lp.setMargins(0, p20, p20, p20);
        vImgLeft.setLayoutParams(lp);

        LayoutParams lp2 = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        vBody.setLayoutParams(lp2);
    }

    public void setImgRight(int id) {
        if (id > 0) {
            vImgRight.setBackgroundResource(id);
            vImgRight.setVisibility(VISIBLE);
        } else {
            vImgRight.setVisibility(GONE);
        }
    }

    public void setImgRightBig(boolean isImgRightBig) {
        this.isImgRightBig = isImgRightBig;
        if (isImgRightBig) {
            LayoutParams lp = new LayoutParams(
                    getResources().getDimensionPixelSize(
                            R.dimen.wg_single_item_big_face), getResources()
                    .getDimensionPixelSize(
                            R.dimen.wg_single_item_big_face));
            lp.gravity = Gravity.CENTER;
            int p20 = getResources().getDimensionPixelSize(
                    R.dimen.wg_single_item_padding);
            lp.setMargins(0, p20, p20, p20);
            vImgRight.setLayoutParams(lp);
            vImgRight.setVisibility(VISIBLE);
        }
    }

    public void setInputBig(boolean isInputBig) {
        this.isInputBig = isInputBig;

        int pBig = getResources().getDimensionPixelSize(
                R.dimen.wk_single_item_title_text_size);
        int pSmall = getResources().getDimensionPixelSize(
                R.dimen.wk_single_item_sub_title_text_size);

        vInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, isInputBig ? pBig
                : pSmall);
        vInput.setGravity(isInputBig ? (Gravity.START | Gravity.CENTER_VERTICAL)
                : (Gravity.END | Gravity.CENTER_VERTICAL));
        vInput.setTextColor(isInputBig ? getResources().getColor(
                R.color.wg_actionbar_text) : getResources().getColor(
                R.color.wg_single_item_sub_title));
    }

    public void setInputPassword(boolean isInputPassword) {
        this.isInputPassword = isInputPassword;

        if (isInputPassword) {
            vInput.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            vInput.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    public void updateLineStatus(int status) {
        switch (status) {
            case LINE_TOP:
                // 第一个，只显示顶部较长的那条
                showLines(true, false, false, false);
                break;
            case LINE_CENTER:
                // 中间的，只显示顶部较短的那条
                showLines(false, true, false, false);
                break;
            case LINE_BOTTOM:
                // 末尾的，显示顶部较短那条和底部较长那条
                showLines(false, true, false, true);
                break;
            case LINE_BOTH:
                showLines(true, false, false, true);
                break;
            case LINE_NONE:
                showLines(false, false, false, false);
                break;
            default:
                // 显示完整的
                showLines(true, false, false, true);
                break;
        }
    }

    private void showLines(boolean line1, boolean line2, boolean line3,
                           boolean line4) {
        vLine1.setVisibility(line1 ? View.VISIBLE : View.GONE);
        vLine2.setVisibility(line2 ? View.VISIBLE : View.GONE);
        vLine3.setVisibility(line3 ? View.VISIBLE : View.GONE);
        vLine4.setVisibility(line4 ? View.VISIBLE : View.GONE);
    }

    public void setHint(String hint) {
        this.mHint = hint;
        vInput.setVisibility(View.VISIBLE);
        vInput.setHint(mHint);
    }

    public void setLineStatus(int lineStatus) {
        this.mLineStatus = lineStatus;
        updateLineStatus(mLineStatus);
    }

    public void setSubTitle(String subTitle) {
        this.mSubTitle = subTitle;

        if (!TextUtils.isEmpty(subTitle)) {
            vSubTitle.setText(subTitle);
            vSubTitle.setVisibility(View.VISIBLE);
        } else {
            vSubTitle.setVisibility(View.GONE);
        }
    }

    public void setTitle(String title) {
        this.mTitle = title;

        if (!TextUtils.isEmpty(title)) {
            vTitle.setText(title);
            vTitle.setVisibility(View.VISIBLE);
        } else {
            vTitle.setVisibility(View.GONE);
        }
    }

    public TextView getvDetail() {
        vDetail.setVisibility(View.VISIBLE);
        return vDetail;
    }

    public void setDetail(String detail) {
        this.mDetail = detail;

        if (!TextUtils.isEmpty(detail)) {
            vDetail.setText(detail);
            vDetail.setVisibility(View.VISIBLE);
        } else {
            vDetail.setVisibility(View.GONE);
        }
    }

    public void setShowInput(boolean isShowInput) {
        this.isShowInput = isShowInput;

        vInput.setVisibility(isShowInput ? View.VISIBLE : View.GONE);

        if (!isShowInput) {
            LayoutParams lp = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.weight = 1;
            vTitle.setLayoutParams(lp);
        } else {
            LayoutParams lp = new LayoutParams(0,
                    LayoutParams.WRAP_CONTENT);
            vTitle.setLayoutParams(lp);
        }
    }

    public void setShowNext(boolean isShowNext) {
        this.isShowNext = isShowNext;
        if (isShowNext) {
            vImgRight.setBackgroundResource(R.mipmap.base_wg_btn_right);
            vImgRight.setVisibility(View.VISIBLE);
        } else if (imgRightId != 0) {
            vImgRight.setBackgroundResource(imgRightId);
            vImgRight.setVisibility(View.VISIBLE);
        } else {
            vImgRight.setVisibility(View.INVISIBLE);
        }
    }

    public void setShowSwitch(boolean isShowSwitch) {
        this.isShowSwitch = isShowSwitch;

        vSwitch.setVisibility(isShowSwitch ? View.VISIBLE : View.GONE);
    }

    public boolean isCheck() {
        return vSwitch.isChecked();
    }

    public void setCheck(boolean isOpen) {
        vSwitch.setChecked(isOpen);
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener l) {
        vSwitch.setOnCheckedChangeListener(l);
    }

}
