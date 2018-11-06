package rx

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main(arg: Array<String>) {
    val obs = Observable.create<String> {
        println("creating values Thread: ${Thread.currentThread().id}")

        Thread.sleep(20000)
        it.onNext("String 1")
        Thread.sleep(1000)
        it.onNext("String 2")
        it.onComplete()
    }
            .subscribeOn(Schedulers.computation())

    println("blockin first ${obs.blockingFirst()}")
}