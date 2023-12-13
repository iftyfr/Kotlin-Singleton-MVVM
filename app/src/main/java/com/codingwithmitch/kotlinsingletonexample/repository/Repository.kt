package com.codingwithmitch.kotlinsingletonexample.repository

import androidx.lifecycle.LiveData
import com.codingwithmitch.kotlinsingletonexample.api.ApiService
import com.codingwithmitch.kotlinsingletonexample.api.MyRetrofitBuilder
import com.codingwithmitch.kotlinsingletonexample.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object Repository {

    var job: CompletableJob? = null

    fun getUser(userId: String): LiveData<User> {
        val job = Job()
        return object : LiveData<User>() {
            override fun onActive() {
                super.onActive()
                CoroutineScope(Dispatchers.IO + job).launch {
                    try {
                        val user = MyRetrofitBuilder.apiService.getUser(userId)
                        withContext(Dispatchers.Main) {
                            value = user
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        job.complete()
                    }
                }
            }

            override fun onInactive() {
                super.onInactive()
                // Cancel the job when the LiveData is inactive to avoid unnecessary work
                job.cancel()
            }
        }
    }


    fun cancelJobs(){
        job?.cancel()
    }

}
















