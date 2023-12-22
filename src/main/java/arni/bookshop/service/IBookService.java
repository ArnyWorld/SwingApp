package arni.bookshop.service;

import arni.bookshop.models.Book;

import java.util.List;

public interface IBookService {
    public List<Book> listBooks();
    public Book listBookById(Integer id);
    public void saveBook(Book book);
    public void deleteBook(Book book);
}
