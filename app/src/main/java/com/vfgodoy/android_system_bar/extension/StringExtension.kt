package com.vfgodoy.android_system_bar.extension


fun String.toMoneyFormat() : String{

    val initial = this
    var result = if(this.contains(".")){
        val splitted = this.split(".")

        var integerPart = splitted[0]
        var decimalPart = splitted[1]
        val integer = integerPart.toIntOrNull()

        if(integer != null && integer == 0){
            integerPart = "00"
        }

        val decimalPlace = splitted[1].length
        if(decimalPlace == 2 ){
            "R$$integerPart,$decimalPart"
        }else if(decimalPlace >= 3){
            "R$${integerPart},${decimalPart.substring(0,2)}"
        } else{
            "R$$integerPart,${decimalPart}0"
        }


    }else{
        "R$$this,00"
    }

    println("TAGLIFE: result: $result - initial: $initial")

    return result

}