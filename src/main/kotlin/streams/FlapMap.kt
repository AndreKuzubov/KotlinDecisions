package streams

import java.util.stream.Stream

/*
*
* @AndreyKuzubov 2018
* */

fun main(arg: Array<String>) {
    val s = Stream.of(2, 4)
    val s11 = s.flatMap {
        Stream.of(it - 1, it + 1)
    }

    for (it in s11) {
        println("it = ${it}")
    }

}