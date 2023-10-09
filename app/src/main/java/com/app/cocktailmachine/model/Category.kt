package com.app.cocktailmachine.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

enum class Category (val value: String) : Serializable {
    @SerializedName("Creamy")
    CREAMY("Creamy"),

    @SerializedName("Fresh")
    FRESH ("Fresh"),

    @SerializedName("Fruity")
    FRUITY ("Fruity"),

    @SerializedName("Strong")
    STRONG("Strong"),

    @SerializedName("Alkoholfrei")
    NON_ALCOLOLIC("Alkoholfrei")


}