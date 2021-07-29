package android.example.com.challengemarvel.ui.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.challengemarvel.R
import android.example.com.challengemarvel.core.Resource
import android.example.com.challengemarvel.databinding.FragmentEventsBinding
import android.example.com.challengemarvel.presentation.MainViewModel
import android.example.com.challengemarvel.utils.hide
import android.example.com.challengemarvel.utils.show
import android.widget.Toast
import androidx.navigation.Navigation
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class EventsFragment : Fragment() {

    //ViewBinding
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel by viewModel<MainViewModel>()

    // Recycler View Adapter
    private val myAdapter = EventListAdapter(mutableListOf())




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewEvents.adapter = myAdapter
        setUi()
        setListeners()
        getEvents()
    }

    private fun setUi() {
        binding.toolbarEvents.inflateMenu(R.menu.main_menu)
    }

    private fun setListeners() {
        binding.toolbarEvents.setOnMenuItemClickListener {
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

    private fun getEvents() {
        viewModel.getEvents().observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> binding.progressBar.show()
                is Resource.Success -> {
                    binding.progressBar.hide()
                    myAdapter.refreshList(resource.data)
                }
                is Resource.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(
                        requireContext(),
                        resource.exception.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}