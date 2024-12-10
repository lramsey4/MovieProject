package Movie;
import java.util.*;

/*
 * Lauren Ramsey
 * CSC3465 Software Design
 * Richard Dutton and Shannon Duvall
 * ExtraCredit MovieDatabase
 * December 2-12th, 2024
 */

public class MovieInfo {
	private String title;
	private String director;
	private ArrayList<String> cast;
	private int year;
	private int grossRank;
	private double boxOfficeEarnings;
	
	/**
	 * A second constructor to print GrossRank specific information
	 * @param title Title of the movie
	 * @param year Year the movie was released
	 * @param grossRank The rank of the movie
	 * @param boxOfficeEarnings The amount of money earned from the movie
	 */
	public MovieInfo(String title, int year, int grossRank, double boxOfficeEarnings) {
		this.title = title;
		this.year = year;
		this.grossRank = grossRank;
		this.boxOfficeEarnings = boxOfficeEarnings;
	}
	
	/**
	 * Original constructor, sets up the information to be printed
	 * @param title Title of the movie
	 * @param director The main director
	 * @param cast A list of up to 5 cast members
	 */
	public MovieInfo(String title, String director, ArrayList<String> cast) {
		this.title = title;
		this.director = director;
		this.cast = new ArrayList<>(cast);
	}
	
	// GETTERS //
	
	public ArrayList<String> getCastMembers() {
		return cast;
	}
	
	public String getDirector() {
		return director;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getGrossRank() {
		return grossRank;
	}
	
	public double getBoxOfficeEarnings() {
		return boxOfficeEarnings;
	}
}