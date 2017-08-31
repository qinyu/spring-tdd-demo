package info.qinyu.price;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository repository;

    @Autowired
    TestEntityManager entityManager;


    @Test
    public void should_query_book_by_name() throws Exception {
        entityManager.persistAndFlush(new Book("Kotlin实战", 8900));

        Book book = repository.findByName("Kotlin实战");

        assertThat(book, is(notNullValue()));
        assertThat(book.getPriceInCent(), is(8900));
    }
}