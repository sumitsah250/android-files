package com.boss.my_first_kotlin_app

class all_avout_array_List  {
    companion object{

        @JvmStatic
        fun main(args : Array<String>){
            val arrNames = ArrayList<String>()
            arrNames.add("A")
            arrNames.add("B")
            arrNames.add("C")
            arrNames[0]="D"
            arrNames.remove("B")
            arrNames.removeAt(1)
            println(arrNames)

        }

    }
}