package com.juntadeandalucia.ced.commons.text

class DocumentValidator {

    enum class Letter (letter: String) {
        T ("T"),
        R("R"),
        W("W"),
        A("A"),
        G("G"),
        M("M"),
        Y("Y"),
        F("F"),
        P("P"),
        D("D"),
        X("X"),
        B("B")

    }

    fun validateDni(dni: String): Boolean{
        return if (dni.length != 9) false
        else{
            if(Character.isDigit(dni[0])){
                val number = dni.substring(0,8)
                val positon = number.toInt()%23
                if(Letter.values()[positon].name.equals(dni.substring(8)))
                    return true
            }
            return false
        }

    }
}