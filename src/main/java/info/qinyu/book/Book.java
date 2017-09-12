package info.qinyu.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Book {

    @Id
    private long id;

    final private String name;
    final private int priceInCent;

}
