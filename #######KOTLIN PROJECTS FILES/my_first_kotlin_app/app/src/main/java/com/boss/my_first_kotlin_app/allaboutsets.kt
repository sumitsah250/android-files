package com.boss.my_first_kotlin_app

class allaboutsets {

    companion object{

        @JvmStatic
        fun main(args : Array<String>){
            val aset = setOf("12",12,34,12,"sumit")
            println(aset)

            val mSet = mutableSetOf<Any>("sumit",3,4);
            mSet.add(true);
            println(mSet)

            val aMap = mapOf<Int,String>(1 to "sumit",10 to "Rahul")
            println(aMap)

            val mMap = mutableMapOf(1 to "Sumit","A" to " AASHISH");
            val bMap = mutableMapOf(1 to "Sumit","A" to " AASHISH");
            mMap.put(2,"sumit")
            mMap.putAll(bMap)
            println(mMap)



        }

    }
}