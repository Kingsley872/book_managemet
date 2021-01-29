package com.book.management.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.book.management.domain.Author;
import com.book.management.domain.Book;
import com.book.management.repository.AuthorRepository;



@Component
public class LoadDataInDB implements CommandLineRunner {
	
	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public void run(String... args) throws Exception {
		// 3 authors, each author has two books
		
		// only runs when program starts 
		if(authorRepository.count() > 0) {
			return;
		}
		
		// create author and books
		Author author1 = new Author("Mark", "Twain");
		
		Book book1 =  new Book("Life on the Mississippi", "Biography", 1883);
		book1.setSummary("Life of the Mississippi is a memoir by Mark Twain detailing "
				+ "his days as a steamboat pilot on the Mississippi River before and after "
				+ "the American Civil War.");
		
		Book book2 = new Book("The Adventures of Tom Sawyer", "Biography", 1876);
		book2.setSummary("The Adventures of Tom Sawyer by Mark Twain is an 1876 novel "
				+ "about a young boy growing up along the Mississippi River, The Story "
				+ "is set in the fictional twon of St. Petersburg, inspired by Hannibal, "
				+ "Missouri, where Twain lived");
		
		// save data to DB
		author1.addBook(book1);
		author1.addBook(book2);
		authorRepository.save(author1);
		
		
		Author author2 = new Author("William", "Shakespeare");
		Book book3 = new Book("Hamlet", "Drama", 1599);
		book3.setSummary("The tragedy of Hanlet, Prince of Denmark, or more...");
		Book book4 = new Book("King Star", "Tragedy", 1606);
		book4.setSummary("King lear is a tragedy by William Shakespeare, believed to have ...");
		
		author2.addBook(book3);
		author2.addBook(book4);
		authorRepository.save(author2);
		
		
		Author author3 = new Author("Leo", "Tolstoy");
		Book book5 = new Book("War and Peace", "Novel", 1869);
		book5.setSummary("Epic in scale, War and Peace delineates in graphic...");
		Book book6 = new Book("Anna Karenia", "Novel", 1877);
		book6.setSummary("Anna Karenina tells of teh doomed love affaire between...");
		
		author3.addBook(book5);
		author3.addBook(book6);
		authorRepository.save(author3);
		
	}

}
