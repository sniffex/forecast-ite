package kh.edu.rupp.ite.iteforecast

import android.app.Application

class MyApplication : Application(){

    companion object{
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}