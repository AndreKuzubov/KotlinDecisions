package rx

import io.reactivex.Observable

/**
 * Обьединение Источников
 *
 * @AndreyKuzubov
 */

fun main(arg: Array<String>) {
    val o = Observable.just(1, 2, 4)
    val o2 = Observable.just(2, 4, 5)

    o.mergeWith(o2)
            .subscribe({
                println("onNext $it")
            })
}