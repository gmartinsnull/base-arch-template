package com.gmartinsdev.base_arch_template.data.remote

/**
 * custom throwable class for successful response from empty body API call
 */
class UserNotFoundThrowable(message: String) : Throwable(message)