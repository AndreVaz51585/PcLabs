package org.example.lab1


fun startAndObserve (thread : Thread){

    var prevState = thread.state


    println("<${System.currentTimeMillis()}> <${thread.name}> <${thread.state}> ")
    thread.start()

    while (thread.state != Thread.State.TERMINATED){
        val curr = thread.state
        if(curr != prevState) {
            println("<${System.currentTimeMillis()}> <${thread.name}> <$curr>")
            prevState = curr
        }
            Thread.sleep(500)
    }

    println("<${System.currentTimeMillis()}> <${thread.name}> <${thread.state}> ")
}


fun main() {
    val thread = Thread({
        Thread.sleep(500)
    }, "Test Thread")

    val sleeper = Thread({
        println("Starts")
        Thread.sleep(2000)
        println("Ends")
    }, "Sleeper")


    startAndObserve(sleeper)
}

/**
 * Explicação ex1
 *
 * Portanto aqui estamos a avaliar o estado de uma thread em concreto a cada x ms , realizando portanto polling.
 * O que está acontencer é que na main definimos então a Thread->thread que ao realizar thread.start() começa a task de dormir durante(500 ms)
 * Portanto a exeecução do código ocorre da seguinte forma :
 * Main Thread :                                                                                               "Test Thread"
 * chama a função startAndObserve , e guarda estado , imprime o mesmo , e realiza o thread.start() --->       vai fazer um sleep durante 500ms
 * enquanto está a "dormir" avaliamos o estado da mesma e validamos o "TIMED_WAITING" de seguida bloqueamos
 * a main thread e voltamos a fazer a avaliação até acabar o sleep de 500ms da test thread ee o programa terminar
 *
 *
 * Explicação ex2:
 *
 * Ao executar várias vezes e com diferentes valores de p, surgiu-me uma questão , que é o seguinte output:
 * <1772296550723> <Sleeper> <NEW>
 * <1772296550731> <Sleeper> <RUNNABLE>
 * Starts
 * <1772296551242> <Sleeper> <TIMED_WAITING>
 * Ends
 * <1772296552771> <Sleeper> <TERMINATED>
 *
 *
 * com valores de p na gama 0..500 ms , o RUNNABLE aparece sempre primeiro que o start , mas acima disso e em algumas execuções , o starts aparece primeiro
 * o que é estranho visto que o thread.starrt() deveria imprimir primeiro o "Starts" antes de entrar no ciclo while e fazer a respetiva validação de mudança de estado
 * Com os sleeps antes da validação da mudança
 *
 *
 * Com o Thread.sleep dentro do while , e antes da atribuição do variavél de estado curr , perdemos o estado RUNNABLE , ou seja ele entra em RUNNABLE mas ao dormir existe uma mudança de estado
 * o que a impediu de ser captada
 */