package ru.chani.tfeb.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.chani.tfeb.di.anotations.ViewModelKey
import ru.chani.tfeb.presentation.CardItemViewModel
import ru.chani.tfeb.presentation.MainActivityViewModel
import ru.chani.tfeb.presentation.MainFragmentViewModel
import ru.chani.tfeb.presentation.SettingsViewModel

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    @Binds
    fun bindMainFragmentViewModel(impl: MainFragmentViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    @Binds
    fun bindMainActivityViewModel(impl: MainActivityViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CardItemViewModel::class)
    @Binds
    fun bindCardItemViewModel(impl: CardItemViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    @Binds
    fun bindSettingsViewModel(impl: SettingsViewModel): ViewModel

}