package ru.chani.tfeb.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.chani.tfeb.R
import ru.chani.tfeb.databinding.FragmentMainBinding
import ru.chani.tfeb.domain.entity.CardEntity


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

    private lateinit var viewModel: MainFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        checkAllPermission()
        viewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]



        viewModel.cardLd.observe(viewLifecycleOwner) {
            with(binding) {
                tvBalance.text = it.balance
                tvCurrency.text = viewModel.getCurrency()
                tvHelloName.text = it.personName
                tvHelloName.text = viewModel.getGreetingPhrase(requireContext())
                viewModel.updateBalanceInTheCard(requireContext())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun goToCardItemFragment() {
        parentFragmentManager.beginTransaction()
            .addToBackStack(CardItemFragment.CARD_ITEM_FRAGMENT_NAME)
            .replace(
                R.id.main_container,
                CardItemFragment.newInstanceEditCardItemFragment(
                    viewModel.cardLd.value?.numberOfCard ?: CardEntity.DEFAULT_CARD_NUMBER
                )
            )
            .commit()
    }


    private fun setOnClickListeners() {
        binding.buttonCheck.setOnClickListener { viewModel.sendSms(requireActivity()) }
        binding.cvBalance.setOnClickListener { goToCardItemFragment() }
    }


    private fun checkAllPermission() {
        checkPermission(PERMISSION_READ_SMS, MY_PERMISSIONS_REQUEST_READ_CODE)
        checkPermission(PERMISSION_SEND_SMS, MY_PERMISSIONS_REQUEST_SEND_SMS)
    }

    private fun checkPermission(permission: String, permissionRequestCode: Int) {
        if (ContextCompat.checkSelfPermission(requireActivity(), permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(permission),
                permissionRequestCode
            )
        }
    }


    companion object {
        const val MAIN_FRAGMENT_NAME = "Main Fragment"

        private const val MY_PERMISSIONS_REQUEST_SEND_SMS = 0
        private const val MY_PERMISSIONS_REQUEST_READ_CODE = 1


        private const val PERMISSION_READ_SMS: String = Manifest.permission.READ_SMS
        private const val PERMISSION_SEND_SMS: String = Manifest.permission.SEND_SMS


        fun newInstanceMainFragment(): MainFragment {
            return MainFragment()
        }
    }
}
