package android.example.com.challengemarvel.ui.tabs

import android.example.com.challengemarvel.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.challengemarvel.databinding.FragmentHomeBinding
import android.example.com.challengemarvel.presentation.MainViewModel
import android.example.com.challengemarvel.utils.AnimUtils
import androidx.activity.addCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    //ViewBinding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //Adapter
    private val pagingAdapter = CharacterListAdapter()

    //ViewModel
    private val viewModel by viewModel<MainViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUi()

        setUiListener()
        getCharacters()
    }

    private fun setUi() {
        binding.recyclerViewCharacter.adapter = pagingAdapter
        NavigationUI.setupWithNavController(binding.toolbarHome, findNavController())
        binding.toolbarHome.navigationIcon = null
        binding.toolbarHome.title = null
        binding.toolbarHome.inflateMenu(R.menu.main_menu)


    }

    private fun setUiListener() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
        binding.toolbarHome.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logout -> {
                    FirebaseAuth.getInstance().signOut()
                    LoginManager.getInstance().logOut()
                    Navigation.findNavController(binding.root).navigateUp()
                    return@setOnMenuItemClickListener false
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
    }

        private fun getCharacters() {
            binding.progressBar.visibility = View.VISIBLE
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.characterCurrentData?.collectLatest { resource ->
                        binding.progressBar.visibility = View.GONE
                        pagingAdapter.submitData(viewLifecycleOwner.lifecycle, resource)
                    }
                }
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }

    }