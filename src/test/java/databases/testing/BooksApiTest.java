package databases.testing;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;

import databases.booksapi.beans.Book;
import databases.testing.infra.AbstractBooksApiTest;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BooksApiTest extends AbstractBooksApiTest {

	@Test(dataProvider = "AddBooksDataProvider", priority = 1) // data-provider
	public void testAddBook(Book bookTestObj) throws IOException {
		RequestBody body = jsonApiHander.parseBookToJsonRequestBody(bookTestObj);
		Response response = booksApiRequestsFactory.getAddBookResponse(body);
		Assert.assertEquals(response.code(), 201, "Fail - Status Code : " + response.code() );
		Book book = jsonApiHander.parseJsonSringToBookObject(response.body().string());
		System.out.println("Adding : " + book);
		Assert.assertTrue(book instanceof Book, "Fail To Parse Book Object");
	}

	@Test(dataProvider = "UpdateBooksDataProvider", priority = 2)
	public void testPutBook(Book book) throws IOException {
		List<Book> booksCollection = getAllBooks();
		int index = (int)(Math.random() * booksCollection.size());
		Book bookTested = booksCollection.get(index);
		System.out.println("Update :" + bookTested);

		RequestBody json = jsonApiHander.parseBookToJsonRequestBody(book);
		
		Response response = booksApiRequestsFactory.bookFullUpdateResponse(bookTested.getId(), json);
		Book bookUpdated = jsonApiHander.parseJsonSringToBookObject(response.body().string());

		System.out.println("Code2 : " + response.code());

		System.out.println(bookUpdated.getAuthor() + " : " + bookUpdated.getId());
		System.out.println(bookTested.getAuthor() + " : " + bookTested.getId());
	}

	
	
	@Test(priority = 3)
	public void testGetAllbooks() throws IOException {
		List<Book> books = getAllBooks();
		
		if(books.size() > 0)
			Assert.assertTrue(books.get(0) instanceof Book, "Fail to parse books list");	
		else
			System.out.println("Found 0 Books");
	}

	@Test(priority = 4)
	public void testGetBooksById() throws IOException {
		List<Book> books = getAllBooks();
		Assert.assertTrue(isBooksParsed(books), "Fail to parse books list");

		Random random = new Random();
		Book testedBook = books.get(random.nextInt(books.size()));

		System.out.println("Get Book By id : Data -> " + testedBook.getId());
		Response response = booksApiRequestsFactory.getBooksById(testedBook.getId());
		
		String body = response.body().string();		
		Book actualBook = jsonApiHander.parseJsonSringToBookObject(body);
		
		Assert.assertEquals(testedBook.getId(), actualBook.getId(), "Fail : Ids mismatch");
	}

	@Test(priority = 5)
	public void testDeleteBooksById() throws IOException, JSONException {
		Random random = new Random();
		List<Book> books = getAllBooks();
		
		Assert.assertTrue(isBooksParsed(books), 
				"getAllBooks[1] Fail to parse books list");

		Book book = books.get(random.nextInt(books.size()));
		System.out.println("Delete : id=" + book.getId());
		Response response = booksApiRequestsFactory.deleteBooksByIdRespone(book.getId());
		System.out.println("Code :" + response.code());
		Assert.assertEquals(204, response.code());

		Response bookByIdResponse = booksApiRequestsFactory.getBooksById(book.getId());	
		String bookByIdBody = bookByIdResponse.body().string();
		boolean expected = bookByIdBody.toLowerCase().contains("not exists");
		
		Assert.assertTrue(expected, "Fail : " + bookByIdBody);		
	}

	@Test(priority = 6)
	public void testUpdateBookPriceById() throws IOException {
		List<Book> booksCollection = getAllBooks();
		Book book = booksCollection.get(0);
		book.setPrice(1.1);

		System.out.println("Petch Method , data=" + book);	
		
		RequestBody body = jsonApiHander.parseBookToJsonRequestBody(book);
		Response response = booksApiRequestsFactory.updateBookPriceResponse(book.getId(), body);
	
		System.out.println(response.code());
		Assert.assertEquals(response.code(), 204, "Fail With Code " + response.code());
	}
	
	@Test(priority = 7)
	public void testGetBookByAuthorNameContains() throws IOException {
		List<Book> books = getAllBooks();
		Book book = books.get(0);
		String nameToSearch = book.getName().substring(2);

		Response response = booksApiRequestsFactory
								.getBookContainsNameResponse(nameToSearch);
		
		Assert.assertEquals(response.code(), 200, "Fail With Code " + response.code());
		
		books = jsonApiHander
					.parseJsonSringToBookList(response.body().string());
		
		System.out.println("Searching " + book);
		Assert.assertTrue(books.size() > 0, "0 Records Match");
		Assert.assertTrue(books.get(0).getName().contains(nameToSearch), "Search Term Not Found");	
		books.forEach(System.out::println);
	}
	
	@Test(priority = 8)
	public void testGetBooksByPriceLessThanEquals() throws IOException {
		double price = 20.0;
		System.out.println("Searching Price Less Than Equals To : " + price);

		Response response = booksApiRequestsFactory
								.getBooksPriceLessThanResponse(price);
	
		Assert.assertEquals(response.code(), 200, "Fail With Code " + response.code());
		
		List<Book> books = jsonApiHander
								.parseJsonSringToBookList(response.body().string());
			
		Assert.assertTrue(books.size() > 0, "0 Records Match");
		books.forEach(System.out::println);
	}
	
	@Test(priority = 9)
	public void testGetBooksByPriceBetween() throws IOException {
		double minPrice = 10;
		double maxPrice = 40;		
		
		System.out.println("Searching Price Between : [" + minPrice+", " + maxPrice+"]");

		Response response = booksApiRequestsFactory
								.getBooksByPriceBetweenResponse(minPrice, maxPrice);

		Assert.assertEquals(response.code(), 200, "Fail With Code " + response.code());

		List<Book> books = jsonApiHander
								.parseJsonSringToBookList(response.body().string());
		
		Assert.assertTrue(books.size() > 0, "0 Records Match");
		books.forEach(System.out::println);
	}

//	@Test(dataProvider = "UpdateBooksDataProvider") // data-provider
//	public void getBooksProvider(Book book) throws IOException {
//		System.out.println(book);
//	}
//	
//	@Test
//	public void testing() throws IOException {
//		System.out.println("In Test");
//	}
}
