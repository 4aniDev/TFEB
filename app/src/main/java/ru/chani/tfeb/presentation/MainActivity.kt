package ru.chani.tfeb.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import ru.chani.tfeb.R

class MainActivity :
    AppCompatActivity(),
    CardItemFragment.OnEditingAndOnAddFinishedListener,
    SettingsFragment.OnEditingFinishedListener {


    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkAllPermissions()
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        setRightFragment()
    }

    private fun setRightFragment() {
        if (viewModel.didTheAppLaunchEarlier()) {
            secondAppLaunch()
        } else {
            firstAppLaunch()
        }
    }

    private fun firstAppLaunch() {
        viewModel.putRecordThatAppWasRun()
        viewModel.generateDefaultCard()
        launchFragmentNewCard()
    }

    private fun secondAppLaunch() {
        if (viewModel.isDefaultCard()) {
            launchFragmentNewCard()
        } else {
            launchMainFragment()
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

    override fun onSettingsEditingFinished() {
        onBackPressed()
    }

    override fun onAddFinished() {
        viewModel.putRecordThatCardIsNotDefault()
        launchMainFragment()
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