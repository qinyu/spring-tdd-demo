package info.qinyu.price;

import org.springframework.data.repository.CrudRepository;


public interface BookRepository extends CrudRepository<Book, Long> {
    Book findOne(String name);
}
