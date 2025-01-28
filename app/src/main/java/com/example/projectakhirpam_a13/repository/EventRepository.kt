package com.example.projectakhirpam_a13.repository

import com.example.projectakhirpam_a13.model.AllEventResponse
import com.example.projectakhirpam_a13.model.Event
import com.example.projectakhirpam_a13.service.EventService
import java.io.IOException

interface EventRepository {
    suspend fun getEvents(): AllEventResponse
    suspend fun insertEvent(event: Event)
    suspend fun updateEvent(idEvent: Int, event: Event)
    suspend fun deleteEvent(idEvent: Int)
    suspend fun getEventById(idEvent: Int): Event
}

class NetworkEventRepository(
    private val eventApiService: EventService
) : EventRepository {
    override suspend fun getEvents(): AllEventResponse =
        eventApiService.getAllEvent()

    override suspend fun insertEvent(event: Event) {
        eventApiService.insertEvent(event)
    }

    override suspend fun updateEvent(idEvent: Int, event: Event) {
        eventApiService.updateEvent(idEvent, event)
    }

    override suspend fun deleteEvent(idEvent: Int) {
        try {
            val response = eventApiService.deleteEvent(idEvent)
            if (!response.isSuccessful) {
                throw IOException(
                    "Gagal Hapus Event. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getEventById(idEvent: Int): Event {
        return eventApiService.getEventById(idEvent).data
    }
}