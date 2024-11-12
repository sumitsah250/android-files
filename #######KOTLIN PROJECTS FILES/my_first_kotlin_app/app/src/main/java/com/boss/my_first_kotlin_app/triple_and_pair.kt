package com.boss.my_first_kotlin_app

class triple_and_pair {
    companion object{

        @JvmStatic
        fun main(args : Array<String>){

            val (a,b) = Pair("A",1)
            println("$a $b")
            val name = Pair("sumit",2);
            println("${name.first} ${name.second}")
            val name1 = Pair("sumit",Pair("Aaditya",3));
            println("${name1.first} ${name1.second.first}")


            val(x,y,z)= Triple("Sumit",2,"Aaditya")

        }

    }
}