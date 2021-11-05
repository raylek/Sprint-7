package ru.sber.orm.repository

import org.springframework.data.repository.CrudRepository
import ru.sber.orm.entity.Student

interface CustomRepository : CrudRepository<Student, Long> {
    fun findByName(name: String): List<Student>
}