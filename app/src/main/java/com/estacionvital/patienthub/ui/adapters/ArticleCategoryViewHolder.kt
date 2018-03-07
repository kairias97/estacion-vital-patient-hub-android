package com.estacionvital.patienthub.ui.adapters

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.ArticleCategory

/**
 * Created by kevin on 5/3/2018.
 */
class ArticleCategoryViewHolder: RecyclerView.ViewHolder {

    private val mCategoryNameTextView: TextView
    private val mCategoryDescriptionTextView: TextView
    private val mCategoryContainerConstraintLayout: ConstraintLayout

    constructor(itemView: View) : super(itemView) {
        mCategoryNameTextView = itemView.findViewById(R.id.text_category_name)
        mCategoryContainerConstraintLayout = itemView.findViewById(R.id.constraint_layout_article_category_container)
        mCategoryDescriptionTextView = itemView.findViewById(R.id.text_category_description)
    }

    fun bindData(articleCategory: ArticleCategory, listener: ArticleCategoryAdapter.OnCategorySelectedListener) {
        mCategoryNameTextView.text = articleCategory.title
        mCategoryDescriptionTextView.text = articleCategory.description
        mCategoryContainerConstraintLayout.setOnClickListener {
            listener.onCategoryItemClicked(articleCategory)
        }

    }
}