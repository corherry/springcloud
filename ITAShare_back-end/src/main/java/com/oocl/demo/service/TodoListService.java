package com.oocl.demo.service;

import com.oocl.demo.domain.TodoItem;
import com.oocl.demo.repository.TodoListRepositpory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class TodoListService {
    private TodoListRepositpory repositpory;

    @Autowired
    public TodoListService(TodoListRepositpory repositpory){
        this.repositpory = repositpory;
    }

    public TodoItem addTodoItem(TodoItem item){
        item.setType("未完成");
        return this.repositpory.save(item);
    }

    public  TodoItem updateTodoItems(TodoItem item){
        return this.repositpory.save(item);
    }

    public List<TodoItem> getItems(TodoItem item){
        return repositpory.findAll(Example.of(item));
    }

    public TodoItem getItemById(Long id){
        return repositpory.findById(id).get();
    }

    public  boolean deleteItem( TodoItem item){
        repositpory.deleteById(Long.parseLong(item.getId().toString()));
        return true;
    }

}
