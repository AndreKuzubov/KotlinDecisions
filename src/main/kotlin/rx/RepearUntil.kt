package rx

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

var counter = 10

/**
 *
 * Повторение операций
 *
 * @AndreyKuzubov
 *
 */

fun getStatus(): Observable<Int> {
    return Observable.create<Int> {
        it.onNext(counter--)
        it.onComplete()
    }
            .subscribeOn(Schedulers.computation())
}

fun main(arg: Array<String>) {
    getStatus()
            .repeatWhen {
                it.delay(1,TimeUnit.SECONDS)
            }
            .takeWhile {
                it>0
            }
            .subscribe {
                println("subscribe ${it}")

            }

    while (true){
        Thread.sleep(100)
    }
}