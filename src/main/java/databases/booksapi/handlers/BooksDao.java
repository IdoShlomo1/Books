package databases.booksapi.handlers;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import databases.booksapi.beans.Book;


@Repository
public class BooksDao {
	@Autowired
	private BooksRepository booksRepository;
	
	public ArrayList<Book> getAllBooks(){
		return (ArrayList<Book>)booksRepository.findAll();
	}
	
	public Book getBookById(int id) {
		Optional <Book> optionalBook = booksRepository.findById(id);
		if (optionalBook.isPresent()) {
			return optionalBook.get();
		}
		return null;
	}
	
	public Book addBook(Book book) {
		booksRepository.save(book);
		return book;
	}
	
	public Book updateBook(Book book) {
		if(booksRepository.existsById(book.getId())) {
			booksRepository.save(book);
			return book;
		}
		return null;
	}

	public boolean deleteBook(int id) {
		if(booksRepository.existsById(id)) {
			booksRepository.deleteById(id);
			return true;
			
		}
		return false;
	}

	
	public ArrayList<Book> findBooksByPriceBetween(double from, double to){
		return booksRepository.findBooksByPriceBetween(from, to);
	}

	public ArrayList<Book> findBooksByPriceLessThenEqual(double from){
		return booksRepository.findBooksByPriceLessThanEqual(from);
	}
	
	public ArrayList<Book> findBooksContainName(String name){
		return booksRepository.findBooksContainName(name);
	}
	
	public Book updateBookPrice(int id, double price) {
		
		Book book = getBookById(id);
		if (book != null) {
			book.setPrice(price);
			booksRepository.save(book);
			return book;
		}
		return null;
	}
	
	public int countBooks() {
		return (int) booksRepository.count();
	}

	public void deleteAllBooks() {
		booksRepository.deleteAll(getAllBooks());
	}

	public void insertManyBooks(List<Book> books) {
		booksRepository.saveAll(books);
	}	
}
