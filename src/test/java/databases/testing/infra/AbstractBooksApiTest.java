package databases.testing.infra;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import databases.booksapi.beans.Book;
import databases.testing.utils.BookFileHandler;
import databases.testing.utils.JsonApiHander;
import okhttp3.Response;

public abstract class AbstractBooksApiTest {
	public final String booksUrl = "http://localhost:8080/api/books";
	protected BooksApiRequestsFactory booksApiRequestsFactory;
	protected JsonApiHander jsonApiHander;
	private static final String booksFilepath = "C:\\Users\\Ido\\Desktop\\author_list.txt";
	private static final String booksFilepathTest = "C:\\Users\\Ido\\Desktop\\author_list_test_set.txt";

	@BeforeClass
	public void f() throws IOException {
		booksApiRequestsFactory = new BooksApiRequestsFactory();
		booksApiRequestsFactory.deleteAllBooksResponse();

	}
	
	public List<Book> getAllBooks() throws IOException {
		Response response = booksApiRequestsFactory.doRequest(booksUrl, RequestType.GET);
		String info = "Response Code " + response.code();

		Assert.assertEquals(200, response.code(), "Fail : " + info);

		String body = response.body().string();
		List<Book> books = jsonApiHander.parseJsonSringToBookList(body);

		return books;
	}

	public boolean isBooksParsed(List<?> books) {
		return ((books instanceof List<?>) && (books.get(0) instanceof Book));
	}

	@BeforeMethod
	public void setup() {
		jsonApiHander = new JsonApiHander();
	}

	public static Object[][] getBooksCollectionByType(int type) {
		List<Book> books = null;
		
		if (type==1)
			books = BookFileHandler.getBookListFromFile(booksFilepath);
		
		else if (type==2)
			books = BookFileHandler.getBookListFromFile(booksFilepathTest);
		
		if(books != null) {
			Object[][] matrix = new Object[books.size()][1];
				
			for (int i=0; i<books.size(); i++) {
				matrix[i][0] = books.get(i);
			}
			
			return matrix;
		}
		
		return null;
	}
	
	@DataProvider(name = "AddBooksDataProvider")
	public Object[][] getTestBooks() {
		Object[][] matrix = getBooksCollectionByType(2);
		return matrix;
	}

	@DataProvider(name = "UpdateBooksDataProvider")
	public Object[][] getUpdateBooks() {
		Object[][] matrix = getBooksCollectionByType(1);
		return matrix;
	}
}
