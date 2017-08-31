package info.qinyu.price;


public class BookPriceCalculator {
    BookPrice calculatePrice(Book book) {
        String price = String.format("%.02f", book.getPriceInCent() / 100f);
        return new BookPrice(book.getName(), price);
    }
}