package ru.sber.orm.dao

import org.hibernate.SessionFactory
import ru.sber.orm.entity.Student

class StudentDAOImpl(
    private val sessionFactory: SessionFactory
) {

    fun save(student: Student) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.save(student)
            session.transaction.commit()
        }
    }

    fun find(id: Long): Student? {
        val result: Student?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.get(Student::class.java, id)
            session.transaction.commit()
        }
        return result
    }

    fun findAll(): List<Student>{
        val result: List<Student>
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.createQuery("from Student")
                .list() as List<Student>
        }
        return result
    }

    fun delete(id: Long) {
        val student: Student?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            student = session.get(Student::class.java, id)
            session.delete(student)
            session.transaction.commit()
        }
        return
    }
}