package com.example.wealthwings

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET("query?function=SYMBOL_SEARCH")
    suspend fun searchStocks(
        @Query("keywords") keywords: String,
        @Query("apikey") apiKey: String = API_KEY
    ): StockSearchResponse

    @GET("query?function=OVERVIEW&apikey=$API_KEY")
    suspend fun getCompanyOverview(
        @Query("symbol") symbol: String,
    ): CompanyOverviewResponse

    @GET("query?function=TIME_SERIES_INTRADAY&interval=5min&apikey=$API_KEY")
    suspend fun getIntradayPrices(@Query("symbol") symbol: String): TimeSeriesResponse

    companion object {
        const val API_KEY = "3BX4UJWT19AEUR9O" // "VMQS9W0UD0UQ82XF"
        const val BASE_URL = "https://www.alphavantage.co/"
    }
}

data class TimeSeriesResponse(
    @SerializedName("Time Series (5min)")
    val timeSeries: Map<String, TimeSeriesEntry>
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
