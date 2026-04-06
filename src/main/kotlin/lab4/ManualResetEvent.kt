package lab4

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class ManualResetEvent {

    private val guard = ReentrantLock()

    private val condition = guard.newCondition()

    private var isSignaled = false

    private var generation = 0


    fun set() = guard.withLock {
        isSignaled = true
        generation++
        condition.signalAll()

    }


    fun reset() = guard.withLock {
        isSignaled = false
    }

    fun waitOne() = guard.withLock {

        // FAST PATH
        if (isSignaled) {
            return
        }


        val currentGen = generation

        while (true) {

            condition.await()

            // Race condition, with was signaled but, before it could get the lock it was reset, therefore must be released
            if (isSignaled || currentGen != generation) {
                return
            }


        }

    }

    @Throws(InterruptedException::class)
    fun waitOne(timeout: Long, unit: TimeUnit): Boolean {
        guard.withLock {
            // FAST PATH
            if (isSignaled) {
                return true
            }

            val current = generation

            var remainingTimeInNanos = unit.toNanos(timeout)

            while (true) {
                try {

                    if (remainingTimeInNanos <= 0) return false

                    remainingTimeInNanos = condition.awaitNanos(remainingTimeInNanos)


                    if (isSignaled || current != generation) {
                        return true
                    }


                } catch (e: InterruptedException) {

                    if (isSignaled || current != generation) {
                        Thread.currentThread().interrupt()
                        return true
                    }

                    throw e
                }

            }


        }

    }

}