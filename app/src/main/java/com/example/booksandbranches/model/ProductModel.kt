package com.example.booksandbranches.model

import android.os.Parcel
import android.os.Parcelable

data class ProductModel(
    var productId: String,
    var productName: String = "",
    var author: String = "",
    var price: Double = 0.0,
    val imageRes: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productId)
        parcel.writeString(productName)
        parcel.writeString(author)
        parcel.writeDouble(price)
        parcel.writeInt(imageRes)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ProductModel> {
        override fun createFromParcel(parcel: Parcel): ProductModel {
            return ProductModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductModel?> {
            return arrayOfNulls(size)
        }
    }
}