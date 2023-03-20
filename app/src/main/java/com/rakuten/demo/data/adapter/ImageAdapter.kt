package com.rakuten.demo.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.rakuten.demo.R
import com.rakuten.demo.data.model.Photo
import com.rakuten.demo.extensions.getFormattedUrl

class ImageAdapter(context: Context, private val photos: List<Photo>) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_image, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val photo = photos[position]

        holder.tvTitle.text = photo.title
        holder.ivPhoto.load(parent?.context?.getFormattedUrl(photo))

        return view
    }

    override fun getItem(position: Int): Photo {
        return photos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return photos.size
    }

    private class ViewHolder(view: View) {
        val ivPhoto: ImageView = view.findViewById(R.id.ivPhoto)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
    }
}
