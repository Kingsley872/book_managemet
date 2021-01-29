package com.book.management.service;

import java.util.List;
import java.util.StringJoiner;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.management.domain.Author;
import com.book.management.domain.Book;
import com.book.management.repository.AuthorRepository;
import com.book.management.repository.BookRepository;
import com.book.management.util.AuthorUtil;
import com.book.management.util.BookUtil;
import com.book.management.util.IntentUtil;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;


@Service
@Transactional
public class BookService extends DialogflowApp {
	
	private Logger logger = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@ForIntent(IntentUtil.LIST_BOOKS_BY_AUTHOR)
	public ActionResponse findAllByAuthor(ActionRequest request) {
		logger.info("Executing Intent - " + IntentUtil.LIST_BOOKS_BY_AUTHOR);
		
		StringBuilder response = new StringBuilder();
		
		// retrieve given name and last name from the request
		String givenName = request.getParameter("given-name").toString();
		String lastName = request.getParameter("last-name").toString();
		
		// retrieve author from DB
		Author author = authorRepository.findByGivenNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(givenName, lastName);
		
		if(author != null) {
			// get list of books mapped to the authors
			//List<Book> booksList = bookRepository.findAll();
			List<Book> booksList = bookRepository.findByAuthor(author);
			// start to build the response that will be sent back to user
			response.append(BookUtil.getRandomListOfBooksMessages());
			
			// use StringJoiner to add comma delimited list of books 
			StringJoiner sj = new StringJoiner(", ");
			booksList.forEach(book -> sj.add(book.toString()));
			
			// add list of books to the response 
			response.append(sj);
			response.append(". ");
			
			// add a random message - ask user to select a book 
			response.append(BookUtil.getRandomListOfBooksSelectionMessages());
			
		} else {
			// author not found
			response.append(AuthorUtil.NOT_FOUND_MESSAGE);
		}
		
		// create response and send it back to user (as ActionResponse)
		ResponseBuilder responseBuilder = getResponseBuilder(request).add(response.toString());
		ActionResponse actionResponse = responseBuilder.build();
		
		return actionResponse;
	}
	
	
	@ForIntent(IntentUtil.LIST_BOOKS_BY_GENRE)
	public ActionResponse findAllByGenre(ActionRequest request) {
		logger.info("Executing Intent - " + IntentUtil.LIST_BOOKS_BY_GENRE);
		
		StringBuilder response = new StringBuilder();
		
		// retrieve genre from the request
		String genre = request.getParameter("Genre").toString();
		
		// find the books genre (in DB) base on the title
		List<Book> bookList = bookRepository.findByGenreContainingIgnoreCase(genre);
		
		if(bookList != null && bookList.size() > 0) {
			// start to popular the response that will be sent to user 
			response = new StringBuilder(BookUtil.getRandomListOfBooksMessages());
			
			// use StringJoiner to add comma delimited list of books 
			StringJoiner sj = new StringJoiner(", ");
			bookList.forEach(book -> sj.add(book.toString()));
			
			response.append(sj);
			response.append(". ");
			
			response.append(BookUtil.getRandomListOfBooksSelectionMessages());
		} else {
			response.append(BookUtil.BOOK_NOT_FOUND_MESSAGE);
		}
		
		ResponseBuilder responseBuilder = getResponseBuilder(request).add(response.toString());
		ActionResponse actionResponse = responseBuilder.build();
		return actionResponse;
	}
	
	
	@ForIntent(IntentUtil.GET_BOOK_DETAILS_BY_AUTHOR)
	public ActionResponse getBookDetailsByAuthor(ActionRequest request) {
		logger.info("Executing Intent - " + IntentUtil.GET_BOOK_DETAILS_BY_AUTHOR);
		
		return getBookDetails(request);
	}
	
	
	@ForIntent(IntentUtil.GET_BOOK_DETAILS_GENRE)
	public ActionResponse getBookDetailsByGenre(ActionRequest request) {
		logger.info("Executing Intent - " + IntentUtil.GET_BOOK_DETAILS_GENRE);
		
		return getBookDetails(request);
	}
	
	
	public ActionResponse getBookDetails(ActionRequest request) {
		StringBuilder response = new StringBuilder();
		ResponseBuilder responseBuilder = null;
		
		// retrieve book-title from the request
		String bookTitle = request.getParameter("any").toString();
		
		// find the book (in DB) base on the title
		Book book = bookRepository.findByTitleContainingIgnoreCase(bookTitle);
		
		if(book != null) {
			// start to popular the response that will be sent to user 
			// will contain details
			response.append(BookUtil.getRandomListOfBooksDetailsMessages());
			response.append("\"");
			response.append(book.getTitle());
			response.append("\"");
			response.append(" was publicshed in ");
			response.append(book.getPublicationYear());
			response.append(" and can be categorized as a ");
			response.append(book.getGenre());
			response.append(". Here is a short summary. ");
			response.append(book.getSummary());
			
			responseBuilder = getResponseBuilder(request).add(response.toString()).endConversation();
		} else {
			response.append(BookUtil.BOOKS_NOT_FOUND_MESSAGE);
			responseBuilder = getResponseBuilder(request).add(response.toString());
		}
		
		ActionResponse actionResponse = responseBuilder.build();
		return actionResponse;
	}
}









