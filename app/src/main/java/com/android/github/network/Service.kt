package com.android.github.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * A public interface that exposes the [getRepositories] method
 */
interface GitHubService {
    /**
     * Returns a Coroutine [Deferred] [List] of [GitHubRepository] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "search/repositories" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("search/repositories?q=stars:>=10000&sort=stars&order=desc")
    fun getRepositories():
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<GitHubRepositoryContainer>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object GitHubNetwork {
    /**
     * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
     * object.
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val github = retrofit.create(GitHubService::class.java)
}
