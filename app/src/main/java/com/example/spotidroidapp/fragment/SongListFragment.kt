package com.example.spotidroidapp.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.spotidroidapp.MainActivity
import com.example.spotidroidapp.R
import com.example.spotidroidapp.databinding.FragmentSonglistBinding
import com.example.spotidroidapp.model.Genre
import com.example.spotidroidapp.model.Song
import com.example.spotidroidapp.model.SongRepository
import com.example.spotidroidapp.ui.adapters.SongAdapter
import com.example.spotidroidapp.ui.listener.OnSongClickListener
import com.example.spotidroidapp.ui.viewmodel.GenreSongViewModel
import com.example.spotidroidapp.ui.viewmodel.GenreSongViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class SongListFragment : Fragment(R.layout.fragment_songlist), OnSongClickListener {
    private val sAdapter = SongAdapter(this)
    private val binding: FragmentSonglistBinding by viewBinding()
    private var token = ""
    private val args : SongListFragmentArgs by navArgs()
    private lateinit var genre: Genre

    private lateinit var viewModel: GenreSongViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genre = args.genre
        activity?.findViewById<MaterialToolbar>(R.id.my_toolbar)?.title = genre.name
        token = activity?.getSharedPreferences("SPOTIFY", 0)?.getString("token", "")!!
        viewModel = GenreSongViewModelFactory(SongRepository(token), genre).create(GenreSongViewModel::class.java)

        viewModel.songs.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                val songs = it
                sAdapter.submitList(songs)
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sAdapter
        }

    }

    override fun onClick(v: View, s: Song) {
        (activity as MainActivity?)!!.playSong(s.uri)
    }

    override fun onClick(s: Song) {
        if(s.liked) {
            viewModel.dislikeSong(s)
        }
        else {
            viewModel.likeSong(s)
        }
    }
}