package rx

import io.reactivex.Observable


fun main(arg: Array<String>) {


    val obs = Observable.create<String> {
        println("creating values Thread: ${Thread.currentThread().id}")
        it.onNext("String 1")
        it.onNext("String 2")
        it.onComplete()
    }.publish()
    obs.connect()


    val obs2 = Observable.defer { ->
        println("body of defer thread: ${Thread.currentThread().id}")
        obs
    }
    for (i in 0..2)
        obs2.subscribe({
            println("${it} thread: ${Thread.currentThread().id}")
        })


}