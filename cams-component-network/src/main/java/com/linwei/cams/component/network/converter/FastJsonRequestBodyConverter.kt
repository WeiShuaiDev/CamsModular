package com.linwei.cams.component.network.converter

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeConfig
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Converter

class FastJsonRequestBodyConverter<T>(private val serializeConfig: SerializeConfig): Converter<T,RequestBody> {

    companion object {
        val MEDIA_TYPE: MediaType? = MediaType.parse("application/json; charset=UTF-8")
    }
    override fun convert(value: T): RequestBody? {
        return RequestBody.create(MEDIA_TYPE,JSON.toJSONBytes(value, serializeConfig))
    }
}