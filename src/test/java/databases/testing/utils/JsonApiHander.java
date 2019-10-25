package databases.testing.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import databases.booksapi.beans.Book;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class JsonApiHander {
	private ObjectMapper objectMapper;

	public JsonApiHander() {
		this.objectMapper = new ObjectMapper();
	}

	public Error parseJsonErrorStringToErrorObj(String body)
			throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(body, Error.class);
	}

	public List<Book> parseJsonSringToBookList(String body)
			throws JsonParseException, JsonMappingException, IOException {
		return Arrays.asList(objectMapper.readValue(body, Book[].class));
	}

	public Book parseJsonSringToBookObject(String body) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(body, Book.class);
	}

	public RequestBody parseBookToJsonRequestBody(Book book) throws JsonProcessingException {
		MediaType mediaType = MediaType.parse("application/json");
		String json = objectMapper.writeValueAsString(book);

		RequestBody body = RequestBody.create(mediaType, json);
		return body;
	}

	public RequestBody parseBookListToJsonRequestBody(List<Book>books) throws JsonProcessingException {
		MediaType mediaType = MediaType.parse("application/json");
		String json = objectMapper.writeValueAsString(books);

		RequestBody body = RequestBody.create(mediaType, json);
		return body;
	}
}
