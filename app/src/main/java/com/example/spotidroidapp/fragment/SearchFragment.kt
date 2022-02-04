package com.example.spotidroidapp.fragment

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.spotidroidapp.MainActivity
import com.example.spotidroidapp.R
import com.example.spotidroidapp.databinding.FragmentSearchBinding
import com.example.spotidroidapp.model.Song
import com.example.spotidroidapp.model.SongRepository
import com.example.spotidroidapp.ui.adapters.SongAdapter
import com.example.spotidroidapp.ui.listener.OnSongClickListener
import com.example.spotidroidapp.ui.viewmodel.GenreSongViewModel
import com.example.spotidroidapp.ui.viewmodel.GenreSongViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search), SearchView.OnQueryTextListener,
    OnSongClickListener {

    private var token = ""
    private val sAdapter = SongAdapter(this)
    private lateinit var viewModel: GenreSongViewModel
    private val binding: FragmentSearchBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mt = requireActivity().findViewById<MaterialToolbar>(R.id.my_toolbar)
        mt.inflateMenu(R.menu.toolbar_items)
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val search = mt.menu.findItem(R.id.search_toolbar)
        (search.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(this@SearchFragment)
        }

        token = activity?.getSharedPreferences("SPOTIFY", 0)?.getString("token", "")!!
        viewModel = GenreSongViewModelFactory(SongRepository(token)).create(
            GenreSongViewModel::class.java)

        viewModel.searchedSongs.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                sAdapter.submitList(it)
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater)  {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.toolbar_items, menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        hideKeyboard()
        return true
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        newText?.let { viewModel.searchSongs(it) }
        return true
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

    override fun onDestroyView() {
        super.onDestroyView()
        val mt = requireActivity().findViewById<MaterialToolbar>(R.id.my_toolbar)
        mt.menu.clear()
    }


}