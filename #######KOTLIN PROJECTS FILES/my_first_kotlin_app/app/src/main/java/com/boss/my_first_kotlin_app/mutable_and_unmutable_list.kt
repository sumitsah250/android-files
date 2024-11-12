package com.boss.my_first_kotlin_app

class mutable_and_unmutable_list  {
    companion object{

        @JvmStatic
        fun main(args : Array<String>){
           val aList  = listOf("Sumit","aaditya","Aashish","chhotu")  // read only operation

            val mList = mutableListOf<Any>("Sumit", "Aashish")
            mList.add("Chhotu")
            mList.add(1)
            val mAList = mutableListOf("bikash",false)
            mList.addAll(mAList)

            println(mAList);
            println("mLIst")
            println(mList)


        }
        private fun add( a:Int, b:Int) : Any{
            return a+b
        }
    }
}