package com.example.hubbie.utilis

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import com.espressif.iot.esptouch.EsptouchTask
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.*


object GeneralUtils {

    const val SSID = "SSID"
    const val BSSID = "BSSID"
    const val PWD = "PASSWORD"

    fun isSDKAtLeastP(): Boolean {
        return Build.VERSION.SDK_INT >= 28
    }

    fun initialSmartConfig(
        data: HashMap<String, String>,
        context: Context?
    ): Single<Pair<String, String>> {

        val ssid = data[SSID]
        val bssid = data[BSSID]
        val pwd = data[PWD]

        Log.e(
            "SMARTCONFIG ERROR",
            "ssid size: ${ssid}, bssid size: ${bssid}, pwd size: ${pwd}, context equal null?: ${context?.equals(
                null
            )} "
        )

        return Single.create {
            if (ssid != null && bssid != null && pwd != null && context != null) {
                val espTask = EsptouchTask(ssid, bssid, pwd, context)

                espTask.setEsptouchListener { p0 ->
                    Log.e("ESP Touch", "ESP LISTENTER success?: ${p0.isSuc}")
                    if (p0 != null) {
                        if (p0.isSuc) {
                            val result = Pair(p0.bssid, p0.inetAddress.toString())
                            it.onSuccess(result)
                        }
                    } else {
                        it.onError(Throwable("ESP Result is null!"))
                    }
                }
                espTask.executeForResult()
            } else {
                it.onError(Throwable("ESP input have null!"))
                Log.e(
                    "SMARTCONFIG ERROR",
                    "ssid size: ${ssid}, bssid size: ${bssid}, pwd size: ${pwd}, context equal null?: ${context?.equals(
                        null
                    )} "
                )
            }
        }
    }

    fun getSSID(context: Context?): Pair<String, String> {
        var ssid = ""
        var bssid = ""

        if (context != null) {
            val wifiManager = (context.applicationContext
                .getSystemService(Context.WIFI_SERVICE) as WifiManager)
            val info = wifiManager.connectionInfo
            val disconnected = info == null || info.networkId == -1 || "<unknown ssid>" == info.ssid
            if (disconnected) {
                Log.e("getSSID", "Wifi not connected!")
            } else {
                ssid = info.ssid
                bssid = info.bssid
                Log.d("getSSID", "SSID: $ssid, BSSID: $bssid")
                Pair(bssid, ssid)
            }
        }
        return Pair(bssid, ssid)
    }

    fun showHideLayout(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }

    fun showingToast(context: Context?, message: String?) {
        if (context != null && message != null && message.isNotEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun getTimeId(): String {
        val date = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("yyyyMMddHHmmssSSSS", Locale.getDefault())
        val timeLong = java.lang.Long.parseLong(simpleDateFormat.format(date))
        return timeLong.toString()
    }
}