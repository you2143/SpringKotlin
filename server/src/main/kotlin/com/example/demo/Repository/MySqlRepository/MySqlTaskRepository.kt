package com.example.demo.repository.mySqlRepository

import com.example.demo.model.Task
import com.example.demo.repository.ITaskRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@Repository
class MySqlTaskRepository(private val jdbcTemplate: JdbcTemplate) : ITaskRepository {
    private val rowMapper = RowMapper<Task> { rs, _ ->
        Task(rs.getLong("id"),rs.getString("content"),rs.getBoolean("done"))
    }

    override fun create(content: String): Task {
        jdbcTemplate.update("INSERT INTO task(content) VALUES(?)" , content)
        val id = jdbcTemplate.queryForObject("SELECT last_insert_id()", Long::class.java)
        return Task(id !!, content, false)
    }

    override fun update(task: Task) {
        jdbcTemplate.update("UPDATE task SET content = ?, done = ? WHERE id = ?",
        task.content,
        task.done,
        task.id)
    }

    override fun findAll(): List<Task> = jdbcTemplate.query("SELECT id, content, done FROM task", rowMapper)

    override fun findById(id: Long): Task? = jdbcTemplate.query("SELECT id, content, done FROM task WHERE id = ?", rowMapper, id).firstOrNull()
}