package com.example.TestProject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@RestController
class TestProjectApplication

fun main(args: Array<String>) {
	runApplication<TestProjectApplication>(*args)
}
