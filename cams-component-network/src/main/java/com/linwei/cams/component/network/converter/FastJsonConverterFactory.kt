package com.linwei.cams.component.network.converter

import com.alibaba.fastjson.serializer.SerializeConfig
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class FastJsonConverterFactory(private val serializeConfig: SerializeConfig) : Converter.Factory() {

    companion object{

        fun create():FastJsonConverterFactory{
            return create(SerializeConfig.globalInstance)
        }

        private fun create(serializeConfig:SerializeConfig):FastJsonConverterFactory{
            return FastJsonConverterFactory(serializeConfig)
        }
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        return FastJsonRequestBodyConverter<Any>(serializeConfig)
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return FastJsonResponseBodyConverter<Any>(type)
    }
}