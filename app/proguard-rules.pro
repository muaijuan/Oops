# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 指定代码的压缩级别
-optimizationpasses 5
# 是否使用大小写混合
-dontusemixedcaseclassnames
# 是否混淆第三方jar
-dontskipnonpubliclibraryclasses
# 混淆时是否做预校验
-dontpreverify
# 混淆时是否记录日志
-verbose
# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#忽略警告
-ignorewarning
-printmapping proguardMapping.txt
#-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes SourceFile,LineNumberTable
-keepattributes EnclosingMethod
-keepattributes Signature

# 保持哪些类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}

#gson解析不被混淆
-keep class com.google.**{*;}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#---------------------------------实体类---------------------------------
-keep class com.muaj.walli.entity.** { *; }
#---------------------------------自定义控件---------------------------------
-keep class com.muaj.walli.widgets.** { *; }


##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# Gson specific classes
-keep class sun.misc.Unsafe {*;}
#-keep class com.google.gson.stream.** {*;}
# Application classes that will be serialized/deserialized over Gson
-dontwarn com.u14studio.entity.**
-keep class com.u14studio.entity.**{*;}
##---------------End: proguard configuration for Gson  ----------

## glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

### OkHttp3
#-dontwarn okhttp3.logging.**
#-keep class okhttp3.internal.**{*;}
#-dontwarn okio.**
#-dontwarn javax.annotation.**
#-dontwarn javax.inject.**
### Retrofit2
#-dontwarn retrofit2.**
#-keep class retrofit2.** { *; }
##-keepattributes Signature-keepattributes Exceptions
### RxJava RxAndroid
#-dontwarn sun.misc.**
#-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
#    long producerIndex;
#    long consumerIndex;
#}
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
#    rx.internal.util.atomic.LinkedQueueNode producerNode;
#}
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
#    rx.internal.util.atomic.LinkedQueueNode consumerNode;
#}
#
### greendao
#-keep class de.greenrobot.dao.** {*;}
#-keep class org.greenrobot.greendao.** {*;}
#-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
#    public static Java.lang.String TABLENAME;
#}
#-keep class **$Properties
#
### eventbus
#-keepclassmembers class ** {
#    @org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#
## Only required if you use AsyncExecutor
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#    <init>(Java.lang.Throwable);
#}
#
#
### zxing
#-keep class com.google.zxing.** {*;}
#-keep class com.journeyapps.barcodescanner.** {*;}
#-dontwarn com.google.zxing.**
#
### arouter
#-keep public class com.alibaba.android.arouter.routes.**{*;}
#-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
#
##--------------------------------- webview ---------------------------------
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#
##--------------------------------- umeng ---------------------------------
#-keep class com.umeng.commonsdk.** {*;}