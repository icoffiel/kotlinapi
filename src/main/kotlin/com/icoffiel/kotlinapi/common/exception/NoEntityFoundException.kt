package com.icoffiel.kotlinapi.common.exception

class NoEntityFoundException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)

    constructor(id: Long, entityName: String) : this("$entityName was not found for id $id")
}