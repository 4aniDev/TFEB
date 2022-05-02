package ru.chani.tfeb.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.chani.tfeb.R
import ru.chani.tfeb.databinding.FragmentCardItemBinding
import ru.chani.tfeb.domain.entity.CardEntity.Companion.DEFAULT_CARD_NUMBER

class CardItemFragment : Fragment() {

    private lateinit var viewModel: CardItemViewModel

    private lateinit var onEditingAndOnAddFinishedListener: OnEditingAndOnAddFinishedListener

    private var _binding: FragmentCardItemBinding? = null
    private val binding: FragmentCardItemBinding
        get() = _binding ?: throw RuntimeException("FragmentEditBinding == null")

    private var screenMode = MODE_UNKNOWN
    private lateinit var cardNumber: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingAndOnAddFinishedListener) {
            onEditingAndOnAddFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingAndOnAddFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CardItemViewModel::class.java]
        launchRightMode()
        addChangeListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun launchAddMode() {
        viewModel.getCardItemByCardNumber(DEFAULT_CARD_NUMBER)
        setObserverForCloseScreen()
        setErrorObservers()
        binding.buttonSave.setOnClickListener {
            viewModel.editCardItem(
                inputCardNumber = binding.etCardNumber?.text.toString(),
                inputPersonName = binding.etPersonName?.text.toString(),
                inputPersonSurname = binding.etPersonSurname?.text.toString()
            )
        }
    }

    private fun launchEditMode() {
        viewModel.getCardItemByCardNumber(cardNumber)
        setObserversForTextFields()
        setObserverForCloseScreen()
        setErrorObservers()
        binding.buttonSave.setOnClickListener {
            viewModel.editCardItem(
                inputCardNumber = binding.etCardNumber?.text.toString(),
                inputPersonName = binding.etPersonName?.text.toString(),
                inputPersonSurname = binding.etPersonSurname?.text.toString()
            )
        }
    }


    private fun setObserverForCloseScreen() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            when (screenMode) {
                MODE_ADD -> onEditingAndOnAddFinishedListener.onAddFinished()
                MODE_EDIT -> onEditingAndOnAddFinishedListener.onEditingFinished()
            }
        }
    }

    private fun setObserversForTextFields() {
        viewModel.cardItem.observe(viewLifecycleOwner) {
            with(binding) {
                etCardNumber.setText(it.numberOfCard)
                etPersonName.setText(it.personName)
                etPersonSurname.setText(it.personSurname)
            }
        }
    }

    private fun setErrorObservers() {
        viewModel.errorInputCardNumber.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_card_number)
            } else {
                null
            }
            binding.tilCardNumber.error = message
        }

        viewModel.errorInputCardPersonName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_card_person_name)
            } else {
                null
            }
            binding.tilPersonName.error = message
        }

        viewModel.errorInputCardPersonSurname.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_card_person_surname)
            } else {
                null
            }
            binding.tilPersonSurname.error = message
        }
    }


    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(CARD_NUMBER)) {
                throw RuntimeException("Param card number is absent")
            }
            cardNumber = args.getString(CARD_NUMBER, DEFAULT_CARD_NUMBER)
        }
    }

    private fun addChangeListeners() {
        binding.etCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCardNumber()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etPersonName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCardPersonName()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etPersonSurname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCardPersonSurname()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    interface OnEditingAndOnAddFinishedListener {
        fun onEditingFinished()

        fun onAddFinished()
    }

    companion object {
        const val CARD_ITEM_FRAGMENT_NAME = "Card Item Fragment NAME"

        private const val SCREEN_MODE = "EXTRA_SCREEN_MODE"
        private const val CARD_NUMBER = "EXTRA_CARD_NUMBER"

        private const val MODE_ADD = "mode add"
        private const val MODE_EDIT = "mode edit"
        private const val MODE_UNKNOWN = ""


        fun newInstanceAddNewCardItemFragment(): CardItemFragment {
            return CardItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditCardItemFragment(cardNumber: String): CardItemFragment {
            return CardItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putString(CARD_NUMBER, cardNumber)
                }
            }
        }
    }
}