package ru.kolesnikov.desiccisionfarm.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="name")
    private String name;

    @Column(name ="description")
    private String description;

    @Column(name ="status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_login")
    private User user;

    public Task(String description, String status, User user) {
        this.description = description;
        this.status = status;
        this.user = user;
    }

    public Task(String name, String description, String status, User user) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.user = user;
    }
}