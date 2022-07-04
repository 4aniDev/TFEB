package ru.chani.tfeb.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import ru.chani.tfeb.R
import ru.chani.tfeb.TfebApp
import javax.inject.Inject

class MainActivity :
    AppCompatActivity(),
    CardItemFragment.OnEditingAndOnAddFinishedListener,
    SettingsFragment.OnFirstChooseLanguageFinished,
    SettingsFragment.OnEditingFinishedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainActivityViewModel

    private val component by lazy {
        (application as TfebApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkAllPermissions()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
        viewModel.setLanguage(this)
        setRightFragment()
    }

    private fun setRightFragment() {
        val appLaunchedEarlier = viewModel.didTheAppLaunchEarlier()
        if (appLaunchedEarlier) {
            secondLaunchApp()
        } else {
            firstLaunchApp()
        }
    }

    private fun firstLaunchApp() {
        launchSettingsFragment()
        viewModel.generateDefaultCard()
    }

    private fun secondLaunchApp() {
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


    fun launchSettingsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_container,
                SettingsFragment.newInstanceSettingsFragment()
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

    override fun onFirstChooseLanguageFinished() {
        viewModel.putRecordThatAppWasRun()
        launchFragmentNewCard()
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