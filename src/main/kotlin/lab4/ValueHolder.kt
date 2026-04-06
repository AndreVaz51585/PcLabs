package lab4

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.time.Duration

class ValueHolder(private var value: Int? = null) {

    private val guard = ReentrantLock()
    private val condition = guard.newCondition()

    /**
     * Blocks the calling thread until the value is available.
     */
    @Throws(InterruptedException::class)
    fun getValue(timeout : Long, unit : TimeUnit): Int? {
        guard.withLock {

            val observedValue: Int? = value
            if (observedValue != null) {
                return observedValue
            }


            var remaingTimeInNanos = unit.toNanos(timeout)

            while (true) {

                /***
                 * o condition.awaitNanos() é um metodo que retorna uma estimitava do tempo que passou.
                 * Quando o valor >0, ocorreu um spurios wakeup, ou seja acordou sozinha, sem que o signalAll() tenha sido chamado, ou seja, sem que o valor tenha sido atualizado.
                 * Quando o valor <=0, ocorreu um timeout, ou seja, terminou o tempo de espera
                 */

               remaingTimeInNanos =  condition.awaitNanos(remaingTimeInNanos)


                val observedValue = value
                if (observedValue != null) {
                    return observedValue
                }

                if(remaingTimeInNanos <= 0) {
                    return null
                }

            }
        }
    }

    /**
     * Sets the value and notifies any waiting threads.
     */
    fun putValue(value: Int): Unit = guard.withLock {

        check(this.value != null) { "Value already set" }

        this.value = value
        condition.signalAll()
    }
}