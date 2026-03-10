package org.example.lab2

import org.example.lab1.startAndObserve

val worker = Thread(
     {
        Thread.sleep(2000)

    },"Worker Thread"
)


val waiter = Thread({
    worker.join()
    println("Worker Finished")
},"Waiter Thread")


fun main() {

    worker.start()
    startAndObserve(waiter)

}