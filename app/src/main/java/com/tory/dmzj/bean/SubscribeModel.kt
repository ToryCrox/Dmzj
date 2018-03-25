package com.tory.dmzj.bean

import com.google.gson.annotations.SerializedName

data class SubscribeModel(

	@field:SerializedName("sub_img")
	val subImg: String? = null,

	@field:SerializedName("sub_first_letter")
	val subFirstLetter: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("sub_uptime")
	val subUptime: Int? = 0,

	@field:SerializedName("sub_readed")
	val subReaded: Int = 0,

	@field:SerializedName("id")
	val id: Int? = 0,

	@field:SerializedName("sub_update")
	val subUpdate: String? = null,

	@field:SerializedName("status")
	val status: String? = null

)