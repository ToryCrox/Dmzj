package com.tory.dmzj.utils

import android.util.Log

import com.tory.dmzj.BuildConfig


/**
 * @Author: Tory
 * Create: 2016/9/11
 * Update: 2016/9/11
 */
object L {

    val TAG = "Dmzj"
    val FORCE_DEBUG = BuildConfig.DEBUG
    val DEBUG = FORCE_DEBUG || Log.isLoggable(TAG, Log.DEBUG)
    fun d(msg: String) {
        if (DEBUG) {
            Log.d(TAG, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (DEBUG) {
            Log.d(TAG, tag + "  " + msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (DEBUG) {
            Log.i(TAG, tag + "  " + msg)
        }
    }

    fun w(msg: String) {
        Log.w(TAG, msg)
    }

    fun w(tag: String, msg: String) {
        Log.w(TAG, tag + "  " + msg)
    }

    fun e(tag: String, msg: String) {
        Log.e(TAG, tag + "  " + msg)
    }

    fun e(msg: String, e: Throwable) {
        Log.e(TAG, "  " + msg, e)
    }

    fun e(tag: String, msg: String, e: Throwable) {
        Log.e(TAG, tag + "  " + msg, e)
    }
}
