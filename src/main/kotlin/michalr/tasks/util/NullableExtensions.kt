package michalr.tasks.util

fun Any?.ifNullThrow(exception: () -> Throwable) {
    if(this == null) {
        throw exception()
    }
}

fun Any?.ifNotNullThrow(exception: () -> Throwable) {
    if(this != null) {
        throw exception()
    }
}

fun Any?.ifNull(sideEffect: () -> Unit) {
    if(this == null) {
        sideEffect()
    }
}

fun Any?.ifNotNull(sideEffect: () -> Unit) {
    if(this != null) {
        sideEffect()
    }
}