package rx

import io.reactivex.Observable
import io.reactivex.functions.BiFunction

fun main(arg: Array<String>) {

    Observable.zip(
            Observable.just(1, 2, 3),
            Observable.just("2", "4", "5"),
            BiFunction<Int, String, Unit> { a, b ->
                println("a = $a  b = $b")
            }
    ).subscribe()

    println()

    Observable.just(1, 2, 3)
            .zipWith(Observable.just("2", "4", "5"),
                    BiFunction<Int, String, Unit> { a, b ->
                        println("a = $a  b = $b")
                    }
            ).subscribe()

}