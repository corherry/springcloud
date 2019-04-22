package com.oocl.demo.domain;


import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "todo_item")
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String type;


    public TodoItem(String content, String type) {
        this.content = content;
        this.type = type;
    }

    public TodoItem(String content) {
        this.content = content;
    }

    public TodoItem(long id, String content, String type) {
        this.id = id;
        this.content = content;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TodoItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
