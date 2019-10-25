package databases.testing.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import databases.booksapi.beans.Book;

public class BookFileHandler {
	private static final String patternString = "(.*?)\\s+by\\s(.*?)\\s\\(([\\d,]+)\\)";

	private static Pattern pattern = Pattern.compile(patternString);

	public static String[] parseTextLineToBookRecord(String line) {
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			return new String[] { matcher.group(1), matcher.group(2), matcher.group(3).replace(",", ""), };
		}
		return null;
	}

	public static List<Book> getBookListFromFile(String filePath) {
		List<Book> books = new ArrayList<Book>();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)))){
			String line;
			while ((line = bufferedReader.readLine()) != null) {

				String[] bookRecord = parseTextLineToBookRecord(line);

				if (bookRecord != null) {
					Book book = TestingUtils.initBookFromRecord(bookRecord);//new Book();
					books.add(book);
				}
			}
		}
		 
		catch (Exception e) {
			System.out.println("Fail To Read Book Records : Error : " + e);
		}		
		return books;
	}	
}
