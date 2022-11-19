package com.movie.review.exceptions;

public class ReviewDataException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public ReviewDataException(String s) {
		super(s);
		this.message = s;
	}

}
