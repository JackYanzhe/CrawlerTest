package pojo;



public class Book {
	 private String bookName;  
     private String constrall;  
     private Double score;  
     private String content;  
     private String country;  
     private String author;
     private String press;
     private String pressdate;
     private String price;
     
	public Book() {
		
	}
	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getConstrall() {
		return constrall;
	}
	public void setConstrall(String constrall) {
		this.constrall = constrall;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getPressdate() {
		return pressdate;
	}
	public void setPressdate(String pressdate) {
		this.pressdate = pressdate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public Book(String bookName, String constrall, Double score,
			String content, String country, String author, String press,
			String pressdate, String price) {
		super();
		this.bookName = bookName;
		this.constrall = constrall;
		this.score = score;
		this.content = content;
		this.country = country;
		this.author = author;
		this.press = press;
		this.pressdate = pressdate;
		this.price = price;
	}

	
     
}
