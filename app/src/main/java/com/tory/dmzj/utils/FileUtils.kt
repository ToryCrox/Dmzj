package com.tory.dmzj.utils

import android.content.Context
import android.os.Environment
import android.text.format.Formatter

import java.io.File
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections

/**
 * @Author: tory
 * Create: 2017/9/2
 */
object FileUtils {
    val SIZE_KB = 1024
    val SIZE_MB = SIZE_KB * 1024
    val SIZE_GB = SIZE_MB * 1024

    val IMAGE_EXTENSION_NAME: Array<String>

    val isExternalStorageAvailable: Boolean
        get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    init {
        IMAGE_EXTENSION_NAME = arrayOf("jpg", "png", "jpeg", "bmp")
        Arrays.sort(IMAGE_EXTENSION_NAME)
    }

    /**
     * 得到手机的缓存目录
     *
     * @param context
     * @return
     */
    fun getCacheDir(context: Context): File {
        if (isExternalStorageAvailable) {
            val cacheDir = context.externalCacheDir
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                return cacheDir
            }
        }

        return context.cacheDir
    }

    /**
     * 得到缓存文件夹
     */
    fun getDiskCacheDir(context: Context, uniqueName: String): File {
        //判断储存卡是否可以用
        val cacheDir = File(getCacheDir(context), uniqueName)
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        return cacheDir
    }

    fun getExternalDir(relativePath: String): File? {
        val path = getExternalPath(relativePath)
        if (path != null) {
            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return dir
        }
        return null
    }

    /**
     * 计算文件夹大小
     * @param dest
     * @return
     */
    fun getFileSize(dest: File?): Long {
        if (dest == null || !dest.exists()) return 0
        if (dest.isFile) {
            return dest.length()
        }
        var len: Long = 0
        for (file in dest.listFiles()) {
            len += getFileSize(file)
        }
        return len
    }

    /**
     * 使用系统的格式化方法
     * @param context
     * @param file
     * @return
     */
    fun formatFileSize(context: Context, file: File): String {
        return Formatter.formatFileSize(context, getFileSize(file))
    }

    /**
     * 获取模式化好的文件或文件夹大小
     * @param dest
     * @return
     */
    fun formatFileSize(dest: File): String {
        val size = getFileSize(dest)
        return formatFileSize(size)
    }

    /**
     * 格式化文件大小字符串,保留1位小数
     * @param size
     * @return
     */
    fun formatFileSize(size: Long): String {
        return if (size < SIZE_KB) {
            size.toString() + "B"
        } else if (size < SIZE_MB) {
            String.format("%.1fK", size / SIZE_KB.toFloat())
        } else if (size < SIZE_GB) {
            String.format("%.1fM", size / SIZE_MB.toFloat())
        } else {
            String.format("%.1fG", size / SIZE_GB.toFloat())
        }
    }

    /**
     * 获取相对外部存储的绝对路径
     * @param relativePath 相对外部存储的路径，可以以/开头，也可以不以/天头
     * @return
     */
    fun getExternalPath(relativePath: String): String? {
        return if (isExternalStorageAvailable) {
            Environment.getExternalStorageDirectory().absoluteFile.toString() + getAndroidPath(relativePath)
        } else null
    }

    /**
     * @param path
     * @return
     */
    fun getAndroidPath(path: String): String {
        var path = path
        path = path.replace("/".toRegex(), File.separator)
        return if (path.startsWith(File.separator)) path else File.separator + path
    }

    /**
     * Java文件操作 获取文件扩展名
     * @param filename
     * @return
     */
    fun getExtensionName(filename: String?): String {
        if (filename != null && filename.length > 0) {
            val dot = filename.lastIndexOf('.')
            if (dot > -1 && dot < filename.length - 1) {
                return filename.substring(dot + 1)
            }
        }
        return ""
    }

    fun isSimpleImage(file: String): Boolean {
        return Arrays.binarySearch(IMAGE_EXTENSION_NAME, getExtensionName(file).toLowerCase()) >= 0
    }
}
