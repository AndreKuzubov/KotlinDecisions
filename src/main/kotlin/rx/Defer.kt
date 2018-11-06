package rx

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers




fun main(arg: Array<String>) {

    val obs = Observable.create<String> {
        println("creating values Thread: ${Thread.currentThread().id}")
        it.onNext("String 1")
        Thread.sleep(1000)
        it.onNext("String 2")
        it.onComplete()
    }
            .subscribeOn(Schedulers.computation())
            .publish()
    obs.connect()


    val obs2 = Observable.defer { ->
        println("body of defer thread: ${Thread.currentThread().id}")

        obs.mergeWith(Observable.just("23"))
    }
    for (i in 0..2)
        obs2.subscribe({
            println("${it} thread: ${Thread.currentThread().id}")
        }, {
            println("error thread: ${Thread.currentThread().id}")
        }, {
            println("complete thread: ${Thread.currentThread().id}")

        })

    while (true)
        Thread.sleep(1000)

}