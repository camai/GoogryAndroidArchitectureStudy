package com.android.studyfork.repository

import com.android.studyfork.network.model.MarketResponse
import com.android.studyfork.network.model.TickerResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * created by onemask
 */
interface UpbitDataSource {
    @GET("market/all")
    fun getMarketAll() : Single<List<MarketResponse>>

    @GET("ticker")
    fun getTikers(@Query("markets") markets : String? ): Single<List<TickerResponse>>

}