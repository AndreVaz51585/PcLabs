package org.example.lab1
import java.net.ServerSocket



fun serverSocket(port : Int){

    println("Starting Multi-threaded echo server on port $port...")
    ServerSocket(port).use { serverSocket ->
        while (true) {
            println("Waiting for a client...")
           val cliSocket = serverSocket.accept()
            Thread({
              cliSocket.use {
                println("Client connected: ${it.inetAddress.hostAddress}")
                val input = it.getInputStream().bufferedReader()
                val output = it.getOutputStream().bufferedWriter()
                input.lineSequence().forEach { line ->
                    println("Received: $line")
                    output.write("Echo: $line\n")
                    output.flush()
                }
                println("Client disconnected.")


              }
            },"Thread-${cliSocket.inetAddress.hostAddress}").start()
        }
    }
}

fun main() {
    serverSocket(12345)
}