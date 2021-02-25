package com.garmin.garminkaptain.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.garmin.garminkaptain.R
import com.garmin.garminkaptain.data.Review
import com.garmin.garminkaptain.data.reviews

class PoiReviewFragment : Fragment(R.layout.fragment_poi_review) {

    inner class PoiReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ratingView = itemView.findViewById<RatingBar>(R.id.poi_review_rating_bar)
        private val titleView = itemView.findViewById<TextView>(R.id.poi_review_title)
        private val textView = itemView.findViewById<TextView>(R.id.poi_review_text)

        fun bind(review: Review) {
            ratingView.rating = review.rating.toFloat()
            titleView.text = review.title
            textView.text = review.text
        }
    }

    inner class PoiReviewsAdapter : RecyclerView.Adapter<PoiReviewViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoiReviewViewHolder {
            return PoiReviewViewHolder(
                layoutInflater.inflate(
                    R.layout.poi_review_item,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: PoiReviewViewHolder, position: Int) {
            reviewList?.getOrNull(position)?.let { holder.bind(it) }
        }

        override fun getItemCount(): Int = reviewList?.size ?: 0
    }

    private val args by navArgs<PoiDetailsFragmentArgs>()

    private var reviewList: List<Review>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reviews[args.poiId]?.let { reviewList = it }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<RecyclerView>(R.id.poi_review_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = PoiReviewsAdapter()
        }
    }

}