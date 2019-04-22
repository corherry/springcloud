package com.oocl.demo.repository;
import com.oocl.demo.domain.TodoItem;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Dylan Wei
 * @date 2019-03-09 19:31
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoListRepositporyTest {
    @Autowired
    private TestEntityManager manager;

    @Autowired
    private TodoListRepositpory repositpory;

    @After
    public void reset(){
        this.manager.clear();
    }

    @Test
    public void should_total_size_increase_1_after_1_item_added(){
        //given
        TodoItem item = new TodoItem("do homework", "未完成");
        int oldSize = this.repositpory.findAll().size();
        //when
        this.repositpory.save(item);
        //then
        int newSize = this.repositpory.findAll().size();
        assertThat(newSize, is(oldSize + 1));
    }

    @Test
    public void should_total_size_increase_3_after_item_list_with_3_items_inserted(){
        //given
        TodoItem item1 = new TodoItem("do homework", "未完成");
        TodoItem item2 = new TodoItem("do homework", "未完成");
        TodoItem item3 = new TodoItem("do homework", "未完成");
        List<TodoItem> items = Arrays.asList(item1, item2, item3);
        int oldSize = this.repositpory.findAll().size();
        //when
        this.repositpory.saveAll(items);
        //then
        int newSize = this.repositpory.findAll().size();
        assertThat(newSize, is(oldSize + items.size()));
    }

    @Test
    public void should_return_all_items(){
        TodoItem item1 = new TodoItem("do homework", "未完成");
        TodoItem item2 = new TodoItem("do homework", "未完成");
        TodoItem item3 = new TodoItem("do homework", "已完成");
        this.manager.persistAndFlush(item1);
        this.manager.persistAndFlush(item2);
        this.manager.persistAndFlush(item3);

        List<TodoItem> items = this.repositpory.findAll(Example.of(new TodoItem()));
        assertThat(items, equalTo(Arrays.asList(item1, item2, item3)));
    }

    @Test
    public void should_return_all_unfinished_items(){
        TodoItem item1 = new TodoItem("do homework", "未完成");
        TodoItem item2 = new TodoItem("do homework", "未完成");
        TodoItem item3 = new TodoItem("do homework", "已完成");
        this.manager.persistAndFlush(item1);
        this.manager.persistAndFlush(item2);
        this.manager.persistAndFlush(item3);

        TodoItem queryItem = new TodoItem();
        queryItem.setType("未完成");
        List<TodoItem> items = this.repositpory.findAll(Example.of(queryItem));
        assertThat(items, equalTo(Arrays.asList(item1, item2)));
    }

    @Test
    public void should_delete_item_successfully(){
        TodoItem item1 = new TodoItem( "do homework", "未完成");
        TodoItem item = this.manager.persistAndFlush(item1);
        long oldSize = this.repositpory.count();
        this.repositpory.deleteById(item.getId());
        assertThat(this.repositpory.count(), is(oldSize - 1));
    }

}