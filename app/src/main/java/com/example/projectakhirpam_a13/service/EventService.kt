package com.example.projectakhirpam_a13.service

import com.example.projectakhirpam_a13.model.AllEventResponse
import com.example.projectakhirpam_a13.model.Event
import com.example.projectakhirpam_a13.model.EventDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("event/")
    suspend fun getAllEvent(): AllEventResponse

    @GET("event/{id_event}")
    suspend fun getEventById(@Path("id_event") idEvent: Int): EventDetailResponse

    @POST("event/store")
    suspend fun insertEvent(@Body event: Event): Response<Void>

    @PUT("event/{id_event}")
    suspend fun updateEvent(@Path("id_event") idEvent: Int, @Body event: Event): Response<Void>

    @DELETE("event/{id_event}")
    suspend fun deleteEvent(@Path("id_event") idEvent: Int): Response<Void>
}