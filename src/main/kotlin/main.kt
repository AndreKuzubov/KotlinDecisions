import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


private val stringState = BehaviorRelay.createDefault<String>("323")


fun main(arg: Array<String>) {
    val secondThread: Scheduler = Schedulers.newThread()
    secondThread.start()

    val treeThread: Scheduler = Schedulers.newThread()
    treeThread.start()

    val disposable = stringState
//            .subscribeOn(secondThread)
//            .observeOn(secondThread)
            .delay(2, TimeUnit.SECONDS)
//            .last(stringState.value)
            .subscribe({
                println(it + "  thread: " + Thread.currentThread().id.toString())
            }, {

            })

    stringState.accept("new1")
    stringState.accept("new2")
    stringState.accept("new3")


    val disposable2 = Observable
            .range(1, 20)
//            .subscribeOn(treeThread)
            .delay(1, TimeUnit.SECONDS)
//            .last(1)
            .subscribe { it ->
                println(it.toString() + " subscribe thread: " + Thread.currentThread().id.toString())
                stringState.accept("s" + it.toString())

            }
//            .subscribe()


    println("main thread sleep  thread: " + Thread.currentThread().id.toString())
    Thread.sleep(60000)
    println("finish main thread")

}


