package lib.frame.module.http;

/**
 * Created by lwxkey on 16/7/7.
 */
public class HttpCode {

    public static final int SUCCESS = 0;//	成功
    public static final int FAIL = 1;//	失败
    public static final int SIGNATURE_ERROE = 2;//签名错误
    public static final int SYS_ERROR = 3;//服务器内部错误
    public static final int SUPPORT_POST_ONLY = 5;//只支持post请求
    public static final int PARAMETER_MISSING = 6;//缺少参数
    public static final int DATA_FORMAT_ERROR = 7;//数据格式错误
    public static final int REQUEST_FORBIDDEN = 8;//请求被禁止
    public static final int REQUEST_ERRO = 9;//请求错误
    public static final int REFERENCE_EXIST = 10;//有引用存在
    public static final int SMS_CODE_SEND_SUCCESS = 1000;//验证码发送成功
    public static final int REGISTER_SUCCESS = 1001;//注册成功
    public static final int REGISTER_FAIL = 1002;//注册失败
    public static final int USERNAME_CAN_NOT_BE_EMPTY = 1003;//用户名不能为空
    public static final int USERNAME_LENGTH_MIN_SIZE_IS_10 = 1004;//用户名长度不能小于10个字符
    public static final int USERNAME_IS_USED = 1005;//用户名被注册
    public static final int PSW_CAN_NOT_BE_EMPTY = 1006;//密码不能为空
    public static final int PSW_MIN_LENGTH_IS_6 = 1007;//密码长度不能小于6个字符
    public static final int MOBILE_VALID = 1008;//手机号不合法
    public static final int MOBILE_EXIST = 1009;//手机号已存在
    public static final int VERIFICATION_CODE_WRONG = 1010;//验证码错误
    public static final int EMAIL_VAILD = 1011;//邮箱不合法
    public static final int EMAIL_EXIST = 1012;//邮箱已存在
    public static final int VERIFICATION_CODE_ERROR = 1013;//验证码错误
    public static final int REGISTER_TYPE_NOT_SUPORTED = 1014;//暂不支持此注册方式
    public static final int LOGIN_SUCCESS = 1100;//登录成功
    public static final int USERNAME_OR_PSW_WRONG = 1101;//用户名或密码错误
    public static final int ACCOUNT_LOCKED = 1102;//您的账号被锁定
    public static final int USER_NOT_EXIST = 1103;//用户不存在
    public static final int USER_NOT_LOGIN = 1104;//用户未登录
    public static final int EXIT_SUCCESS = 1199;//退出成功
    public static final int INFOS_MODIFIED_SUCCESS = 1200;//资料修改成功
    public static final int INFOS_MODIFIED_FAIL = 1201;//资料修改失败
    public static final int PSW_MODIFIED_SUCCESS = 1202;//密码修改成功
    public static final int PSW_WRONG = 1203;//当前密码不正确
    public static final int PSW_RESET_SUCCESS = 1204;//密码重置成功
    public static final int BANDING_SUCCESS = 1205;//绑定成功
    public static final int MOBILE_MODIFIED = 1206;//手机更改成功
    public static final int EMAIL_BANDING_SUCCESS = 1207;//邮箱绑定成功
    public static final int EMAIL_MODIFIED_SUCCESS = 1208;//邮箱更改成功
    public static final int UNBANDING_SUCCESS = 1209;//解绑成功
    public static final int SMS_BANDING_SUCCESS = 1220;//SNS绑定成功
    public static final int SMS_BANDING_FAIL = 1221;//SNS绑定失败
    public static final int ACCOUNT_BANDING_NOT_SUPORT_THIS_PLATFORM = 1222;//暂不支持此平台账号绑定
    public static final int GETTING_INFOS_SUCCESS = 1230;//资料获取成功
    public static final int BANDING_MOBILE_FIRST = 1300;//请先绑定手机号
    public static final int INSUFFICIENT_BALANCE = 1301;//余额不足
    public static final int INSUFFICIENT_BALANCE_WITHDRAW = 1302;//余额不满
    public static final int APPEND_SERVICE_ADDED = 1501;//附加服务项目名称已添加，不能重复添加!
    public static final int PAYMENT_ADDED = 1502;//支付方式名称已添加，不能重复添加!
    public static final int COUNTRY_ADDED = 1503;//国家地区名称名称已添加，不能重复添加!
    public static final int CAR_TYPE_PRICE_ADDED = 1504;//该地区车型价格设置已添加，不能重复添加!
    public static final int ADD_DRIVER_NONE = 1505;//添加司机查询不存在!
    public static final int ADD_MY_DRIVER_EXIST = 1506;//添加司机查询不存在!
    public static final int ADD_BLACKED_EXIST = 1507;//	添加拉黑司机已存在,不能重复添加!
    public static final int ORDER_IS_GONE = 1601;//	此订单已经被抢，请游览其他订单!
    public static final int ORDER_IS_GETTED = 1602;//	抢单成功!
}
