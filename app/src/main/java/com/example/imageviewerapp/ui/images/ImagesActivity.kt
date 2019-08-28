package com.example.imageviewerapp.ui.images

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imageviewerapp.R
import com.example.imageviewerapp.ui.images.adapter.ImagesAdapter
import com.example.imageviewerapp.ui.images.paging.PagingState
import com.example.imageviewerapp.utils.observeNonNull
import kotlinx.android.synthetic.main.activity_main.imagesRecyclerView
import kotlinx.android.synthetic.main.activity_main.refreshLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImagesActivity : AppCompatActivity() {

    private val viewModel: ImagesViewModel by viewModel()

    private lateinit var adapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        adapter = ImagesAdapter(
            onClickListener = { },
            retryListener = { viewModel.reload() }
        )

        imagesRecyclerView.layoutManager = GridLayoutManager(this@ImagesActivity, 2)
        imagesRecyclerView.adapter = adapter

        viewModel.imagesLiveData.observeNonNull(this) { list ->
            adapter.submitList(list)
        }

        viewModel.stateLiveData.observeNonNull(this) {
            renderState(it)
        }

        refreshLayout.setOnRefreshListener { viewModel.reload() }
    }

    private fun renderState(state: PagingState) {
        refreshLayout.isRefreshing = state is PagingState.InitialLoading
        adapter.setPagingState(state)
    }
}