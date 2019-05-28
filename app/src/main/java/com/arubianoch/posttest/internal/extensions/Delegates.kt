package com.arubianoch.posttest.internal.extensions

import kotlinx.coroutines.*

/**
 * @author Andres Rubiano Del Chiaro
 */
fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}