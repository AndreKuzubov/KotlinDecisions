import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

/*
*
* Решение прослушивания последнего изменения для перемеренной, завернутого в BehaviorRelay от jakewharton
*
* Промежуточные значения не отправляются к наблюдателю, что позволяет выполнять ресурсоемкие операции
*
*  @AndreyKuzubov
* */
private val testState = BehaviorRelay.createDefault<String>("1")
private val rxDis: CompositeDisposable = CompositeDisposable()


fun main(arg: Array<String>) {
    val obs = testState
            .buffer(1, TimeUnit.SECONDS)
            .filter { it -> it.size > 0 }
            .map { it -> it[it.size - 1] }


    println("change group 1 ")
    testState.accept("1")
    testState.accept("2")
    testState.accept("3")


    rxDis.addAll(
            obs.subscribe({
                println("textStateNew: ${it}")
                Thread.sleep(10)
            }, {
                println("err: ${it}")

            }, {
                println("onComplete: ")

            })
    )


    Thread.sleep(10000)

    println("change group 2 ")
    testState.accept("4")
    testState.accept("5")
    testState.accept("6")

    while (true)
        Thread.sleep(1000)

    rxDis.dispose()


}

