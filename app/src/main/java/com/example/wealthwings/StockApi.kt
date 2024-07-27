package com.example.wealthwings

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET("query?function=SYMBOL_SEARCH")
    suspend fun searchStocks(@Query("keywords") keywords: String, @Query("apikey") apiKey: String = API_KEY): StockSearchResponse

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyOverview(@Query("symbol") symbol: String, @Query("apikey") apiKey: String = API_KEY): CompanyOverviewResponse

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyOverviewRaw(@Query("symbol") symbol: String, @Query("apikey") apiKey: String = API_KEY): ResponseBody

    @GET("query?function=TIME_SERIES_DAILY")
    suspend fun getDailyPrices(@Query("symbol") symbol: String, @Query("apikey") apiKey: String = API_KEY): DailyTimeSeriesResponse


    companion object {
        const val API_KEY = "3BX4UJWT19AEUR9O"
        const val BASE_URL = "https://www.alphavantage.co/"
    }
}

data class DailyTimeSeriesResponse(
    @SerializedName("Time Series (Daily)")
    val timeSeries: Map<String, TimeSeriesEntry>?
)


data class TimeSeriesEntry(
    @SerializedName("1. open")
    val open: String,
    @SerializedName("2. high")
    val high: String,
    @SerializedName("3. low")
    val low: String,
    @SerializedName("4. close")
    val close: String,
    @SerializedName("5. volume")
    val volume: String
)

data class CompanyOverviewResponse(
    @SerializedName("Symbol") val symbol: String?,
    @SerializedName("AssetType") val assetType: String?,
    @SerializedName("Name") val name: String?,
    @SerializedName("Description") val description: String?,
    @SerializedName("CIK") val cik: String?,
    @SerializedName("Exchange") val exchange: String?,
    @SerializedName("Currency") val currency: String?,
    @SerializedName("Country") val country: String?,
    @SerializedName("Sector") val sector: String?,
    @SerializedName("Industry") val industry: String?,
    @SerializedName("Address") val address: String?,
    @SerializedName("FiscalYearEnd") val fiscalYearEnd: String?,
    @SerializedName("LatestQuarter") val latestQuarter: String?,
    @SerializedName("MarketCapitalization") val marketCapitalization: String?,
    @SerializedName("EBITDA") val ebitda: String?,
    @SerializedName("PERatio") val peRatio: String?,
    @SerializedName("DividendPerShare") val dividendPerShare: String?,
    @SerializedName("EPS") val eps: String?,
    @SerializedName("RevenueTTM") val revenueTTM: String?,
    @SerializedName("GrossProfitTTM") val grossProfitTTM: String?,
    @SerializedName("QuarterlyEarningsGrowthYOY") val quarterlyEarningsGrowthYOY: String?,
    @SerializedName("QuarterlyRevenueGrowthYOY") val quarterlyRevenueGrowthYOY: String?
)

data class StockSearchResponse(
    @SerializedName("bestMatches")
    val matches: List<StockMatch>
)

data class StockMatch(
    @SerializedName("1. symbol")
    val symbol: String,
    @SerializedName("2. name")
    val name: String
)

