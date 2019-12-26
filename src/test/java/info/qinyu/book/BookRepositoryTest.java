package info.qinyu.book;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
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

        assertThat(book).isNotNull();
        assertThat(book.getPriceInCent()).isEqualTo(8900);
    }
}