package android.example.com.challengemarvel.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.challengemarvel.R
import android.example.com.challengemarvel.core.Resource
import android.example.com.challengemarvel.databinding.FragmentRecoverPasswordBinding
import android.example.com.challengemarvel.presentation.AccountViewModel
import android.example.com.challengemarvel.utils.hideKeyboard
import android.example.com.challengemarvel.utils.isValidEmail
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecoverPasswordFragment : Fragment() {

    //Binding
    private var _binding: FragmentRecoverPasswordBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel by viewModel<AccountViewModel>()

    //TextWatcher
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateInfo()
            binding.mailEditLayout.error = null

        }

        override fun afterTextChanged(s: Editable?) {
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecoverPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.mailEditText.addTextChangedListener(textWatcher)
        binding.sendEmailButton.setOnClickListener {
            if (validateInfo()) {
                val email = binding.mailEditText.text.toString()
                sendEmail(email)
            }
        }
    }

    private fun sendEmail(email: String) {
        binding.root.hideKeyboard()
        viewModel.sendEmail(email).observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.sendEmailButton.isEnabled = false
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.mailEditText.text?.clear()
                    binding.loadingProgressBar.visibility = View.GONE
                    Navigation.findNavController(binding.root)
                        .navigateUp()

                }
                is Resource.Failure -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.mailEditLayout.error = " "
                    Toast.makeText(
                        requireContext(),
                        resource.exception.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }


    private fun validateInfo(): Boolean {
        return if (binding.mailEditText.text.toString().isValidEmail()) {
            binding.sendEmailButton.isEnabled = true
            true
        } else {
            binding.sendEmailButton.isEnabled = false
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}