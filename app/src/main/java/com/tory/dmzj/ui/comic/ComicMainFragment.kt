package com.tory.dmzj.ui.comic


import com.tory.dmzj.ui.base.BaseViewPagerFragment

class ComicMainFragment : BaseViewPagerFragment() {

    override fun buildTabItems(tabItems: MutableList<TabItem>) {
        addTabItem("更新", ComicUpdateFragment::class.java)
        addTabItem("推荐", ComicRecommendFragment::class.java)
        addTabItem("阅读", ComicReaderFragment::class.java)
        addTabItem("分类", CartoonCategoryFragment::class.java)
    }


    companion object {
        val FRAGMENT_TAG = ComicMainFragment::class.java.simpleName!!

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PicaMainFragment.
         */
        fun newInstance(): ComicMainFragment {
            return ComicMainFragment()
        }
    }
}// Required empty public constructor
