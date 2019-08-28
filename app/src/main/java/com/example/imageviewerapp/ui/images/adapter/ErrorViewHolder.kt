package com.example.imageviewerapp.ui.images.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.example.imageviewerapp.R
import kotlinx.android.synthetic.main.item_paging_error.view.error
import kotlinx.android.synthetic.main.item_paging_error.view.retry

class ErrorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context).inflate(R.layout.item_paging_error, parent, false)
    )

    private val errorTextView = view.error
    private val retryButton = view.retry

    fun onBind(errorText: String, onRetry: () -> Unit) {
        errorTextView.text = errorText
        retryButton.setOnClickListener { onRetry() }
    }

    fun onBind(@StringRes errorTextRes: Int, onRetry: () -> Unit) {
        errorTextView.setText(errorTextRes)
        retryButton.setOnClickListener { onRetry() }
    }
}