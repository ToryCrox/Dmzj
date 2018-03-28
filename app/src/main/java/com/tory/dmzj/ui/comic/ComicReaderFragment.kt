package com.tory.dmzj.ui.comic


import com.tory.dmzj.R
import com.tory.dmzj.ui.base.BasePageFragment


/**
 * @Author: tory
 * Create: 2017/9/5
 * Update: ${UPDATE}
 */
class ComicReaderFragment : BasePageFragment() {



    override fun bindLayout(): Int = R.layout.fragment_comic_reader

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun fetchData() {

    }

    companion object {

        val TAG = "ComicReaderFragment"

        fun newInstance(): ComicReaderFragment {
            return ComicReaderFragment()
        }
    }


}
