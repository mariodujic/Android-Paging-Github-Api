package com.groundzero.github.data.response

class StatusResponse private constructor(var status: Status, var throwable: Throwable?) {

    companion object {
        fun loading(): StatusResponse = StatusResponse(Status.LOADING, null)
        fun success(): StatusResponse = StatusResponse(Status.SUCCESS, null)
        fun error(throwable: Throwable): StatusResponse = StatusResponse(Status.ERROR, throwable)
    }
}