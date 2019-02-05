package MVVM_coroutine

import io.reactivex.schedulers.Schedulers

val vm = ViewModel()

var finish = false

fun main() {
    val loginsAtterms = listOf(
            null to "21",
            "123" to "32",
            "user" to "32",
            "user" to "pass123!!"
    )
    var atterm = 0

    vm.loginSubject
            .subscribeOn(Schedulers.single())
            .subscribe({
                if (!it && atterm < loginsAtterms.size) {
                    vm.login(loginsAtterms[atterm].first, loginsAtterms[atterm].second)
                    atterm++
                } else {
                    finish = true
                }
            })


    vm.login(null, "23432")

    while (!finish)
        Thread.sleep(100)

}