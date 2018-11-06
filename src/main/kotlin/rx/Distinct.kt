package rx

import io.reactivex.Observable
import java.util.*

class Item(
        val key: Int,
        val id: String = UUID.randomUUID().toString()
)


/**
 * Distinct удаляет дубликаты
 *
 * @AndreyKuzubov
 */
fun main(arg: Array<String>) {
    Observable.just<Item>(
            Item(key = 1),
            Item(key = 1),
            Item(key = 1),
            Item(key = 12),
            Item(key = 1),
            Item(key = 1),
            Item(key = 2),
            Item(key = 1)
    )
            .distinct { it.key }
            .subscribe({
                println(" item: ${it.key} ${it.id}")
            })


}