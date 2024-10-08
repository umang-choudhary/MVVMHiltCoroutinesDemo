package com.app.koltinpoc.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.koltinpoc.R
import com.app.koltinpoc.databinding.FragmentOfflineBinding
import com.app.koltinpoc.utils.DataHandler
import com.app.koltinpoc.utils.logData
import com.app.koltinpoc.view.adapter.NewsAdapter
import com.app.koltinpoc.viewModel.OfflineViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class OffLineFragment : Fragment(R.layout.fragment_offline) {

    lateinit var binding: FragmentOfflineBinding
    val viewModel: OfflineViewModel by viewModels()

    @Inject
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOfflineBinding.bind(view)
        init()

        viewModel.article.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    logData("onViewCreated: SUCCESS  ${dataHandler.data?.get(0)?.title} ")
                    newsAdapter.differ.submitList(dataHandler.data)
                }

                is DataHandler.ERROR -> {
                    logData("onViewCreated: ERROR ${dataHandler.message}")
                }

                is DataHandler.LOADING -> {
                    logData("onViewCreated: LOADING")
                }
            }
        }
    }

    private fun init() {
        binding.recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

}