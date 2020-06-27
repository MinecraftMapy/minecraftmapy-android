package pl.kapiz.minecraftmapy.data.api

import pl.kapiz.minecraftmapy.utils.RefreshLiveData
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return if (getRawType(returnType) != RefreshLiveData::class.java) null
        else getParameterUpperBound(0, returnType as ParameterizedType).let {
            if (getRawType(it) != ApiResponse::class.java) throw IllegalArgumentException("Type must be a ApiResponse.")
            if (it !is ParameterizedType) throw IllegalArgumentException("ApiResponse must be parameterized.")
            LiveDataCallAdapter<Any>(getParameterUpperBound(0, it))
        }
    }
}
