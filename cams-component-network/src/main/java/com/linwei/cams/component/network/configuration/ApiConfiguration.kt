package com.linwei.cams.component.network.configuration

/**
 * 网络环境切换
 */
class ApiConfiguration(builder: Builder) {
    val apiEnv = builder.apiEnv

    companion object {

        fun builder(): Builder {
            return Builder()
        }

        class Builder {
            var apiEnv: ApiEnv? = ApiEnv.ENV_TEST

            fun apiEnv(apiEnv: ApiEnv): Builder {
                this.apiEnv = apiEnv
                return this
            }

            fun build(): ApiConfiguration {
                return ApiConfiguration(this)
            }
        }
    }

}
