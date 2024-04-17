package com.nurlan.core.network.di

data class NetworkResponse<T>(val isSuccess: Boolean, val data: T, val error: String)
