package ru.sber.orm.entity

import javax.persistence.*

@Entity
class Course(
    @Id
    @GeneratedValue
    var id: Long,

    @Column
    var name: String,

    @OneToMany(
        cascade = [CascadeType.ALL],
        mappedBy = "course",
        fetch = FetchType.EAGER
    )
    var students: MutableList<Student> = mutableListOf()
)