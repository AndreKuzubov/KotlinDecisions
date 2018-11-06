package rxDecisions

import delayEmits
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun exmpl1() {
    Observable.zip(
            Observable.just(1, 2, 4, 5, 6, 7),
            Observable.interval(5, TimeUnit.SECONDS),
            BiFunction<Int, Long, Int> { it, _ ->
                it
            })
            .subscribe {
                println("it $it time ${System.currentTimeMillis()}")
            }

}


fun example2() {
    Observable.just(1, 2, 4, 5, 6, 7)
            .delayEmits(2, TimeUnit.SECONDS, ignoreOffset = 2)
            .subscribeOn(Schedulers.computation())
            .subscribe {
                println("it $it time ${System.currentTimeMillis()}")
            }
}

fun main(arg: Array<String>) {
    example2()

    while (true)
        Thread.sleep(100)
}