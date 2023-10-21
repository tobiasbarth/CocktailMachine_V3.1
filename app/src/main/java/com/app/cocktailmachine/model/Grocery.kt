package com.app.cocktailmachine.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable



//enum class Grocery(val label: String, var spot: Byte, var available: Boolean) : Serializable {
//    @SerializedName("Maracujasaft")
//    PASSION_FRUIT_JUICE("Maracujasaft", 1, true),
//
//    @SerializedName("Orangensaft")
//    ORANGE_JUICE("Orangensaft",2, true),
//
//    @SerializedName("Ananassaft")
//    PINEAPPLE_JUICE("Ananassaft",3, true),
//
//    @SerializedName("Zitronensaft")
//    LEMON_JUICE("Zitronensaft",4, true),
//
//    @SerializedName("Grenadinensirup")
//    GRENADINE_SYRUP("Grenadinensirup",5, true),
//
//    @SerializedName("Wodka")
//    VODKA("Wodka",6, true),
//
//    @SerializedName("Kokossirup")
//    COCONUT_SYRUP("Kokossirup",7, true),
//
//    @SerializedName("Orangenlikör")
//    ORANGE_LIQUEUR("Orangenlikör",8, true),
//
//    @SerializedName("Gin")
//    GIN("Gin",9, true),
//
//    @SerializedName("Brauner Rum")
//    BROWN_RUM("Brauner Rum",10, true),
//
//    @SerializedName("Tequila")
//    TEQUILA("Tequila",11, true),
//
//    @SerializedName("Mandelsirup")
//    ALMOND_SYRUP("Mandelsirup",12, true),
//
//    @SerializedName("Brandy")
//    BRANDY("Brandy",13, true),
//
//    @SerializedName("Blue Curacao")
//    BLUE_CURACAO("Blue Curacao",14, true),
//
//    @SerializedName("Sahne")
//    CREAM("Sahne",15, true),
//
//    @SerializedName("Weißer Rum")
//    WHITE_RUM("Weißer Rum",16, true),
//
//    @SerializedName("Bananensaft")
//    BANANA_JUICE("Bananensaft",0, false),
//
//    @SerializedName("Kokos Rum")
//    COCONUT_RUM("Kokos Rum",0, false),
//
//    @SerializedName("Cola")
//    COLA("Cola",0, true)
//
//}


enum class Grocery(val label: String, var spot: Byte, var available: Boolean) : Serializable {
    @SerializedName("Maracujasaft")
    PASSION_FRUIT_JUICE("Maracujasaft", 0, false),

    @SerializedName("Orangensaft")
    ORANGE_JUICE("Orangensaft",0, false),

    @SerializedName("Ananassaft")
    PINEAPPLE_JUICE("Ananassaft",0, false),

    @SerializedName("Zitronensaft")
    LEMON_JUICE("Zitronensaft",0, false),

    @SerializedName("Grenadinensirup")
    GRENADINE_SYRUP("Grenadinensirup",0, false),

    @SerializedName("Wodka")
    VODKA("Wodka",0, false),

    @SerializedName("Kokossirup")
    COCONUT_SYRUP("Kokossirup",0, false),

    @SerializedName("Orangenlikör")
    ORANGE_LIQUEUR("Orangenlikör",0, false),

    @SerializedName("Gin")
    GIN("Gin",0, false),

    @SerializedName("Brauner Rum")
    BROWN_RUM("Brauner Rum",0, false),

    @SerializedName("Tequila")
    TEQUILA("Tequila",0, false),

    @SerializedName("Mandelsirup")
    ALMOND_SYRUP("Mandelsirup",0, false),

    @SerializedName("Brandy")
    BRANDY("Brandy",0, false),

    @SerializedName("Blue Curacao")
    BLUE_CURACAO("Blue Curacao",0, false),

    @SerializedName("Sahne")
    CREAM("Sahne",0, false),

    @SerializedName("Weißer Rum")
    WHITE_RUM("Weißer Rum",0, false),

    @SerializedName("Bananensaft")
    BANANA_JUICE("Bananensaft",0, false),

    @SerializedName("Kokos Rum")
    COCONUT_RUM("Kokos Rum",0, false),

    @SerializedName("Cola")
    COLA("Cola",0, false)

}


