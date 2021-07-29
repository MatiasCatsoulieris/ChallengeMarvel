package android.example.com.challengemarvel.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.challengemarvel.R
import android.example.com.challengemarvel.core.Resource
import android.example.com.challengemarvel.databinding.FragmentRegisterBinding
import android.example.com.challengemarvel.presentation.AccountViewModel
import android.example.com.challengemarvel.utils.hide
import android.example.com.challengemarvel.utils.hideKeyboard
import android.example.com.challengemarvel.utils.isValidEmail
import android.example.com.challengemarvel.utils.show
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : Fragment() {

    //ViewBinding
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel by viewModel<AccountViewModel>()
    //TextWatcher
    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateInfo()
            binding.mailEditLayout.error = null
            binding.passwordEditLayout.error = null
            binding.confirmPasswordEditLayout.error = null
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUiListeners()
    }

    private fun setUiListeners() {
        binding.mailEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)
        binding.confirmPasswordEditText.addTextChangedListener(textWatcher)

        binding.registerButton.setOnClickListener {
            if(validateInfo()) {
                val mail = binding.mailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                registerUser(mail, password)
            }
        }
        binding.loginTextView.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }

    private fun validateInfo(): Boolean {
        return if (binding.mailEditText.text.toString().isValidEmail()
            && binding.passwordEditText.text.toString().length > 5
            && binding.confirmPasswordEditText.text.toString() == binding.passwordEditText.text.toString()
        ) {
            binding.registerButton.isEnabled = true
            true
        } else {
            binding.registerButton.isEnabled = false
            false
        }
    }

    private fun registerUser(mail: String, password: String) {
        binding.root.hideKeyboard()
        viewModel.customRegister(mail, password).observe(viewLifecycleOwner, { resource ->
            when(resource) {
                is Resource.Loading -> {
                    binding.loadingProgressBar.show()
                }
                is Resource.Failure -> {
                    binding.loadingProgressBar.hide()
                    binding.mailEditLayout.error = " "
                    binding.passwordEditLayout.error = " "
                    binding.confirmPasswordEditLayout.error = " "
                    Toast.makeText(requireContext(), resource.exception.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    binding.loadingProgressBar.hide()
                    binding.mailEditText.text?.clear()
                    binding.passwordEditText.text?.clear()
                    binding.confirmPasswordEditText.text?.clear()
                    Navigation.findNavController(binding.root).navigate(RegisterFragmentDirections.actionRegisterToHome())
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}