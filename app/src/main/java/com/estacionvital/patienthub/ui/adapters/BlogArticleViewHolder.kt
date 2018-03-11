package com.estacionvital.patienthub.ui.adapters

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.BlogArticle
import com.estacionvital.patienthub.util.DateUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_blog_article.view.*

/**
 * Created by kevin on 8/3/2018.
 */
class BlogArticleViewHolder: RecyclerView.ViewHolder {
    private val mTitleTextView: TextView
    private val mCreatedByTextView: TextView
    private val mCreatedDateTextView: TextView
    private val mThumbnailImageView: ImageView
    private val mArticleContainerConstraintLayout: ConstraintLayout

    constructor(itemView: View): super(itemView){
        mTitleTextView =  itemView.findViewById(R.id.text_article_title)
        mCreatedByTextView = itemView.findViewById(R.id.text_article_created_by)
        mCreatedDateTextView = itemView.findViewById(R.id.text_article_created_date)
        mThumbnailImageView = itemView.findViewById(R.id.image_thumbnail)
        mArticleContainerConstraintLayout = itemView.findViewById(R.id.constraint_layout_article_container)
    }
    fun bindData(article: BlogArticle, listener: BlogArticleAdapter.OnArticleInteractionListener) {
        mTitleTextView.text = article.title
        mCreatedByTextView.text = article.author.name
        mCreatedDateTextView.text = DateUtil.parseDateStringToFormat(article.date,
                "yyyy-MM-dd HH:mm:ss",
                "dd/MM/yyyy")

        Picasso.get()
                .load(article.thumbnail)
                .placeholder(R.drawable.img_logo)
                .error(R.drawable.img_logo)
                .into(mThumbnailImageView)
        mArticleContainerConstraintLayout.setOnClickListener {
            listener.onArticleItemClicked(article)
        }
    }

}