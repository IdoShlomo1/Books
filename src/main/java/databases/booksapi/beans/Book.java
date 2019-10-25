package databases.booksapi.beans;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;


@Entity
@Table(name="books")
public class Book {
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + ", seals=" + seals + ", price=" + price
				+ "]";
	}
	private int id;
	private String name;
	private String author;
	private int seals;
	private double price;
	
	@Id
	@GeneratedValue
	public int getId() { return id; }	
	public void setId(int id) { this.id = id; }

	@Column(nullable=false)
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	@Column(nullable=false)
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	
	@Column(nullable=false)
	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }
	
	@Column(nullable=false)
	public int getSeals() { return seals; }
	public void setSeals(int seals) { this.seals = seals; }

}
