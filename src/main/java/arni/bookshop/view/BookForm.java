package arni.bookshop.view;

import arni.bookshop.models.Book;
import arni.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class BookForm extends JFrame {
    BookService bookService;
    private JPanel panel;
    private JTable bookTable;
    private JTextField idBook;
    private JTextField textBook;
    private JTextField textAuthor;
    private JTextField textPrice;
    private JTextField textStock;
    private JButton saveButton;
    private JButton editButton;
    private JButton deleteButton;
    private DefaultTableModel booksTableModel;

    @Autowired
    public BookForm(BookService bookService){
        this.bookService = bookService;
        initForm();
        saveButton.addActionListener(e -> saveBook());
        bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadSelectedBook();
            }
        });
        editButton.addActionListener(e -> editBook());
        deleteButton.addActionListener(e -> deleteBook());
    }

    private void deleteBook(){
        if(this.idBook.getText().isEmpty()){
            showMessage("Select a row");
        }else{
            if(idBook.getText().isEmpty()){
                showMessage("the id of the book is required");
                textBook.requestFocusInWindow();
                return;
            }
            //Llenamos el objeto libro a actualizar
            int idBookRecovered = Integer.parseInt(idBook.getText());
            var bookNameRecovered = textBook.getText();
            var bookAuthorRecovered = textAuthor.getText();
            var bookPriceRecovered = Double.parseDouble(textPrice.getText());
            var bookStockRecovered = Integer.parseInt(textStock.getText());
            var book = new Book(idBookRecovered, bookNameRecovered, bookAuthorRecovered, bookPriceRecovered, bookStockRecovered);
            this.bookService.deleteBook(book);
            showMessage("Deleted Book successfully");
            clearForm();
            listBooks();
        }
    }
    private void editBook(){
        if(this.idBook.getText().isEmpty()){
            showMessage("Select a row");
        }else{
            if(textBook.getText().isEmpty()){
                showMessage("the name of the book is required");
                textBook.requestFocusInWindow();
                return;
            }
            //Llenamos el objeto libro a actualizar
            int idBookRecovered = Integer.parseInt(idBook.getText());
            var bookNameRecovered = textBook.getText();
            var bookAuthorRecovered = textAuthor.getText();
            var bookPriceRecovered = Double.parseDouble(textPrice.getText());
            var bookStockRecovered = Integer.parseInt(textStock.getText());
            var book = new Book(idBookRecovered, bookNameRecovered, bookAuthorRecovered, bookPriceRecovered, bookStockRecovered);
            this.bookService.saveBook(book);
            showMessage("Updated Book successfully");
            clearForm();
            listBooks();
        }
    }

    private void loadSelectedBook(){
        //Los indices de las columnas inician en 0
        var row = bookTable.getSelectedRow();
        if(row !=-1){
            String idBookRecovered = bookTable.getModel().getValueAt(row, 0).toString();
            idBook.setText(idBookRecovered);
            String textBookRecovered = bookTable.getModel().getValueAt(row, 1).toString();
            textBook.setText(textBookRecovered);
            String textAuthorRecovered = bookTable.getModel().getValueAt(row, 2).toString();
            textAuthor.setText(textAuthorRecovered);
            String textPriceRecovered = bookTable.getModel().getValueAt(row, 3).toString();
            textPrice.setText(textPriceRecovered);
            String textStockRecovered = bookTable.getModel().getValueAt(row, 4).toString();
            textStock.setText(textStockRecovered);

        }
    }

    public void initForm(){
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900,700);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - getWidth()/2);
        int y = (screenSize.height - getHeight()/2);
        setLocation(x, y);
    }

    private void saveBook(){
        if(textBook.getText().isEmpty()){
            showMessage("Field name text is required");
            textBook.requestFocusInWindow();
        }
        var bookName = textBook.getText();
        var author = textAuthor.getText();
        var price = Double.parseDouble(textPrice.getText());
        var stock = Integer.parseInt(textStock.getText());
        //Create Book
        var book = new Book(null, bookName, author, price, stock);
        //book.setName(bookName);
        //book.setAuthor(author);
        //book.setPrice(price);
        //book.setStock(stock);
        this.bookService.saveBook(book);
        showMessage("Saved Book successfully");
        clearForm();
        listBooks();
    }

    private void showMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    private void clearForm(){
        textBook.setText("");
        textAuthor.setText("");
        textPrice.setText("");
        textStock.setText("");
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //Crear idBOok oculto
        idBook = new JTextField("");
        idBook.setVisible(false);
        this.booksTableModel = new DefaultTableModel(0, 5){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        String[] headers = {"Id", "Book", "Author", "Price", "Stock"};
        this.booksTableModel.setColumnIdentifiers(headers);
        //Instance JTable
        this.bookTable = new JTable(booksTableModel);
        //Evitar que se seleccinen varios libros
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listBooks();
    }

    private void listBooks(){
        //Clear Table
        booksTableModel.setRowCount(0);
        //Get books
        var books = bookService.listBooks();
        books.forEach((book)->{
            Object[] rowBook = {
                    book.getIdBook(),
                    book.getName(),
                    book.getAuthor(),
                    book.getPrice(),
                    book.getStock()
            };
            this.booksTableModel.addRow(rowBook);
        });

    }
}
