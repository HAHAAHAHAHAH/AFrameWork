package com.wltt.aframework.tools

import android.content.Context

/**
 * SharedPreferences工具类
 *
 * 存取的时候可以指定表名，也可以不指定，不指定使用defaultName
 *
 */
object SharedPreferencesTool {
    private const val defaultName = "shared_preferences"

    fun saveData(context: Context, name: String?, key: String, content: Any) {

        val sharedPreferences = if (name.isNullOrEmpty()) {
            context.getSharedPreferences(defaultName, 0)
        } else {
            context.getSharedPreferences(name, 0)
        }

        val editor = sharedPreferences.edit()

        when (content) {
            is String -> {
                editor.putString(key, content)
            }
            is Int -> {
                editor.putInt(key, content)
            }
            is Long -> {
                editor.putLong(key, content)
            }
            is Float -> {
                editor.putFloat(key, content)
            }
            is Boolean -> {
                editor.putBoolean(key, content)
            }
        }
        editor.commit()
    }

    fun getString(context: Context, name: String?, key: String, defValue: String): String {
        val sharedPreferences = if (name.isNullOrEmpty()) {
            context.getSharedPreferences(defaultName, 0)
        } else {
            context.getSharedPreferences(name, 0)
        }
        return sharedPreferences.getString(key, defValue)!!
    }

    fun getInt(context: Context, name: String?, key: String, defValue: Int): Int {
        val sharedPreferences = if (name.isNullOrEmpty()) {
            context.getSharedPreferences(defaultName, 0)
        } else {
            context.getSharedPreferences(name, 0)
        }
        return sharedPreferences.getInt(key, defValue)
    }

    fun getLong(context: Context, name: String?, key: String, defValue: Long): Long {
        val sharedPreferences = if (name.isNullOrEmpty()) {
            context.getSharedPreferences(defaultName, 0)
        } else {
            context.getSharedPreferences(name, 0)
        }
        return sharedPreferences.getLong(key, defValue)
    }

    fun getFloat(context: Context, name: String?, key: String, defValue: Float): Float {
        val sharedPreferences = if (name.isNullOrEmpty()) {
            context.getSharedPreferences(defaultName, 0)
        } else {
            context.getSharedPreferences(name, 0)
        }
        return sharedPreferences.getFloat(key, defValue)
    }

    fun getBoolean(context: Context, name: String?, key: String, defValue: Boolean): Boolean {
        val sharedPreferences = if (name.isNullOrEmpty()) {
            context.getSharedPreferences(defaultName, 0)
        } else {
            context.getSharedPreferences(name, 0)
        }
        return sharedPreferences.getBoolean(key, defValue)
    }
}