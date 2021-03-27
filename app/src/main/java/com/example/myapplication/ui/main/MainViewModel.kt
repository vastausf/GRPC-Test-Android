package com.example.myapplication.ui.main

import GreeterClient
import HelloRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.wire.GrpcClient
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Protocol

class MainViewModel : ViewModel() {
    private var greeterClient = GrpcClient
        .Builder()
        .client(OkHttpClient.Builder().protocols(listOf(Protocol.H2_PRIOR_KNOWLEDGE)).build())
        .baseUrl("http://192.168.1.126:50051/")
        .build()
        .create(GreeterClient::class)

    fun sendHello(
        name: String,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            val response = greeterClient.SayHello().execute(HelloRequest(name))

            onSuccess(response.message)
        }
    }
}
