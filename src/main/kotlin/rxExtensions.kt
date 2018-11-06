import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.delayEmits(period: Long, unit: TimeUnit, ignoreOffset: Int = 0): Observable<T> = Observable.zip(
        this,
        Observable.interval(period, unit)
                .mergeWith(Observable.fromArray(*Array(ignoreOffset, { 0L }))),
        BiFunction<T, Long, T> { it, _ ->
            it
        })


fun <T> Observable<T>.delayEmitsFrom(period: Long, unit: TimeUnit): Observable<T> = Observable.zip(
        this,
        Observable.interval(period, unit).mergeWith(Observable.just(1L)),
        BiFunction<T, Long, T> { it, _ ->
            it
        })
