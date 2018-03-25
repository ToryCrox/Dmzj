package com.tory.dmzj.ui.base

import android.os.Bundle

/**
 * from : http://www.jianshu.com/p/c5d29a0c3f4c
 */
abstract class BasePageFragment : BaseFragment() {

    protected var isRecreated: Boolean = false

    protected var isViewInitiated: Boolean = false
    protected var isVisibleToUser: Boolean = false
    protected var isDataInitiated: Boolean = false

    /**
     * @param savedInstanceState
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            isRecreated = savedInstanceState.getBoolean(KEY_ARG_RECREAT)
        }
        isViewInitiated = true
        prepareFetchData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        isRecreated = true
        outState!!.putBoolean(KEY_ARG_RECREAT, isRecreated)
        super.onSaveInstanceState(outState)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareFetchData()
    }

    abstract fun fetchData()

    /**
     *
     * @param forceUpdate
     * @return
     */
    @JvmOverloads
    fun prepareFetchData(forceUpdate: Boolean = false): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData()
            isDataInitiated = true
            return true
        }
        return false
    }

    companion object {
        val KEY_ARG_RECREAT = "key_arg_recreat"
    }
}