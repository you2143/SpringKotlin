package com.example.demo.repository

import com.example.demo.model.Task

interface ITaskRepository {

    fun create(content: String): Task

    fun update(task: Task)

    fun findAll(): List<Task>

    fun findById(id: Long): Task?
}

