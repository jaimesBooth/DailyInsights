package com.example.dailyinsight;

/**
 * Describes quote objects.
 * @author Luke
 *
 */
public class Quote {
	
	//Attributes for the quote class
	private String message; //The quote message
	private Category type; //The quote type
	
	/**
	 * Constructor for the quote class
	 * @param insight the inspirational message of the quote object
	 * @param type the category type of the quote object
	 */
	public Quote (String message, Category type) {
		this.message = message;
		this.type = type;
	}
	
	/*
	 * Accessor method for getting the object message
	 * @return the object message
	 */
	public String getMessage() {
		return message;
	}
	
	/*
	 * Mutator method for setting the object message
	 * @param message the new message 
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/*
	 * Accessor method for getting the object category
	 * @return the category of the object
	 */
	public Category getCategory() {
		return type;
	}
	
	/*
	 * Mutator method for setting the object category
	 * @param the new category of the object
	 */
	public void setCategory(Category type) {
		this.type = type;
	}
}

