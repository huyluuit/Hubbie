package com.example.hubbie.modules.device

import com.example.hubbie.entities.Device

interface IDevice {
    interface Presenter {
        fun deviceItemClicked(deviceId: Int)
        fun createDeviceClicked()
        fun getAllDevices(): ArrayList<Device>
    }

    interface Interactor {
        fun getDevice(deviceId: Int): Device
        fun getAllDevice(): ArrayList<Device>
    }

    interface Route {
        fun navigateToLogin()
        fun navigateToAccountInfo()
        fun navigateToCreateDevice()
        fun navigateToDeviceDetail()
        fun navigateToChat()
    }

    interface View {
        fun onItemClicked()
        fun createDeviceClicked()
    }
}