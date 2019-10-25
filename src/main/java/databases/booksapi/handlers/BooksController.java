package databases.booksapi.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import databases.booksapi.beans.Book;

@RestController
@RequestMapping("api/books")
public class BooksController {
	@Autowired
	public BooksDao booksDao;
	
	@GetMapping
	public ResponseEntity<?> getAllBooks(){
		try {
			ArrayList<Book> books = booksDao.getAllBooks();
			return new ResponseEntity<>(books, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("id/{id}")
	public ResponseEntity<?> getBookById(@PathVariable("id") int id){
		try {
			Book book = booksDao.getBookById(id);
			if (book != null)
				return new ResponseEntity<>(book, HttpStatus.OK);	
			
			return new ResponseEntity<>("Book id:" + id + " Not Exists", HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> addBook(@RequestBody Book book) {
		try {
			Book bookAdded = booksDao.addBook(book);
			return new ResponseEntity<>(bookAdded, HttpStatus.CREATED);
		} 
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("id/{id}")
	public ResponseEntity<?> updateBook(@PathVariable("id")int id ,@RequestBody Book book) {
		try {
			book.setId(id);
			Book bookUpdated = booksDao.updateBook(book);
			if(bookUpdated != null)
				return new ResponseEntity<>(book, HttpStatus.OK);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} 
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("id/{id}")
	public ResponseEntity<?> updateBook(@PathVariable("id")int id) {
		try {
			boolean bookDeleted = booksDao.deleteBook(id);
			if(bookDeleted)
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} 
		
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("between-price/{from}/{to}")
	public ResponseEntity<?> findBookByPriceBetween(@PathVariable("from")double from, @PathVariable("to")double to) {
		try {
			ArrayList<Book> books = booksDao.findBooksByPriceBetween(from, to);
			return new ResponseEntity<>(books, HttpStatus.OK);
		}	
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("less-equal-price/{from}")
	public ResponseEntity<?>findBooksByPriceLessThanEqual(@PathVariable("from") double from){
		try {
			ArrayList<Book> books = booksDao.findBooksByPriceLessThenEqual(from);
			return new ResponseEntity<>(books, HttpStatus.OK);
		}	
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("contains-name/{name}")
	public ResponseEntity<?> findBooksContainName(@PathVariable("name")String name){
		try {
			ArrayList<Book> books = booksDao.findBooksContainName(name);
			return new ResponseEntity<>(books, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping("patch/id/{id}")
	public ResponseEntity<?> updateBookprice(@PathVariable("id")int id, @RequestBody Book bookObject){
		try {
			Book book = booksDao.updateBookPrice(id, bookObject.getPrice());
			if(book != null)
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
			return new ResponseEntity<>("Price Update Failed", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		}
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	@GetMapping("count")
	public ResponseEntity<?>getBooksBooksCount(){
		try {
			int count = booksDao.countBooks();
			return new ResponseEntity<>(count, HttpStatus.OK);
		}	
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/admin-tools/push-books")
	public ResponseEntity<?>addTestBooksToDB(@RequestBody List<Book> booksToAdd){
		try {
			booksDao.insertManyBooks(booksToAdd);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/admin-tools/clean-test-db")
	public ResponseEntity<?>cleanDB(){
		try {
			booksDao.deleteAllBooks();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
