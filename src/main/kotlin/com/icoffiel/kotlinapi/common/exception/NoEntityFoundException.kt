package com.icoffiel.kotlinapi.common.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class NoEntityFoundException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)

    constructor(id: Long, entityName: String) : this("$entityName was not found for id $id")
}