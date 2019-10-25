package databases.booksapi.handlers;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import databases.booksapi.beans.Book;

public interface BooksRepository extends CrudRepository<Book, Integer>{
	ArrayList<Book> findBooksByPriceBetween(double from, double to);
	ArrayList<Book> findBooksByPriceLessThanEqual(double from);
	// Native SQL:
	@Query(value="select * from Books b where b.name like %:name%", nativeQuery=true)
	ArrayList<Book> findBooksContainName(String name);	
	
}