package com.tory.dmzj.bean

class UserModel : BaseModel() {
    var result: String? = null
    var msg: String? = null
    var data: User? = null


    companion object {
        val STATUS_OFFLINE = 0
        val STATUS_ONLINE = 1
        val USER_STATUS = "user_status"
    }

    data class User(var dmzj_token: String?, var nickname: String?, var photo: String?, var status: Int, var uid: String?)

    override fun toString(): String {
        return "UserModel(result=$result, msg=$msg, data=$data)"
    }
}