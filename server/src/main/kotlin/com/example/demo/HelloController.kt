package com.example.demo.controller
 
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class HelloController {
    @GetMapping("/")
    fun Hello(model: Model): String {
        model.addAttribute("message", "Hello Test")
        return "Hello"
    }
}