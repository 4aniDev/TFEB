package ru.chani.tfeb

import android.app.Application
import ru.chani.tfeb.di.components.DaggerApplicationComponent

class TfebApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}