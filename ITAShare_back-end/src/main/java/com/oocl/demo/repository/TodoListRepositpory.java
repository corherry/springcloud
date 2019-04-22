package com.oocl.demo.repository;

import com.oocl.demo.domain.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Howells
 * @DATE 03/08/2019
 */
@Repository
public interface TodoListRepositpory extends JpaRepository<TodoItem,Long> {
}
