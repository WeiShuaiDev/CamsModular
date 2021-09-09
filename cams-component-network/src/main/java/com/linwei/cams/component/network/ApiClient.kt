package com.linwei.cams.component.network

class ApiClient private constructor() {

    private var mApiServiceMap:Map<String,Any> = mutableMapOf()

    companion object{
        private var INSTANCE:ApiClient?=null

        @JvmStatic
        fun getInstance():ApiClient{
            return INSTANCE?:ApiClient().apply {
                INSTANCE=this
            }
        }
    }
}