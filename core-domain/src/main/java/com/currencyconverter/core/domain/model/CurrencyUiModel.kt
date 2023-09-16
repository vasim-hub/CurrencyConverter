package com.currencyconverter.core.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.currencyconverter.model.BaseAutoCompleteModel
import java.util.Locale

data class CurrencyUiModel(val code: String, val text: String) : BaseAutoCompleteModel, Parcelable {

    override fun filter(query: String): Boolean {
        return text.lowercase(Locale.getDefault())
            .contains(query.lowercase(Locale.getDefault()))
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(text)
    }

    companion object CREATOR : Parcelable.Creator<CurrencyUiModel> {
        override fun createFromParcel(parcel: Parcel): CurrencyUiModel {
            return CurrencyUiModel(
                code = parcel.readString() ?: "",
                text = parcel.readString() ?: ""
            )
        }

        override fun newArray(size: Int): Array<CurrencyUiModel?> {
            return arrayOfNulls(size)
        }
    }
}