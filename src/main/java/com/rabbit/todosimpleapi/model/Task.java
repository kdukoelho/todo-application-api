package com.rabbit.todosimpleapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.rabbit.todosimpleapi.model.Task.*;

@Entity @Table(name = TABLE_NAME)
@Data @NoArgsConstructor
public class Task {
    public static final String TABLE_NAME = "tb_task";
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    private String id;

    @NotBlank
    @Column(name = "name", length = 30)
    @Size(min = 1, max = 30)
    private String name;

    @Column(name = "description", length = 100)
    @Size(max = 100)
    private String description;

    @ManyToMany(mappedBy = "taskList")
    private List<User> usersList = new ArrayList<>();
}