package com.encore.vo;
//db table에 대한 정보를 담고 있는 클래스..
//book table의 하나의 row를 인스턴스화 시킨 클래스
//vo, domain, dto 라 부른다.
public class Book {
	private String isbn;
	private String title;
	private String writer; //연습을 위해 다르게 지정해봄. 컬럼명과 동일하게 가는 것이 가장 최적
	private String publisher;
	private int price;
	
	
	public Book(String isbn, String title, String writer, String publisher, int price) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.writer = writer;
		this.publisher = publisher;
		this.price = price;
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getWriter() {
		return writer;
	}


	public void setWriter(String writer) {
		this.writer = writer;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", writer=" + writer + ", publisher=" + publisher
				+ ", price=" + price + "]";
	}
	
	
	
	
}
