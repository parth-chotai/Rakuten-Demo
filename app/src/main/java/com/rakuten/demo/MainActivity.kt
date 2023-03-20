package com.rakuten.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.rakuten.demo.data.adapter.ImageAdapter
import com.rakuten.demo.data.model.RecentPhotos
import com.rakuten.demo.databinding.ActivityMainBinding
import com.rakuten.demo.util.Constants
import com.rakuten.demo.util.NetworkResult
import com.rakuten.demo.viewmodel.RecentPhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: RecentPhotosViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listView = binding.lvImages

        val detailActivityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val title = data?.getStringExtra(Constants.TITLE)
                binding.tvBottom.text = title
            }
        }

        viewModel.photosMetadata.observe(this) { networkResult ->
            when (networkResult) {
                is NetworkResult.Error -> {
                    handleVisibility(pbVisible = false, lvVisible = false, binding = binding)
                    Toast.makeText(this, networkResult.message, Toast.LENGTH_SHORT).show()
                }
                NetworkResult.Loading -> {
                    handleVisibility(pbVisible = true, lvVisible = false, binding = binding)
                }
                is NetworkResult.Success -> {
                    handleVisibility(pbVisible = false, lvVisible = true, binding = binding)
                    val adapter =
                        networkResult.data.recentPhotos?.let { ImageAdapter(this, it.photos) }
                    listView.adapter = adapter
                    listView.setOnItemClickListener { _, _, position, _ ->
                        val selectedPhoto = adapter?.getItem(position)
                        val intent = Intent(this, DetailActivity::class.java)
                        intent.putExtra(RecentPhotos.KEYS.PHOTO, selectedPhoto)
                        detailActivityResult.launch(intent)
                    }
                }
            }
        }
    }

    private fun handleVisibility(
        pbVisible: Boolean = true,
        lvVisible: Boolean = true,
        binding: ActivityMainBinding,
    ) {
        binding.pbLoading.isVisible = pbVisible
        binding.lvImages.isVisible = lvVisible
    }
}