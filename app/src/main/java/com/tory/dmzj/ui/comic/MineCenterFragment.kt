package com.tory.dmzj.ui.comic

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.tory.dmzj.R
import com.tory.dmzj.bean.UserModel
import com.tory.dmzj.event.LoginSuccessEvent
import com.tory.dmzj.helper.SpHelper
import com.tory.dmzj.ui.LoginActivity
import com.tory.dmzj.ui.MineSubscribeActivity
import com.tory.dmzj.ui.base.BaseFragment
import com.tory.dmzj.ui.base.BaseViewPagerFragment
import com.tory.dmzj.utils.L
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Created by tory on 2018/1/14.
 * @date 2018/1/16
 */
class MineCenterFragment : BaseViewPagerFragment() {

    @BindView(R.id.photo)
    lateinit var mPhoto : ImageView
    @BindView(R.id.nickname)
    lateinit var mNickname : TextView

    var mUser : UserModel.User ? = null

    override fun bindLayout(): Int = R.layout.fragment_mine_center

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
    }

    override fun buildTabItems(tabItems: MutableList<TabItem>) {
        addTabItem("漫画", CartoonCenterFragment::class.java)
    }

    fun fetchData() {
        L.d("fetchData")
        val user =  SpHelper.getUser()
        L.d("fetchData $user")
        if (user != null){
            displayUser(user)
        }
    }

    private fun displayUser(user: UserModel.User) {
        mUser = user
        mNickname.text = user.nickname
        Glide.with(this).load(user.photo).into(mPhoto)
    }

    @OnClick(R.id.mine_container)
    fun minContainerClick(){
        if(mUser == null){
            turnToLoginActivity()
        }
    }

    fun turnToLoginActivity(){
        val intent = Intent(activity, LoginActivity::class.java)
        activity!!.startActivity(intent)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginSuccess(evnet : LoginSuccessEvent){
        fetchData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        L.d( "MineCenterFragment", "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    class CartoonCenterFragment : BaseFragment(){
        override fun bindLayout(): Int  = R.layout.fragment_cartoon_center

        @OnClick(R.id.mine_cartoon_subscribe)
        fun turnToSubscribe() {
                val intent = Intent(activity, MineSubscribeActivity::class.java)
                activity!!.startActivity(intent)
        }

        companion object {
            val  TAG = "CartoonCenterFragment"
        }
    }

    companion object {
        val TAG = "MineCenterFragment"
        val FRAGMENT_TAG = MineCenterFragment::class.java.simpleName!!
    }
}