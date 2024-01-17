package tcla.libraries.jsonserialization

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

internal val MOSHI: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
