package ru.chani.tfeb.data.sms

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.telephony.SmsManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SmsSender {


    fun sendSms(lastFourNumbersOfCardToMessage: String, activity: Activity) {
        checkSendSmsPermission(activity)

        val smsManager = SmsManager.getDefault()
        val smsTextForGetBalance = TFEB_SMS_KEY_WORD_FOR_GET_BALANCE + lastFourNumbersOfCardToMessage
        smsManager.sendTextMessage(
            TFEB_SMS_NUMBER_FOR_CHECK_BALANCE,
            null,
            smsTextForGetBalance,
            null,
            null
        )
    }

    private fun checkSendSmsPermission(activity: Activity) {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.SEND_SMS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.SEND_SMS
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.SEND_SMS),
                    MY_PERMISSIONS_REQUEST_SEND_SMS
                )
            }
        }
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_SEND_SMS = 0

        private const val TFEB_SMS_NUMBER_FOR_CHECK_BALANCE = "+0717"
        private const val TFEB_SMS_KEY_WORD_FOR_GET_BALANCE = "BAL"
    }
}