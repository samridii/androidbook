package com.example.booksandbranches.model

import android.os.Parcel
import android.os.Parcelable

data class BookDetailModel(
    var bookId: String,
    var title: String = "",
    var author: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var imageRes: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bookId)
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(description)
        parcel.writeDouble(price)
        parcel.writeInt(imageRes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookDetailModel> {
        override fun createFromParcel(parcel: Parcel): BookDetailModel {
            return BookDetailModel(parcel)
        }

        override fun newArray(size: Int): Array<BookDetailModel?> {
            return arrayOfNulls(size)
        }
    }
}