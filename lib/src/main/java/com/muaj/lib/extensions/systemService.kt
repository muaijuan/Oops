package com.muaj.lib.extensions

import android.annotation.TargetApi
import android.app.ActivityManager
import android.bluetooth.BluetoothManager
import android.content.ClipboardManager
import android.content.Context
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.telephony.TelephonyManager
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

/**
 * Created by muaj on 2018/6/5
 * SystemService 各种manager
 */
val Context.activityManager
    get() = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?

val Context.wifiManager
    get() = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?

val Context.windowManager
    get() = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager?

val Context.batteryManager
    get() = applicationContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager?

val Context.bluetoothManager
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    get() = applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?

val Context.cameraManager
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    get() = applicationContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager?

val Context.clipboardManager
    get() = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?

val Context.connectivityManager
    get() = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

val Context.inputMethodManager
    get() = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?

val Context.telephonyManager
    get() = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?

val Context.audioManager
    get() = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
