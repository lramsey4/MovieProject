package Movie;
import java.util.*;

/*
 * Lauren Ramsey
 * CSC3465 Software Design
 * Richard Dutton and Shannon Duvall
 * ExtraCredit MovieDatabase
 * December 2-12th, 2024
 */

public class Movie {
	private int ratingRank;
	private int grossRank;
	private String title;
	private int year;
	private double rating;
	private double boxOfficeEarnings;
	private String director;
	private ArrayList<String> cast;
	
	public Movie() {
		this.cast = new ArrayList<>();
		this.director = "";
		this.ratingRank = 0;
		this.grossRank = 0;
		this.title = "";
		this.year = 0;
		this.rating = 0.0;
		this.boxOfficeEarnings = 0.0;
	}
	
	// GETTERS //
	
	public ArrayList<String> getCastMembers() {
		return cast;
	}
	
	public String getDirector() {
		return director;
	}
	
	public int getRatingRank() {
		return ratingRank;
	}
	
	public int getGrossRank() {
		return grossRank;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getYear() {
		return year;
	}
	
	public double getRating() {
		return rating;
	}
	
	public double getBoxOfficeEarnings() {
		return boxOfficeEarnings;
	}

	// SETTERS //
	
	public void setRatingRank(int rank) {
		this.ratingRank = rank;
	}
	
	public void setGrossRank(int rank) {
		this.grossRank = rank;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDirector(String director) {
		this.director = director;
		
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public void setBoxOfficeEarnings(double earnings) {
		this.boxOfficeEarnings = earnings;
	}
	
	public void setCastMembers(ArrayList<String> cast) {
		this.cast = cast;
	}
	
}