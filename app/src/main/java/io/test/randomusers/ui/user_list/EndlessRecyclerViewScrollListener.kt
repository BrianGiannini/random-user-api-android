package io.test.randomusers.ui.user_list

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerViewScrollListener(private val layoutManager: RecyclerView.LayoutManager) :
    RecyclerView.OnScrollListener() {

    private var visibleThreshold = 20
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        val lastVisibleItemPosition = getLastVisibleItem()
        val totalItemCount = layoutManager.itemCount

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage)
            loading = true
        }
    }

    private fun getLastVisibleItem(): Int {
        return when (layoutManager) {
            is LinearLayoutManager -> {
                layoutManager.findLastVisibleItemPosition()
            }
            else -> {
                0
            }
        }
    }

    abstract fun onLoadMore(page: Int)

}