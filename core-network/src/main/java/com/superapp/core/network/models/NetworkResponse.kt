package com.superapp.core.network.models

data class NetworkResponse<T>(val isSuccess: Boolean, val data: T, val error: String)