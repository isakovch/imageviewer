package com.example.imageviewerapp.ui.images.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imageviewerapp.R

class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context).inflate(R.layout.item_paging_progress, parent, false)
    )
}