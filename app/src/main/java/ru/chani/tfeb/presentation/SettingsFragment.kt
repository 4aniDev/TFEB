package ru.chani.tfeb.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.chani.tfeb.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingsBinding = null")

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
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.cvLnEn.setOnClickListener {
            viewModel.setLocale(
                requireActivity(),
                SettingsViewModel.LANGUAGE_CODE_EN
            )
        }

        binding.cvLnRu.setOnClickListener {
            viewModel.setLocale(
                requireActivity(),
                SettingsViewModel.LANGUAGE_CODE_RU
            )
        }
    }


    companion object {
        const val SETTINGS_FRAGMENT_NAME = "SETTINGS_FRAGMENT_NAME"

        fun newInstanceSettingsFragment() = SettingsFragment()

    }

}