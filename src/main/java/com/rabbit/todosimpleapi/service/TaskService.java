package com.rabbit.todosimpleapi.service;

import com.rabbit.todosimpleapi.dto.TaskRequestDTO;
import com.rabbit.todosimpleapi.dto.TaskResponseDTO;
import com.rabbit.todosimpleapi.model.Task;
import com.rabbit.todosimpleapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskResponseDTO> findAllByUserId(String userId){
        try{
            ArrayList<TaskResponseDTO> taskResponseDTOList = new ArrayList<>();
            this.taskRepository.findAllByUserId(userId).forEach(task -> taskResponseDTOList.add(new TaskResponseDTO(task)));
            return taskResponseDTOList;
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<TaskResponseDTO> findAll(){
        try{
            ArrayList<TaskResponseDTO> taskResponseDTOList = new ArrayList<>();
            this.taskRepository.findAll().forEach(task -> taskResponseDTOList.add(new TaskResponseDTO(task)));
            return taskResponseDTOList;
        } catch(RuntimeException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public TaskResponseDTO findById(String id){
        try{
            Optional<Task> task = taskRepository.findById(id);
            return new TaskResponseDTO(
                    task.orElseThrow(() -> new RuntimeException(
                            String.format("Task id %s not found.", id))));
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Transactional
    public TaskResponseDTO create(TaskRequestDTO taskRequestDTO){
        try{
            Task task = new Task(taskRequestDTO);
            taskRepository.save(task);
            return new TaskResponseDTO(task);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Transactional
    public TaskResponseDTO update(TaskRequestDTO taskRequestDTO, String id){
        try{
            Task task = new Task(findById(id));
            task.setName(taskRequestDTO.name());
            task.setDescription(taskRequestDTO.description());
            this.taskRepository.save(task);
            return new TaskResponseDTO(task);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public String delete(String id){
        try{
            taskRepository.deleteTaskUserRelationshipById(id);
            taskRepository.deleteById(id);
            return "operation completed";
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return "operation failed";
    }
}
