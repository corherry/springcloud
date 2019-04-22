package com.oocl.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.demo.TodoListApplication;
import com.oocl.demo.domain.TodoItem;
import com.oocl.demo.service.TodoListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Dylan Wei
 * @date 2019-03-09 23:21
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TodoListApplication.class)
@ComponentScan(basePackages = "com")
public class TodoListControllerTest {
    @MockBean
    private TodoListService todoListService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private static final String url = "/todoItems";

    @Test
    public void should_return_201_after_item_added_successfully() throws Exception {
        // given
        TodoItem item = new TodoItem("do homework", "未完成");
        when(this.todoListService.addTodoItem(any())).thenReturn(item);
        // when & then
        mockMvc.perform(
                post(url).content(mapper.writeValueAsString(item)).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().string(mapper.writeValueAsString(item)));
    }

    @Test
    public void should_return_all_items() throws Exception {
        // given
        TodoItem item1 = new TodoItem("do homework", "未完成");
        TodoItem item2 = new TodoItem("do homework", "未完成");
        TodoItem item3 = new TodoItem("do homework", "已完成");
        when(this.todoListService.getItems(any())).thenReturn(Arrays.asList(item1, item2, item3));
        // when & then
        mockMvc.perform(
                get(url + "?filter=全部"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(Arrays.asList(item1, item2, item3))));
    }

    @Test
    public void should_return_status_code_with_204_when_update_successfully() throws Exception {
        // given
        TodoItem item = new TodoItem("do homework", "未完成");
        // when & then
        mockMvc.perform(
                put(url).content(mapper.writeValueAsString(item)).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_return_status_code_with_204_when_update_partially_successfully() throws Exception {
        // given
        TodoItem item1 = new TodoItem("do homework", "未完成");
        TodoItem item2 = new TodoItem("do homework", "未完成");
        when(this.todoListService.getItemById(any())).thenReturn(item2);
        // when & then
        mockMvc.perform(
                patch(url).content(mapper.writeValueAsString(item1)).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_return_204_status_code_when_delete_item_successfully() throws Exception {
        // given
        TodoItem item1 = new TodoItem("do homework", "未完成");
        TodoItem item2 = new TodoItem("do homework", "未完成");
        when(this.todoListService.getItemById(any())).thenReturn(item2);
        // when & then
        mockMvc.perform(
                delete(url).content(mapper.writeValueAsString(item1)).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }


}