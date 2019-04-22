package com.oocl.demo.service;

import com.oocl.demo.domain.TodoItem;
import com.oocl.demo.repository.TodoListRepositpory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Example;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dylan Wei
 * @date 2019-03-09 23:06
 */
public class TodoListServiceTest {
    private TodoListRepositpory repositpory;

    private TodoListService todoListService;

    @Before
    public void beforeEach(){
        this.repositpory = mock(TodoListRepositpory.class);
        this.todoListService = new TodoListService(this.repositpory);
    }

    @Test
    public void should_added_item_successfully(){
        //given
        TodoItem item = new TodoItem("do homework", "未完成");
        //when
        this.todoListService.addTodoItem(item);
        //then
        verify(this.repositpory).save(item);
    }

    @Test
    public void should_execute_findAll_function(){
        //given
        TodoItem item = new TodoItem();
        item.setType("未完成");
        //when
        this.todoListService.getItems(item);
        //then
        verify(this.repositpory).findAll(Example.of(item));
    }

    @Test
    public void should_execute_delete_function(){
        TodoItem item = new TodoItem();
        item.setId(1L);
        this.todoListService.deleteItem(item);
        verify(this.repositpory).deleteById(1L);
    }

    @Test
    public void should_returnItem_by_id(){
        TodoItem item = new TodoItem();
        when(this.repositpory.findById(any())).thenReturn(Optional.of(item));
        TodoItem resultItem = this.todoListService.getItemById(1L);
        assertThat(resultItem, is(item));
    }

    @Test
    public void should_update_item_successfully(){
        TodoItem item = new TodoItem();
        this.todoListService.updateTodoItems(item);
        verify(this.repositpory).save(item);
    }

}