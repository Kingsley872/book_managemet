package com.book.management.util;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class AuthorUtil {
	
	public static final String NOT_FOUND_MESSAGE = "I have not found the specified author. Please try agian.";
	
	// list of messages for incoming requests (list_authors intent)
	public static final List<String> listOfAuthorsMessages = new ArrayList<>();
	
	// list of messages as response (list_authors intent)
	public static final List<String> listOfAuthorSelectionMessages = new ArrayList<>();
	
	static {
		listOfAuthorsMessages.add("Here is the list of authors: ");
		listOfAuthorsMessages.add("Sure, here is the list: ");
		listOfAuthorsMessages.add("I found the following authors: ");
		
		listOfAuthorSelectionMessages.add("Which one would you like to select?");
		listOfAuthorSelectionMessages.add("Please choose one of them to continue.");
		listOfAuthorSelectionMessages.add("I can provide list of books if you choose an author.");
	}
	
	// create methods for extracting a random message used by Google Assistant 
	public static String getRandomListOfAuthorsMessage() {
		Integer listOfAuthorsValue = new Random().nextInt(listOfAuthorsMessages.size());
		return listOfAuthorsMessages.get(listOfAuthorsValue);
	}
	
	public static String getRandomAuthorSelectionMessage() {
		Integer listOfAuthorsSelectionValue = new Random().nextInt(listOfAuthorSelectionMessages.size());
		return listOfAuthorSelectionMessages.get(listOfAuthorsSelectionValue);
	}
}
