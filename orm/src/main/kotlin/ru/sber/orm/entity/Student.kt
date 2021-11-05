package ru.sber.orm.entity

import javax.persistence.*

@Entity
class Student(
    @Id
    @GeneratedValue
    var id: Long,

    @Column
    var name: String,

    @ManyToOne(
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "id")
    var course: Course,
)