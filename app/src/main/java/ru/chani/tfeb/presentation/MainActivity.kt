package ru.chani.tfeb.presentation

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import ru.chani.tfeb.R

class MainActivity : AppCompatActivity(), CardItemFragment.OnEditingAndOnAddFinishedListener {


    private lateinit var sharedPreference: SharedPreferences
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkAllPermissions()

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        initSharedPreference()
        setRightFragment()
    }

    private fun setRightFragment() {
        if (WasTheAppLaunchedEarlier()) {
            Log.d("RUN", "запускалось")
            if (isCardDefault()) {
                launchFragmentNewCard()
            } else {
                launchMainFragment()
            }
        } else {
            Log.d("RUN", "ПЕРВЫЙ ЗАПУСК")
            putRecordThatAppWasRunToSharedPreference()
            viewModel.generateDefaultCard()
            launchFragmentNewCard()
        }
    }

    fun launchMainFragment() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_container,
                MainFragment.newInstanceMainFragment()
            )
            .commit()
    }

    fun launchFragmentNewCard() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_container,
                CardItemFragment.newInstanceAddNewCardItemFragment()
            )
            .commit()
    }

    override fun onEditingFinished() {
        onBackPressed()
    }

    override fun onAddFinished() {
        launchMainFragment()
        putRecordThatCardIsNotDefault()
    }

    private fun initSharedPreference() {
        sharedPreference = getSharedPreferences(SHARED_PREFERENCE_NAME_RUN_APP_COUNT, MODE_PRIVATE)
    }

    private fun putRecordThatAppWasRunToSharedPreference() {
        val editor = sharedPreference.edit()
        editor.putBoolean(SHARED_PREFERENCE_KEY_WAS_APP_RUN, SHARED_PREFERENCE_VALUE_APP_WAS_RUN)
        editor.apply()
    }

    private fun putRecordThatCardIsNotDefault() {
        sharedPreference.edit().apply {
            putBoolean(
                SHARED_PREFERENCE_KEY_IS_DEFAULT_CARD,
                SHARED_PREFERENCE_VALUE_CARD_IS_NOT_DEFAULT
            )
        }.apply()
    }

    private fun isCardDefault(): Boolean {
        return sharedPreference.getBoolean(
            SHARED_PREFERENCE_KEY_IS_DEFAULT_CARD,
            SHARED_PREFERENCE_VALUE_CARD_IS_DEFAULT
        )
    }

    private fun WasTheAppLaunchedEarlier(): Boolean {
        return sharedPreference.getBoolean(
            SHARED_PREFERENCE_KEY_WAS_APP_RUN,
            SHARED_PREFERENCE_VALUE_APP_WAS_RUN_DEFAULT
        )
    }

    private fun checkAllPermissions() {
        checkPermission(
            PERMISSION_READ_SMS,
            MY_PERMISSIONS_REQUEST_READ_CODE
        )
        checkPermission(
            PERMISSION_SEND_SMS,
            MY_PERMISSIONS_REQUEST_SEND_SMS
        )
    }

    private fun checkPermission(permission: String, permissionRequestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                permissionRequestCode
            )
        }
    }


    companion object {
        private const val SHARED_PREFERENCE_NAME_RUN_APP_COUNT =
            "SHARED_PREFERENCE_NAME_RUN_APP_COUNT"


        private const val SHARED_PREFERENCE_KEY_WAS_APP_RUN = "IS APP RUN EARLY?"
        private const val SHARED_PREFERENCE_VALUE_APP_WAS_RUN = true
        private const val SHARED_PREFERENCE_VALUE_APP_WAS_RUN_DEFAULT = false

        private const val SHARED_PREFERENCE_KEY_IS_DEFAULT_CARD = "KEY for CARD_INFO"
        private const val SHARED_PREFERENCE_VALUE_CARD_IS_NOT_DEFAULT = false
        private const val SHARED_PREFERENCE_VALUE_CARD_IS_DEFAULT = true



        private const val MY_PERMISSIONS_REQUEST_SEND_SMS = 0
        private const val MY_PERMISSIONS_REQUEST_READ_CODE = 1

        private const val PERMISSION_READ_SMS: String = Manifest.permission.READ_SMS
        private const val PERMISSION_SEND_SMS: String = Manifest.permission.SEND_SMS
    }


    //        for RECEIVE
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
//            != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this,
//                arrayOf(Manifest.permission.RECEIVE_SMS),
//                requestReceiveSms)
//        }
//    }


}