package android.example.com.challengemarvel.ui

import android.example.com.challengemarvel.R
import android.example.com.challengemarvel.core.Resource
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.challengemarvel.databinding.FragmentDetailBinding
import android.example.com.challengemarvel.presentation.MainViewModel
import android.example.com.challengemarvel.utils.getProgressDrawable
import android.example.com.challengemarvel.utils.loadImage
import android.example.com.challengemarvel.utils.toHTTPS
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : Fragment() {

    //ViewBinding
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel by viewModel<MainViewModel>()

    //RecyclerView Adapter
    private val myAdapter = ComicDetailAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupWithNavController(binding.toolbarDetail, findNavController())
        setUi()
        getInfo()
    }

    private fun setUi() {
        binding.toolbarDetail.title = null
        binding.recyclerViewCharacterComics.adapter = myAdapter
        val itemDecoration = DividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL
        )
        itemDecoration.setDrawable(ColorDrawable(requireContext().resources.getColor(R.color.black)))
        binding.recyclerViewCharacterComics.addItemDecoration(object: RecyclerView.ItemDecoration(){
        })
        binding.recyclerViewCharacterComics.addItemDecoration(itemDecoration)
    }

    private fun getInfo() {
        arguments?.let {
            val characterId = requireArguments().getInt("characterId")
            getCharacterInfo(characterId)

        }
    }

    private fun getCharacterInfo(id: Int) {
        viewModel.getCharacterById(id).observe(viewLifecycleOwner, { resource ->
            when(resource) {
                is Resource.Success -> {
                    val imageUrl =
                        "${resource.data[0].thumbnail?.path?.toHTTPS()}/standard_xlarge.${resource.data[0].thumbnail?.extension}"
                    Log.e("CharacterName", "${resource.data[0].name}")
                    binding.characterImage.loadImage(imageUrl, getProgressDrawable(requireContext()))
                    binding.tvToolbarTitle.text = resource.data[0].name
                    binding.characterDescription.text = resource.data[0].description
                    myAdapter.refreshList(resource.data[0].comics.items)

                }
                is Resource.Loading -> {}
                is Resource.Failure -> {}
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}