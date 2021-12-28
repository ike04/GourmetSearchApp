package com.google.codelab.gourmetsearchapp.model

data class Failure(
    val error: Throwable,
    val message: Int,
    val retry: () -> Unit
) {
    fun resolve() {
        this.retry.invoke()
    }
}

class NoLocationPermissionException : Throwable()
