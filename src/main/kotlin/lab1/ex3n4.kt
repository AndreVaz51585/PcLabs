package org.example.lab1


fun main() {

    //----------------------------------------------- VARIANTE 1 --------------------------------------------
 /* val A = Thread({
      for (i in 1..20)
      println("A$i")
    //  Thread.sleep(5)
  }, "A")


    val B = Thread({
        for (i in 1..20)
            println("B$i")
      //  Thread.sleep(5)
    }, "B")


    A.start()
    B.start()
*/

// ----------------------------------------------------- VARIANTE 2 ---------------------------------------------------

    val A = Thread({
        for (i in 1..100)
            if(i % 10 == 0) println("A$i")
          Thread.sleep(5)
    }, "A")


    val B = Thread({
        for (i in 1..100)
            if(i % 10 == 0) println("B$i")
          Thread.sleep(5)
    }, "B")


    A.start()
    B.start()



}

/**
 *
 * EX3:
 * Ao validdarmos com vários Thread.sleep() em ordens diferentes , com valores diferentes e sem o sleep , a execução dos print ocorre de forma alteranda
 * não com ordem claro , basicamente o processo de interleaving , onde as duas as instruções de ambas as threads são executadas de forma alterando com mixed order
 *
 *
 * EX4:
 *
 * Olhando para ambos as variantes , temos que com Heavy instrumentation(var 1) existe interleaving , contudo , em low instrumentation(var 2 ) não existe interleaving , apesar da orddem de execução e aparecimento ser diferente
 * de execução para execução ; Isto para 0..20
 *
 * Para ranges maiores 0..100 por exemplo já temos interleaving.
 *
 * A conclusão a que chegamos é que o Print , é uma operação muito custosa , isto porque obriga a chamadas syscall em vários casos , passar para modo kernel e internamente pode bloquear a Thread,
 * o sheduller podde escolher outra thread tal como estamos a validar com o interleaving , além disso I/O obriga o sheduller a intervir.
 */