package com.boss.my_first_kotlin_app

class loops  {
    companion object{

        @JvmStatic
        fun main(args : Array<String>){

            for(i in 0..10){
                println("Numbaer is : ${i}")
            }

         for(i in 0 until 10){
                println("Numbaer is : ${i}")
            }

            for(i in 10 downTo 0){
                println("Numbaer is : ${i}")
            }

            for(i in 10 downTo 0 step 2){
                println("Numbaer is : ${i}")
            }

            val arrnumbars = ArrayList<Int>()
            arrnumbars.add(1);
            arrnumbars.add(2);
            arrnumbars.add(3);
            arrnumbars.add(4);
            arrnumbars.add(5);
            for(i in arrnumbars){
                println("the number in array is $i")
            }


            //wile and do while  loop is save as java


        }

    }
}