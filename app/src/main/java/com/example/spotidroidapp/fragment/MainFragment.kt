package com.example.spotidroidapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.spotidroidapp.databinding.FragmentMainBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.spotidroidapp.R
import com.example.spotidroidapp.model.Genre
import com.example.spotidroidapp.model.SongRepository
import com.example.spotidroidapp.ui.adapter.GAdapter
import com.example.spotidroidapp.ui.listener.OnGenreClickListener
import com.example.spotidroidapp.ui.viewmodel.GenreViewModel
import com.example.spotidroidapp.ui.viewmodel.GenreViewModelFactory
import kotlinx.coroutines.launch


class MainFragment : Fragment(R.layout.fragment_main), OnGenreClickListener {
    private val gAdapter = GAdapter(this)
    private val binding: FragmentMainBinding by viewBinding()
    private var token = ""

    private lateinit var viewModel: GenreViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        token = activity?.getSharedPreferences("SPOTIFY", 0)?.getString("token", "")!!
        viewModel = GenreViewModelFactory(SongRepository(token)).create(GenreViewModel::class.java)

        viewModel.genres.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                val result = it
                result.forEach { Log.d("(((((((((9", it.name) }
                gAdapter.submitList(result)
            }
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = gAdapter
        }

    }


    override fun onClick(g: Genre) {
        val action =
            MainFragmentDirections.actionMainFragmentToSongListFragment(g)
        view?.findNavController()?.navigate(action)
    }

}