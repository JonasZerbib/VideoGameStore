//Or Cohen 307852681
//Jonas Zerbib 340941301
package modle;

public class Game {

	// Game fields
	private int id;
	private double price;
	private String name;
	private String summary;
	private String developer;
	private String publisher;
	private String trailer;
	private String picture;
	private String releaseDate;

	// Constructors
	public Game() {
	}

	public Game(int id, double price, String name, String summary, String developer, String publisher, String trailer,
			String picture, String releaseDate) {
		super();
		this.id = id;
		this.price = price;
		this.name = name;
		this.summary = summary;
		this.developer = developer;
		this.publisher = publisher;
		this.trailer = trailer;
		this.picture = picture;
		this.releaseDate = releaseDate;

	}

	// Copy constructor
	public Game(Game copiedGame) {
		super();
		this.id = copiedGame.id;
		this.price = copiedGame.price;
		this.name = copiedGame.name;
		this.summary = copiedGame.summary;
		this.developer = copiedGame.developer;
		this.publisher = copiedGame.publisher;
		this.trailer = copiedGame.trailer;
		this.picture = copiedGame.picture;
		this.releaseDate = copiedGame.releaseDate;

	}

	// Get and Set functions
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

}
