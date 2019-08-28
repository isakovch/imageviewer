package com.example.imageviewerapp.ui.images.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imageviewerapp.R
import com.example.imageviewerapp.data.model.Image
import com.example.imageviewerapp.ui.images.paging.PagingState

class ImagesAdapter(
    private val onClickListener: (Image) -> Unit,
    private val retryListener: () -> Unit
) : PagedListAdapter<Image, RecyclerView.ViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(item1: Image, item2: Image): Boolean = item1.id == item2.id
            override fun areContentsTheSame(item1: Image, item2: Image): Boolean = item1 == item2
        }

        private val noAdditionalItemRequiredState = listOf(PagingState.Idle, PagingState.InitialLoading)
    }

    private var pagingState: PagingState = PagingState.Idle

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_image -> ImagesViewHolder(parent, onClickListener)
            R.layout.item_paging_progress -> ImagesViewHolder(parent, onClickListener)
            else -> ErrorViewHolder(parent)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImagesViewHolder -> getItem(position)?.let { holder.onBind(it) }
            is ErrorViewHolder -> {
                val state = pagingState
                if (state is PagingState.Error) {
                    holder.onBind(state.throwable.localizedMessage, retryListener)
                } else {
                    holder.onBind(R.string.error_occurred, retryListener)
                }
            }
            is ProgressViewHolder -> {
                // Do nothing since progress has no data to bind.
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when {
        !stateRequiresExtraItem(pagingState) || position < itemCount - 1 -> R.layout.item_image
        pagingState is PagingState.Loading -> R.layout.item_paging_progress
        else -> R.layout.item_paging_error
    }

    override fun getItemCount(): Int =
        super.getItemCount() + if (stateRequiresExtraItem(pagingState)) 1 else 0

    override fun getItem(position: Int): Image? =
        if (position < super.getItemCount()) super.getItem(position) else null

    fun setPagingState(newState: PagingState) {
        if (pagingState == newState) return

        val shouldHasExtraItem = stateRequiresExtraItem(newState)
        val hasExtraItem = stateRequiresExtraItem(pagingState)

        pagingState = newState

        // since item count is a function - cache its value.
        val count = itemCount

        when {
            hasExtraItem && shouldHasExtraItem -> notifyItemChanged(count)
            hasExtraItem && !shouldHasExtraItem -> notifyItemRemoved(count)
            !hasExtraItem && shouldHasExtraItem -> notifyItemInserted(count)
        }
    }

    private fun stateRequiresExtraItem(state: PagingState) = state !in noAdditionalItemRequiredState
}