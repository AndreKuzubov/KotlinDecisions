package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

val m = Mutex(false)

private suspend fun generateWorld_criticalSection(): Deferred<String> {
    return GlobalScope.async {
        m.withLock {
            println("generateWorld")
            delay(2000L)
            "world"
        }
    }
}


fun main() {
    val j = GlobalScope.launch {
        val world = generateWorld_criticalSection()
        val world2 = generateWorld_criticalSection()

        println()
        println("${world.await()} ${world2.await()}")
    }.apply { start() }

    while (j.isActive)
        Thread.sleep(100)

}
