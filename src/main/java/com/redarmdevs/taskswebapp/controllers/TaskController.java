package com.redarmdevs.taskswebapp.controllers;

import com.redarmdevs.taskswebapp.exceptions.TaskException;
import com.redarmdevs.taskswebapp.exceptions.UserException;
import com.redarmdevs.taskswebapp.models.Task;
import com.redarmdevs.taskswebapp.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping(path = "api/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping(path = "{user}")
    public Set<Task> getTasks(@PathVariable(name = "user") String user){
        try {
            return taskService.getTasks(user);
        } catch (UserException e) {
            return Collections.emptySet();
        }
    }

    @PostMapping(path = "{user}")
    public ResponseEntity<String> addTask(@PathVariable(name = "user") String username, @RequestBody Task task){
        String message;
        try {
            taskService.addTask(username, task);
            message = "task '" + task.getName() + "' added successfully!";
            return ResponseEntity.ok(message);
        }
        catch (UserException e){
            message = "task failed to be added: " + e.getMessage();
            return ResponseEntity.badRequest().body(message);
        }
    }

    @DeleteMapping(path = {"{user}"})
    public ResponseEntity<String> deleteTask(@PathVariable(name="user") String username, @RequestParam(name = "taskID") Long taskID){
        String message;
        try{
            taskService.deleteTask(username, taskID);
            message = "task deleted successfully";
            return ResponseEntity.ok(message);
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "{user}/deleteTasks")
    public ResponseEntity<String> deleteAllUsersTasks(@PathVariable(name="user") String username){
        try{
            taskService.deleteAllTasksByUser(username);
            String message = "All tasks by " + username + " deleted successfully";
            return ResponseEntity.ok(message);
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
