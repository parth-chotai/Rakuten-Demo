package com.rakuten.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
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
    private lateinit var listView: ListView
    private lateinit var headerView: LinearLayout
    private var headerViewHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listView = binding.lvImages
        headerView = binding.llHeader

        setQuickReturnPattern()

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

    private fun setQuickReturnPattern() {
        // Add an empty header view to the top of the list and sets its height to match that of the header view.
        // This ensures that the first item in the list does not get hidden behind the header view
        // when it is scrolled up to position 0.
        val emptyView = View(this)
        val params =
            AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, headerView.height)
        emptyView.layoutParams = params
        listView.addHeaderView(emptyView)

        // set up the scroll listener for the ListView
        var lastFirstVisibleItem = 0
        var headerViewTranslationY = 0f
        listView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                // do nothing
            }

            override fun onScroll(
                view: AbsListView,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int,
            ) {
                if (headerViewHeight == 0) {
                    headerViewHeight = headerView.height
                }
                if (firstVisibleItem == 0) {
                    headerViewTranslationY = 0f
                    headerView.animate().translationY(headerViewTranslationY)
                    listView.animate().translationY(headerViewHeight.toFloat())
                } else {
                    if (firstVisibleItem > lastFirstVisibleItem) {
                        // scroll down
                        if (headerViewTranslationY != -headerViewHeight.toFloat()) {
                            headerViewTranslationY = -headerViewHeight.toFloat()
                            headerView.animate().translationY(headerViewTranslationY)
                            listView.animate().translationY(0f)
                        }
                    } else if (firstVisibleItem < lastFirstVisibleItem) {
                        // scroll up
                        if (headerViewTranslationY != 0f) {
                            headerViewTranslationY = 0f
                            headerView.animate().translationY(headerViewTranslationY)
                            listView.animate().translationY(headerViewHeight.toFloat())
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem
            }
        })

        // set the initial marginTop of the ListView
        listView.post {
            val params = listView.layoutParams as RelativeLayout.LayoutParams
            val margin = headerViewHeight
            params.setMargins(0, -margin, 0, 0)
            listView.layoutParams = params
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