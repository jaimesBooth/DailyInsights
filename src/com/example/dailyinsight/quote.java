package com.example.dailyinsight;

public class quote {
	
	//Attributes for the quote class
	private String message; //The quote message
	private category type; //The quote type
	
	/**
	 * Constructor for the quote class
	 * @param insight the inspirational message of the quote object
	 * @param type the category type of the quote object
	 */
	public quote (String message, category type) {
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
	public category getCategory() {
		return type;
	}
	
	/*
	 * Mutator method for setting the object category
	 * @param the new category of the object
	 */
	public void setCategory(category type) {
		this.type = type;
	}
}

