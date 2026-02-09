package michalr.tasks.util

fun Boolean.ifTrueThrow(exception: () -> Throwable) {
    if (this) {
        throw exception()
    }
}

fun Boolean.ifFalseThrow(exception: () -> Throwable) {
    if (!this) {
        throw exception()
    }
}

fun Boolean.ifTrue(sideEffect: () -> Unit) = sideEffect()

fun Boolean.ifFalse(sideEffect: () -> Unit) = sideEffect()