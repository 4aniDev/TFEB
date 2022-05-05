package ru.chani.tfeb.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.chani.tfeb.databinding.FragmentSettingsBinding
import ru.chani.tfeb.domain.entity.Language

class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingsBinding = null")


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SettingsFragment.OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            RuntimeException("Activity must implement SettingsFragment.OnEditingFinishedListener")
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
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onSettingsEditingFinished()
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

    interface OnEditingFinishedListener {
        fun onSettingsEditingFinished()
    }

    companion object {
        const val SETTINGS_FRAGMENT_NAME = "SETTINGS_FRAGMENT_NAME"

        fun newInstanceSettingsFragment() = SettingsFragment()

    }

}