package ru.chani.tfeb.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.chani.tfeb.di.anotations.ApplicationScope
import ru.chani.tfeb.di.modules.DataModule
import ru.chani.tfeb.di.modules.DomainModule
import ru.chani.tfeb.di.modules.ViewModelModule
import ru.chani.tfeb.presentation.CardItemFragment
import ru.chani.tfeb.presentation.MainActivity
import ru.chani.tfeb.presentation.MainFragment
import ru.chani.tfeb.presentation.SettingsFragment

@ApplicationScope
@Component(
    modules = [
        DomainModule::class,
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(mainFragment: MainFragment)

    fun inject(cardItemFragment: CardItemFragment)

    fun inject(settingsFragment: SettingsFragment)


    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}