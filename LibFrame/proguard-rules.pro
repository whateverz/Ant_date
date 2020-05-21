## Add project specific ProGuard rules here.
## By default, the flags in this file are appended to flags specified
## in D:\Program Files\android\android-sdk/tools/proguard/proguard-android.txt
## You can edit the include path and order by changing the proguardFiles
## directive in build.gradle.
##
## For more details, see
##   http://developer.android.com/guide/developing/tools/proguard.html
#
## Add any project specific keep options here:
#
## If your project uses WebView with JS, uncomment the following
## and specify the fully qualified class name to the JavaScript interface
## class:
##-keepclassmembers class fqcn.of.javascript.interface.for.webview {
##   public *;
##}
#-dontshrink
#
#-keep class *.R
#-keepclasseswithmembers class **.** {*;}
#
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
# public *;
#}
#-keepattributes *Annotation*
#
#-keepclassmembers class * extends com.sea_monster.dao.AbstractDao {
# public static java.lang.String TABLENAME;
#}
#
#-keep class **$Properties
#-dontwarn org.eclipse.jdt.annotation.**
#
#-keep class com.ultrapower.** {*;}
#
#-keepclassmembers class **.R$* {
#    public static <fields>;
#}
#
#-keepclassmembers class * {
#   public <init>(org.json.JSONObject);
#}
#
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#
#-keep public class com.tencent.bugly.**{*;}
#-dontwarn com.tencent.bugly.**
#
#-keep class * implements android.os.Parcelable {
#public static final android.os.Parcelable$Creator *;
#}
#
#
## For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
#-keep class **.module.ui.** {*;}
#-keep class **.bean.** {*;}
#-keep class **.R
#-keep class **.R$* {
#    <fields>;
#}
#
#-keepattributes Signature,Exceptions,InnerClasses,LineNumberTable,LocalVariableTable
#
##-keep class com.baidu.mobads.** {
##  public protected *;
##}
#
#-dontwarn okio.**
#-dontwarn com.squareup.okhttp.**
##-dontwarn com.baidu.location.**
#
#-keep class com.squareup.okhttp.** { *;}
##-keep class com.baidu.location.** {*;}
#-keep class org.lasque.tusdk.**{public *; protected *; }
#
#-dontwarn com.bigkoo.pickerview.**
#
#
##EventBus start
#-keep class * extends java.lang.annotation.Annotation
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
#-keepclassmembers class ** {
#    public void onEvent*(**);
#}
#
#-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}
##EventBus end
#
##baidu map start
#-keep class com.baidu.mobads.** {
#  public protected *;
#}
#-dontwarn com.baidu.location.**
#-keep class com.baidu.location.** {*;}
##baidu map end
#
##弹框
#-keep class android.** {*;}
#-keep class **.github.** {*;}
#-keep class **.afollestad.** {*;}
#-keep class **.nostra13.** {*;}
#
#-dontwarn  **.afollestad.**
#
######
#-keep class org.** {*;}
#-dontwarn org.**
