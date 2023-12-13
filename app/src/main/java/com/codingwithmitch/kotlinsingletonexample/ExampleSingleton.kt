package com.codingwithmitch.kotlinsingletonexample

import com.codingwithmitch.kotlinsingletonexample.model.User

object ExampleSingleton {

    val singletonUser: User by lazy{
        User("iftyfr6@gmail.com", "ifty", "some_image_url.png")
    }
}



