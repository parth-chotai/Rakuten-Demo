package com.rakuten.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.rakuten.demo.data.model.Photo
import com.rakuten.demo.data.model.RecentPhotos
import com.rakuten.demo.databinding.ActivityDetailBinding
import com.rakuten.demo.extensions.getFormattedText
import com.rakuten.demo.extensions.getFormattedUrl
import com.rakuten.demo.extensions.parcelable

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photo = intent.parcelable<Photo>(RecentPhotos.KEYS.PHOTO)

        photo?.let {
            binding.ivDetailPhoto.load(this.getFormattedUrl(it))
            binding.tvId.text = it.id?.getFormattedText(getString(R.string.id_format))
            binding.tvOwner.text = it.owner?.getFormattedText(getString(R.string.owner_format))
            binding.tvSecret.text = it.secret?.getFormattedText(getString(R.string.secret_format))
            binding.tvServer.text = it.server?.getFormattedText(getString(R.string.server_format))
            binding.tvFarm.text = it.farm?.getFormattedText(getString(R.string.farm_format))
            binding.tvTitle.text = it.title?.getFormattedText(getString(R.string.title_format))
            binding.tvIsPublic.text =
                it.isPublic?.getFormattedText(getString(R.string.is_public_format))
            binding.tvIsFriend.text =
                it.isFriend?.getFormattedText(getString(R.string.is_friend_format))
            binding.tvIsFamily.text =
                it.isFamily?.getFormattedText(getString(R.string.is_family_format))
        }
    }
}