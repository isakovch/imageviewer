package com.example.imageviewerapp.ui.images.adapter

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.imageviewerapp.R
import com.example.imageviewerapp.data.model.Image
import com.example.imageviewerapp.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_image.view.imageView
import kotlinx.android.synthetic.main.item_image.view.nameTextView

class ImagesViewHolder(
    private val parent: ViewGroup,
    onClickListener: (Image) -> Unit
) : BaseViewHolder<Image>(parent, R.layout.item_image, onClickListener) {

    private val imageView = itemView.imageView
    private val nameTextView = itemView.nameTextView

    override fun onBind(item: Image) {
        super.onBind(item)
        Glide
            .with(parent)
            .load(item.imageUrl)
            .into(imageView)

        nameTextView.text = item.name
    }

    override fun onViewRecycled() {
        super.onViewRecycled()
        Glide.with(imageView.context.applicationContext).clear(imageView)
    }
}