package MVVM_coroutine

import kotlinx.coroutines.*
import java.util.*

class Repository {

    val loginHash = "user".hashCode()
    val passwordHash = "pass123!!".hashCode()

    fun createConnectToken(): Deferred<String> = GlobalScope.async {
        println("createConnectToken")

        delay(1000)
        UUID.randomUUID().toString()
    }

    fun getAuthVersion(): Deferred<Int> = GlobalScope.async {
        println("getAuthVersion")

        delay(1000)
        2
    }

    fun login(login: String, password: String, token: String): Deferred<Boolean> = GlobalScope.async {
        println("login")

        delay(1000)
        val logOk = login.hashCode() == loginHash
        val passOk = password.hashCode() == passwordHash
        delay(1000)
        logOk && passOk
    }

    fun sendClientSomeData(): Job = GlobalScope.async {
        println("sendClientSomeData")
        delay(1000)
    }
}