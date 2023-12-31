package com.rabbit.todosimpleapi.controller;

import com.rabbit.todosimpleapi.dto.TaskRequestDTO;
import com.rabbit.todosimpleapi.dto.TaskResponseDTO;
import com.rabbit.todosimpleapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> findAllByUserId(@PathVariable String userId){
        try{
            List<TaskResponseDTO> taskResponseDTOList = this.taskService.findAllByUserId(userId);
            return ResponseEntity.ok().body(taskResponseDTOList);
        } catch(RuntimeException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findAll() {
        try {
            List<TaskResponseDTO> taskResponseDTOList = this.taskService.findAll();
            return ResponseEntity.ok().body(taskResponseDTOList);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable String id) {
        try {
            TaskResponseDTO responseDTO = this.taskService.findById(id);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody TaskRequestDTO taskRequestDTO) {
        try {
            TaskResponseDTO taskResponseDTO = this.taskService.create(taskRequestDTO);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(taskResponseDTO.id()).toUri();
            return ResponseEntity.created(uri).build();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody TaskRequestDTO taskRequestDTO, @PathVariable String id) {
        try {
            this.taskService.update(taskRequestDTO, id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        try{
            this.taskService.delete(id);
            return ResponseEntity.noContent().build();
        } catch(RuntimeException ex){
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }
}
