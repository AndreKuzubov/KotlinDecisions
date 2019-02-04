package coroutine

import kotlinx.coroutines.*
import java.lang.Thread.sleep
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private suspend fun createWorld(): String {
    return suspendCoroutine { cont ->
        println("generate world")

        sleep(2000L)
        cont.resume("world")
    }
}


private suspend fun createHello(): String {
    return suspendCoroutine { cont ->
        println("generate hello")

        sleep(500L)
        cont.resume("world")
//        cont.resumeWithException(Throwable("world"))
    }
}

fun main() {
    val job = GlobalScope.launch(Dispatchers.IO) {
        val world = async { createWorld() }
        val hello = async { createHello() }

        println()
        println("${world.await()} ${hello.await()}")
        println("${world.await()} ${hello.await()}")
    }.apply {
        start()
    }


    while (job.isActive)
        sleep(1000)


}