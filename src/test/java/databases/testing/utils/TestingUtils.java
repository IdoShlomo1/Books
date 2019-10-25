package databases.testing.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import databases.booksapi.beans.Book;

public class TestingUtils {
	public static Double getDouble(int precision) {
		return BigDecimal.valueOf((100 * Math.random())).setScale(precision, RoundingMode.HALF_UP).doubleValue();
	}
	
	public static Book initBookFromRecord(String[] record) {
		Book book = new Book();
		book.setName(record[0]);
		book.setAuthor(record[1]);
		book.setSeals(Integer.parseInt(record[2]));
		book.setPrice(getDouble(3));
		return book;
	}
}
