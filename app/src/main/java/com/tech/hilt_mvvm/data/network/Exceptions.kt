package com.tech.hilt_mvvm.data.network

import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)
//class TimeOutException(message: String) : SocketTimeoutException(message)
