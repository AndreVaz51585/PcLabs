package org.example.lab2

fun parallelSum(values: IntArray, nBlocks: Int): Long{

    val partitionSize = values.size/ nBlocks // gives us the total quantity of the numbers off each block

    val partials = LongArray(nBlocks) // existe memória partilhada nesta região, todas as threads acedem

    val threads = Array(nBlocks){ i ->
        Thread({
            val start = i * partitionSize
            val end = if (i == nBlocks - 1) values.size else start + partitionSize
            var localCount : Long= 0
                for(j in start until  end){
                    localCount += values[j]

                }
            partials[i] = localCount
            println(partials[i])
        },"count-partition $i")

    }

    threads.forEach { it.start() }
    threads.forEach { it.join() }

    return partials.sum()
}

fun main(){

    val value =
        parallelSum(intArrayOf(15,15,15,15,10,10,10,10,10), 4)


    println(value)

}