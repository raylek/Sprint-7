package com.example.demo

import com.example.demo.persistance.Entity
import com.example.demo.persistance.EntityRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class EntityRepositoryTest {

    @Autowired
    private lateinit var entityRepository: EntityRepository

    @Test
    fun findById() {
        val savedEntity = entityRepository.save(
            Entity(
                name = "Иван",
                surname = "Иванов"
            )
        )

        val foundEntity = entityRepository.findById(savedEntity.id!!)

        assertTrue { foundEntity.get() == savedEntity }

    }
}