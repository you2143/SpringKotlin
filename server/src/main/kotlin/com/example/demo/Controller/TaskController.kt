package com.example.demo.controller

import com.example.demo.model.Task
import com.example.demo.repository.ITaskRepository
import com.example.demo.dto.TaskCreateForm
import com.example.demo.dto.TaskUpdateForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.http.HttpStatus

@Controller
@RequestMapping("tasks")
class TaskController(private val taskRepository: ITaskRepository) {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleException(): String = "tasks/not_found"

    @GetMapping("")
    fun index(model: Model): String {
        val tasks = taskRepository.findAll()
        model.addAttribute("tasks", tasks)
        return "tasks/index"
    }

    @GetMapping("new")
    fun new(form: TaskCreateForm): String {
        return "tasks/new"
    }

    @PostMapping("")
    fun create(@Validated form: TaskCreateForm,
               bindingResult: BindingResult): String {
        if (bindingResult.hasErrors())
            return "tasks/new"
        
        val content = requireNotNull(form.content)
        taskRepository.create(content)
        return "redirect:/tasks"
    }

    @GetMapping("{id}/edit")
    fun edit(@PathVariable("id") id: Long,
               form: TaskUpdateForm): String {
        var task = taskRepository.findById(id) ?: throw Exception()
        
        form.content = task.content
        form.done = task.done
        return "tasks/edit"
    }

    @PatchMapping("{id}")
    fun update(@PathVariable("id") id: Long,
               @Validated form: TaskUpdateForm,
               bindingResult: BindingResult): String {
        if (bindingResult.hasErrors())
            return "tasks/new"
        var task = taskRepository.findById(id) ?: throw Exception()
        val newTask = task.copy(content = requireNotNull(form.content), done = form.done)
        taskRepository.update(newTask)
        form.done = task.done
        return "redirect:/tasks"
    }
}