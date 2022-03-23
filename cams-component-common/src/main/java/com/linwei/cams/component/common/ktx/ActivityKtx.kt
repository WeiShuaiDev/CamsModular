package com.linwei.cams.component.common.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity

/**
 * 启动`Activity`返回`Result`获取数据处理
 * @param callback [ActivityResultCallback]
 * @return [ActivityResultLauncher]
 */
fun AppCompatActivity.startActivityForResult(
    @NonNull callback: ActivityResultCallback<ActivityResult>
):ActivityResultLauncher<Intent> {
    val mActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                callback.onActivityResult(activityResult)
            }
        }
    return mActivityLauncher
}

/**
 * 启动 `Activity`设置`Result`数据
 * @param name [String]
 * @param value [String]
 */
fun AppCompatActivity.setResult(name:String,value:String){
    val intent=Intent().apply{
        putExtra(name,value)
    }
    setResult(Activity.RESULT_OK,intent)
}

/**
 * 启动 `Activity`设置`Result`数据
 * @param map [Map]
 */
fun AppCompatActivity.setResult(map:Map<String,Any?>){
    val intent=Intent().apply{
        putExtra(map)
    }
    setResult(Activity.RESULT_OK,intent)
}

/**
 * 启动 `Activity`
 * @param packageContext [Context] 上下文
 * @param cls [Class] 目标类
 * @param name [String]
 * @param value [String]
 */
fun ActivityResultLauncher<Intent>.launch(packageContext: Context, cls: Class<*>,name:String,value:String) {
    launch(packageContext,cls){intent ->
        intent.apply {
            putExtra(name,value)
        }
    }
}

/**
 * 启动 `Activity`
 * @param packageContext [Context] 上下文
 * @param cls [Class] 目标类
 * @param map [Map]
 */
fun ActivityResultLauncher<Intent>.launch(packageContext: Context, cls: Class<*>,map:Map<String,Any?>) {
    val intent = Intent(packageContext, cls).apply {
        putExtra(map)
    }
    launch(intent)
}

/**
 * 获取`Intent`传输数据
 * @param name [String]
 * @param defaultValue [Any]
 */
fun Intent.getExtra(name:String,defaultValue:Any):Any?{
    return when(defaultValue){
        is Boolean ->getBooleanExtra(name, defaultValue)
        is Byte -> getByteExtra(name,defaultValue)
        is Char -> getCharExtra(name,defaultValue)
        is Short -> getShortExtra(name,defaultValue)
        is Int -> getIntExtra(name,defaultValue)
        is Long ->getLongExtra(name,defaultValue)
        is Float ->getFloatExtra(name,defaultValue)
        is Double ->getDoubleExtra(name,defaultValue)
        is String-> getStringExtra(name)
        else ->null
    }
}

/**
 * 设置`Intent`传输数据
 * @param map [Map]
 */
fun Intent.putExtra(map:Map<String,Any?>){
    map.asSequence().forEach {
        when(it.value){
            is Boolean ->putExtra(it.key,it.value as Boolean)
            is Byte -> putExtra(it.key,it.value as Byte)
            is Char -> putExtra(it.key,it.value as Char)
            is Short -> putExtra(it.key,it.value as Short)
            is Int -> putExtra(it.key,it.value as Int)
            is Long ->putExtra(it.key,it.value as Long)
            is Float ->putExtra(it.key,it.value as Float)
            is Double ->putExtra(it.key,it.value as Double)
            is String-> putExtra(it.key,it.value as String)
        }
    }
}

/**
 * 启动 `Activity`
 * @param packageContext [Context] 上下文
 * @param cls [Class] 目标类
 * @param block [Block]
 */
fun ActivityResultLauncher<Intent>.launch(packageContext: Context, cls: Class<*>,block: (Intent) -> Intent) {
    launch(block(Intent(packageContext, cls)))
}

/**
 * 请求批量`Permission` 权限
 * @param callback [ActivityResultCallback]
 * @return [ActivityResultLauncher]
 */
fun AppCompatActivity.requestMultiplePermissions(
    @NonNull callback: ActivityResultCallback<Map<String,Boolean>>
):ActivityResultLauncher<Array<String>> {
    val requestMultiplePermissions :ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions : Map<String, Boolean> ->
            callback.onActivityResult(permissions)
        }
    return requestMultiplePermissions
}

/**
 * 请求一条`Permission` 权限
 * @param callback [ActivityResultCallback]
 * @return [ActivityResultLauncher]
 */
fun AppCompatActivity.requestPermission(
    @NonNull callback: ActivityResultCallback<Boolean>
):ActivityResultLauncher<String> {
    val requestPermissions :ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted  ->
            callback.onActivityResult(isGranted)
        }
    return requestPermissions
}

/**
 * 请求一条`Permission` 权限
 * @param callback [ActivityResultCallback]
 * @return [ActivityResultLauncher]
 */
fun AppCompatActivity.requestPermission(
    @NonNull success:() ->Unit,@NonNull failed:() ->Unit
):ActivityResultLauncher<String> {
    val requestPermissions :ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted  ->
            if(isGranted){
                success()
            }else{
                failed()
            }
        }
    return requestPermissions
}

/** 申请批量 `Permission` 权限
 * @param input [String]
 */
fun ActivityResultLauncher<Array<String>>.launch(input:Array<String>) {
    launch(input)
}

/** 申请 `Permission` 权限
 * @param input [String]
 */
fun ActivityResultLauncher<String>.launch(input:String) {
    launch(input)
}