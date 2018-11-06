package rx

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 *
 * События с мин интервалом
 *
 * @AndreyKuzubov
 */

fun main(arg: Array<String>) {
    Observable.create<Int> {
        it.onNext(12)
        it.onNext(2)
        it.onNext(4)
        Thread.sleep(100)
        it.onNext(4)

        Thread.sleep(10000)
        it.onNext(443)

        Thread.sleep(10000)
        it.onNext(44)
    }
            .debounce(1, TimeUnit.SECONDS)
            .subscribe {
                println("items: $it")
            }

    while (true) {
        Thread.sleep(1000)
    }
}