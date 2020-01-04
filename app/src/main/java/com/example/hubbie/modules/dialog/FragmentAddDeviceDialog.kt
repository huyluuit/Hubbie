package com.example.hubbie.modules.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.hubbie.R
import com.example.hubbie.entities.Device
import com.example.hubbie.entities.DeviceSorted
import com.example.hubbie.modules.base.view.BaseDialogFragment
import com.example.hubbie.utilis.GeneralUtils
import com.example.hubbie.utilis.firestore.FirestoreDeviceUtil
import com.example.hubbie.utilis.realtime.FirebaseRealtimeDevice
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class FragmentAddDeviceDialog(private val uid: String) : BaseDialogFragment() {

    companion object {
        private const val REQUEST_PERMISSION = 0x01
        const val SSID = "SSID"
        const val BSSID = "BSSID"
        const val PWD = "PASSWORD"
    }

    private lateinit var etSsid: EditText
    private lateinit var etPwd: EditText
    private lateinit var tvState: TextView
    private lateinit var tvBssid: TextView
    private lateinit var tvIp: TextView
    private lateinit var btnStartSmartConfig: Button
    private lateinit var btnOk: Button
    private val compositeDisposable = CompositeDisposable()
    private var isPermissionGranted = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val v = activity?.layoutInflater?.inflate(R.layout.fragment_add_device_dialog, null)
        val wifiInfo = GeneralUtils.getSSID(context)
        if (v != null) {
            etSsid = v.findViewById(R.id.etSsid)
            etPwd = v.findViewById(R.id.etPwd)
            etSsid.setText(wifiInfo.second.substring(1, wifiInfo.second.length - 1))
            tvState = v.findViewById(R.id.tvState)
            tvBssid = v.findViewById(R.id.tvBSSID)
            tvIp = v.findViewById(R.id.tvIp)
            btnStartSmartConfig = v.findViewById(R.id.btnStartSmartConfig)
            btnOk = v.findViewById(R.id.btnOk)
            btnOk.setOnClickListener {
                compositeDisposable.dispose()
            }
            btnStartSmartConfig.setOnClickListener {
                requestLocationPermission()
                if (isPermissionGranted) {
                    if (etSsid.text.toString().isNotEmpty()) {
                        if (etPwd.text.toString().isNotEmpty()) {
                            showProcessLoading("Đang quét thiết bị...")
                            val ssidByte = wifiInfo.second.substring(1, wifiInfo.second.length - 1)
                            val bssidByte = wifiInfo.first
                            val pwdByte = etPwd.text.toString()
                            val data: HashMap<String, String> = HashMap()
                            data[SSID] = ssidByte
                            data[BSSID] = bssidByte
                            data[PWD] = pwdByte
                            val smartConfigDisposable =
                                GeneralUtils.initialSmartConfig(data, context)
                                    .timeout(60, TimeUnit.SECONDS)
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                                        onSuccess = {
                                            val macDevice = it.first
                                            val ipDevice = it.second.substring(1, it.second.length)
                                            val device = Device(macDevice, ipDevice)
                                            val deviceSorted = DeviceSorted(macDevice, ipDevice)
                                            var isFirestoreComplete = false
                                            var isRealtimeComplete = false
                                            val firestoreDisposable =
                                                FirestoreDeviceUtil.setDevice(uid, deviceSorted)
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribeBy(
                                                        onComplete = {
                                                            Log.d("SetDeviceToFS", "Success")
                                                            isFirestoreComplete = true
                                                            if (isRealtimeComplete) {
                                                                dismissProcessLoading()
                                                            }
                                                        },
                                                        onError = {
                                                            Log.d("SetDeviceToFS", "Failed")
                                                        }
                                                    )
                                            val realtimeDisposable =
                                                FirebaseRealtimeDevice.setDevice(device)
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribeBy(
                                                        onComplete = {
                                                            Log.d("SetDeviceToRT", "Success")
                                                            isRealtimeComplete = true
                                                            if (isFirestoreComplete) {
                                                                dismissProcessLoading()
                                                            }
                                                        },
                                                        onError = {
                                                            Log.d("SetDeviceToRT", "Success")
                                                        }
                                                    )
                                            compositeDisposable.add(firestoreDisposable)
                                            compositeDisposable.add(realtimeDisposable)
                                            tvState.visibility = View.VISIBLE
                                            tvState.text = "Thêm thiết bị thành công!"
                                            tvBssid.text = "BSSID: " + it.first
                                            tvIp.text = "IP: " + it.second
                                        },
                                        onError = {
                                            tvState.visibility = View.VISIBLE
                                            tvState.text =
                                                "Thêm thiết bị thất bại! \n Lỗi: ${it.cause.toString()}"
                                            dismissProcessLoading()
                                        }
                                    )
                            compositeDisposable.add(smartConfigDisposable)
                        } else {
                            GeneralUtils.showingToast(context, "Password còn trống!")
                        }
                    } else {
                        GeneralUtils.showingToast(context, "SSID còn trống!")
                    }
                } else {
                    GeneralUtils.showingToast(context, "Permission was not granded")
                }
            }
        }

        val dialog = AlertDialog.Builder(activity).setView(v).create()
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        return dialog

    }

    interface DialogBtnClick {
        fun onSmartConfigClick()
        fun onOkClick()
    }

    fun requestLocationPermission() {
        if (this.context!!.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = false
            val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(this.activity!!, permissions, REQUEST_PERMISSION)
        } else {
            isPermissionGranted = true
            btnStartSmartConfig.isEnabled = true
        }
    }

}