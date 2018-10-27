import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private val rxDis: CompositeDisposable = CompositeDisposable()

/*
* Пример отображающий различие м/у Hot и Cold Observables
*
* @AndreyKuzubov
*/


private fun hotObservable(): Observable<Long> {
    val obs = Observable.interval(0, 1, TimeUnit.SECONDS)
            .publish()

    rxDis.addAll(
            obs.connect()
    )
    return obs
}

private fun coldObservable(): Observable<Long> {
    val obs = Observable.interval(0, 1, TimeUnit.SECONDS)
    return obs
}

fun main(arg: Array<String>) {
    println("main thread: ${Thread.currentThread().id}")


    val obs = coldObservable()


    Thread.sleep(3000)

    for (i in 0..1) {
        println("subribing thread: ${Thread.currentThread().id}")
        val o = when (i) {
            0 -> obs.subscribeOn(Schedulers.computation())
            else -> obs.observeOn(Schedulers.computation())
        }
        rxDis.addAll(
                o.subscribe(
                        { it -> println("sub${i} thread ${Thread.currentThread().id}: ${it}") },
                        { it -> println("err${i}: " + it.message + " stack: " + it.stackTrace) },
                        { println("oncomplete${i}") }
                )
        )
        Thread.sleep(3000)

    }


    Thread.sleep(10000)
    while (true)
        Thread.sleep(100000)

    rxDis.dispose()

}