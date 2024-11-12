package com.boss.my_first_kotlin_app

class conditionalstatements  {
    companion object{

        @JvmStatic
        fun main(args : Array<String>){
            var num = 11
            var msg=""

            msg=if(num>100) "No not greater" else "No is smaller" // ternary  if else

            if( num>100){
                println("no is greater")
            }else{
                println("number is smaller")
            }
            println(msg)


            when(num){    /// its like switch
                11 ->{
                    println("this is 11")

                }
                100 ->{ println("this is 100")

                }
            }

            when{
                num>100 ->{println("this is greater")}
                num<100 ->{ println("this is smaller")

                }
            }


        }
        private fun add( a:Int, b:Int) : Any{
            return a+b
        }
    }
}