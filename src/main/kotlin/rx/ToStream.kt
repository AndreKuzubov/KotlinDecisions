package rx

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main(arg: Array<String>) {

    val obs = Observable.create<String> {
        println("creating values Thread: ${Thread.currentThread().id}")

        Thread.sleep(10000)
        it.onNext("String 1")
        Thread.sleep(1000)
        it.onNext("String 2")
        it.onComplete()
    }
            .subscribeOn(Schedulers.computation())

    obs.toList()
            .observeOn(Schedulers.newThread())
            .subscribe { it ->
                println("list $it")
            }



    while (true) {
        Thread.sleep(100)
    }
}