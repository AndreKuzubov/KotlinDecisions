package coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
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
        cont.resumeWithException(Throwable("world"))
    }
}

fun main() {
    val job = GlobalScope.launch {
        val world = createWorld()

        val hello = try {
            createHello()
        } catch (ex: Exception) {
            "ex"
        }

        println()
        println("$hello $world")
    }.apply {
        start()
    }


    while (job.isActive)
        sleep(1000)


}