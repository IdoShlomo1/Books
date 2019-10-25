package databases.testing.infra;

import java.io.IOException;

import databases.testing.infra.RequestType;
import databases.testing.infra.RequestesTypeFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BooksApiRequestsFactory {
	public final String booksUrl = "http://localhost:8080/api/books";
	public final String booksUrlById = "http://localhost:8080/api/books/id";
	public OkHttpClient client;

	public BooksApiRequestsFactory() {
		client = new OkHttpClient();
	}

	public Response doRequest(String url, RequestType requestType, RequestBody ...body) throws IOException {
		Request request = RequestesTypeFactory.getRequest(url, requestType, body); 
		Response response = null;
		try {
			response = client.newCall(request).execute();
			return response;
		} catch (IOException e) {
			String message = "Fail to make request [verbe=%s, url=%s]";
			throw new IOException(String.format(message, url, requestType));
		}
	}
	
	public Response getBookListResponse() throws IOException {
		Response response = doRequest(booksUrl, RequestType.GET);
		return response;
	}
	
	public Response getBooksById(int id) throws IOException {
		String url = booksUrlById + "/" + id;
		Response response = doRequest(url, RequestType.GET);
		return response;
	}

	public Response deleteBooksByIdRespone(int id) throws IOException {
		String url = booksUrlById + "/" + id;
		Response response = doRequest(url, RequestType.DELETE);
		return response;
	}
	
	public Response deleteAllBooksResponse() throws IOException {
		String url = booksUrlById + "/admin-tools/clean-test-db";
		Response response = doRequest(url, RequestType.DELETE);
		return response;
	}
	
	
	public Response updateBookPriceResponse(int id, RequestBody body) throws IOException {
		String url = booksUrl + "/patch/id/" + id;
		Response response = doRequest(url, RequestType.PATCH, body);
		return response;
	}
	
	public Response bookFullUpdateResponse(int id, RequestBody body) throws IOException {
		String url = booksUrlById + "/" + id;
		Response response = doRequest(url, RequestType.PUT, body);
		return response;
	}
	
	public Response getAddBookResponse(RequestBody body) throws IOException {
		Response response = doRequest(booksUrl, RequestType.POST, body);
		return response;
	}

	public Response getBookContainsNameResponse(String name) throws IOException {
		String url = booksUrl + "/contains-name/" + name;
		Response response = doRequest(url, RequestType.GET);
		return response;
	}
	
	public Response getBooksPriceLessThanResponse(double price) throws IOException {
		String url = booksUrl + "/less-equal-price/" + price;
		Response response = doRequest(url, RequestType.GET);
		return response;
	}
	
	public Response getBooksByPriceBetweenResponse(double minPrice, double maxPrice) throws IOException {
		String url = booksUrl + "/between-price/" + minPrice + "/" + maxPrice;
		Response response = doRequest(url, RequestType.GET);
		return response;
	}
	
}
