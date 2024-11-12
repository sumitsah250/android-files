package com.boss.my_first_kotlin_app

class generalvariable  {
    companion object{

        @JvmStatic
        fun main(args : Array<String>){

            var a = "sumit"
            val b= " aashis" // this cannot be reassigned agan it is live final
            println("hello world $a")
            println("The sum is ${add(4,5)}") // many things are in lambda use carly brackets


        }
         private fun add( a:Int, b:Int) : Any{
            return a+b

        }
    }
}