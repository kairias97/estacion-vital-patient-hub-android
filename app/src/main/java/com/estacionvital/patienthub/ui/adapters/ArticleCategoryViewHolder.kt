package com.estacionvital.patienthub.ui.adapters

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.ArticleCategory
import com.squareup.picasso.Picasso


/**
 * Created by kevin on 5/3/2018.
 */
class ArticleCategoryViewHolder: RecyclerView.ViewHolder {

    private val mCategoryNameTextView: TextView
    private val mCategoryDescriptionTextView: TextView
    private val mThumbnailImageView: ImageView
    private val mCategoryContainerConstraintLayout: ConstraintLayout
    private val mContainerCardView: CardView

    constructor(itemView: View) : super(itemView) {
        mCategoryNameTextView = itemView.findViewById(R.id.text_category_name)
        mCategoryContainerConstraintLayout = itemView.findViewById(R.id.constraint_layout_article_category_container)
        mCategoryDescriptionTextView = itemView.findViewById(R.id.text_category_description)
        mThumbnailImageView = itemView.findViewById(R.id.image_thumbnail)
        mContainerCardView = itemView.findViewById(R.id.card_container)

    }

    fun bindData(articleCategory: ArticleCategory, listener: ArticleCategoryAdapter.OnCategorySelectedListener) {
        mCategoryNameTextView.text = articleCategory.title

        val description = articleCategory.description.split("\r\n/")
        mCategoryDescriptionTextView.text = description[0]
        if(description.size == 2){
            val path = description[1]
            Picasso.get()
                    .load(path)
                    .placeholder(R.drawable.img_logo)
                    .error(R.drawable.img_logo)
                    .into(mThumbnailImageView)
        }
        else{
            Picasso.get()
                    .load(R.drawable.img_logo)
                    .placeholder(R.drawable.img_logo)
                    .error(R.drawable.img_logo)
                    .into(mThumbnailImageView)
        }
        mCategoryContainerConstraintLayout.setOnClickListener {
            listener.onCategoryItemClicked(articleCategory)
        }
    }
}