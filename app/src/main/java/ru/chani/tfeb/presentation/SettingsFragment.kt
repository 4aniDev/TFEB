package ru.chani.tfeb.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.chani.tfeb.TfebApp
import ru.chani.tfeb.databinding.FragmentSettingsBinding
import ru.chani.tfeb.domain.entity.Language
import javax.inject.Inject

class SettingsFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as TfebApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SettingsViewModel

    private lateinit var onFirstChooseLanguageFinished: OnFirstChooseLanguageFinished
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingsBinding = null")


    override fun onAttach(context: Context) {
        component.inject(this)

        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            RuntimeException("Activity must implement SettingsFragment.OnEditingFinishedListener")
        }

        if (context is OnFirstChooseLanguageFinished) {
            onFirstChooseLanguageFinished = context
        } else {
            RuntimeException("Activity must implement SettingsFragment.OnFirstChooseLanguageFinished")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[SettingsViewModel::class.java]

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            rightCloseScreen()
        }

        setOnClickListeners()
    }



    private fun setOnClickListeners() {
        binding.cvLnEn.setOnClickListener {
            viewModel.setLocale(
                requireActivity(),
                Language.EN
            )
        }

        binding.cvLnTm.setOnClickListener {
            viewModel.setLocale(
                requireActivity(),
                Language.TK
            )
        }

        binding.cvLnRu.setOnClickListener {
            viewModel.setLocale(
                requireActivity(),
                Language.RU
            )
        }
    }

    private fun rightCloseScreen() {
        val appLaunchedEarlier = viewModel.didTheAppLaunchEarlier()
        if (appLaunchedEarlier) {
            onEditingFinishedListener.onSettingsEditingFinished()
        } else {
            onFirstChooseLanguageFinished.onFirstChooseLanguageFinished()
        }
    }

    interface OnFirstChooseLanguageFinished {
        fun onFirstChooseLanguageFinished()
    }

    interface OnEditingFinishedListener {
        fun onSettingsEditingFinished()
    }

    companion object {
        const val SETTINGS_FRAGMENT_NAME = "SETTINGS_FRAGMENT_NAME"

        fun newInstanceSettingsFragment() = SettingsFragment()

    }

}