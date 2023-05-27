import com.google.gson.annotations.SerializedName

/*
Creator Javohirbek
Created on : 22.05.2023
*/

data class Currency(
    @SerializedName("Ccy")
    val currencyCode: String,
    @SerializedName("CcyNm_UZ")
    val currencyName: String,
    @SerializedName("Rate")
    val rate: String,
    @SerializedName("Date")
    val date: String
){
    override fun toString(): String {
        return "$currencyCode : $rate"
    }
}
