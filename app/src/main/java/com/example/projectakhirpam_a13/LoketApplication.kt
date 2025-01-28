package com.example.projectakhirpam_a13

import android.app.Application
import com.example.projectakhirpam_a13.depedencies.AppContainer
import com.example.projectakhirpam_a13.depedencies.LoketContainer

class LoketApplication: Application() {
        lateinit var container: AppContainer

        override fun onCreate() {
            super.onCreate()
            container = LoketContainer()
        }
}