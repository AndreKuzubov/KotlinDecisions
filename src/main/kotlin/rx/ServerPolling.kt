package rx

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit


val serverJobs: MutableMap<String, Long> = HashMap<String, Long>()

/**
 * Пуллинг сервера
 *
 * @AndreyKuzubov
 */

fun startServerJob(timeDelay: Long): Single<String> {
    return Single.create<String> {
        println("Starting job ${timeDelay}")
        val jobId = UUID.randomUUID().toString()
        serverJobs.put(jobId, System.currentTimeMillis() + timeDelay)
        it.onSuccess(jobId)
    }
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())

}

fun getJobStatus(jobId: String): Single<String> {
    return Single.create<String> {
        println("getting job status ${jobId}")
        when {
            !serverJobs.containsKey(jobId) -> it.onSuccess("NO_JOB")
            serverJobs[jobId]!! > System.currentTimeMillis() -> it.onSuccess("IN_PROGRESS")
            else -> it.onSuccess("FINISH")
        }
    }
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())


}


fun example1() {
    val d = Observable
            .just(10000)
            .subscribeOn(Schedulers.io())
            .flatMapSingle {
                startServerJob(it.toLong())
            }
            .flatMapSingle { jobId ->
                getJobStatus(jobId)
                        .repeatWhen { it.delay(1, TimeUnit.SECONDS) }
                        .takeUntil {
                            it == "IN_PROGRESS"
                        }

                        .lastElement()
                        .toSingle()
            }
            .observeOn(Schedulers.computation())

    val disposable = d.subscribe({
        println("subsctribe ${it}")
    }, {
        println("subsctribe error ${it.message}")
        it.printStackTrace()
    }, {
        println("onComplete")
    })
}


fun exampleOncePolling() {

    val scheduler = Schedulers.newThread()

    var polling = false

    val d =
            Observable.just(10000, 1234, 3432)
                    .flatMapSingle {
                        while (polling)
                            Thread.yield()
                        polling = true
                        startServerJob(it.toLong())
                    }
                    .subscribeOn(scheduler)
                    .flatMapSingle { jobId ->
                        getJobStatus(jobId)
                                .repeatWhen { it.delay(1, TimeUnit.SECONDS) }
                                .takeUntil {
                                    it != "IN_PROGRESS"
                                }
                                .lastElement()
                                .toSingle()
                                .doFinally {
                                    polling = false
                                }
                    }
                    .observeOn(Schedulers.computation())

    val disposable = d.subscribe({
        println("subsctribe ${it}")
    }, {
        println("subsctribe error ${it.message}")
        it.printStackTrace()
    }, {
        println("onComplete")
    })
}

fun main(arg: Array<String>) {

    exampleOncePolling()

    while (true)
        Thread.sleep(1000)

}