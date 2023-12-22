package arni.bookshop.service;

import arni.bookshop.models.Book;
import arni.bookshop.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {
    @Autowired
    private BookRepository repositoryBook;
    @Override
    public List<Book> listBooks() {
        return this.repositoryBook.findAll();
    }
    @Override
    public Book listBookById(Integer id) {
        Book book = this.repositoryBook.findById(id).orElse(null);
        return book;
    }
    @Override
    public void saveBook(Book book) {
        this.repositoryBook.save(book);
    }
    @Override
    public void deleteBook(Book book) {
        this.repositoryBook.delete(book);
    }
}
