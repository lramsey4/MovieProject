package Movie;
import java.io.*;
import java.util.*;

/*
 * Lauren Ramsey
 * CSC3465 Software Design
 * Richard Dutton and Shannon Duvall
 * ExtraCredit MovieDatabase
 * December 2-12th, 2024
 */

public class MovieDatabase {
	
	private ArrayList<Movie> movies = new ArrayList<>();
	
	/**
	 * Main file data loader, is used to find the information common throughout all files
	 * @param filename The name of the file to be loaded
	 * @param parser The file parser
	 */
	private void loadMoviesData(String filename, MovieDataParser parser) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			
			line = reader.readLine();
			
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\t");
				Movie movie = new Movie();
				parser.parseMovieData(movie, parts);
				movies.add(movie);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Error reading movies file: " + filename);
		}
	}
	
	/**
	 * specific loader for rated movies
	 * @param filename Name of the file
	 */
	public void loadRatedMovies(String filename) {
		loadMoviesData(filename, (movie, parts) -> {
			movie.setRatingRank(Integer.parseInt(parts[0]));
			movie.setTitle(parts[1]);
			movie.setYear(Integer.parseInt(parts[2]));
			movie.setRating(Double.parseDouble(parts[3]));
		});
	}
	
	/**
	 * specific loader for gross movies
	 * @param filename Name of the file
	 */
	public void loadGrossMovies(String filename) {
		loadMoviesData(filename, (movie, parts) -> {
			movie.setGrossRank(Integer.parseInt(parts[0]));
			movie.setTitle(parts[1]);
			movie.setYear(Integer.parseInt(parts[2]));
			movie.setBoxOfficeEarnings(Double.parseDouble(parts[3]));
		});
	}
	
	/**
	 * specific loader for movies with cast
	 * @param filename Name of the file
	 */
	public void loadCastMovies(String filename) {
		loadMoviesData(filename, (movie, parts) -> {
			movie.setTitle(parts[1]);
			movie.setYear(Integer.parseInt(parts[2]));
			movie.setDirector(parts[3]);
			
			ArrayList<String> castMembers = new ArrayList<>();
			for (int i = 4; i < Math.min(parts.length, 9); i++) {
				castMembers.add(parts[i]);
			}
			movie.setCastMembers(castMembers);
		});
	}
	
	/**
	 * Answers "Query Total Earnings for a Year", goes through every movie in that
	 * year and creates a sum.
	 * @param year The year of choice
	 * @return the total box office earnings made in that year
	 */
	public double getTotalEarningsForYear(int year) {
		double totalEarnings = 0.0;
		for (Movie movie : movies) {
			if (movie.getYear() == year && movie.getBoxOfficeEarnings() > 0) {
				totalEarnings += movie.getBoxOfficeEarnings();
			}
		}
		return totalEarnings;
	}
	
	/**
	 * Answers "List All Directors", Creates a list of every director alphabetically.
	 * @return a (now sorted) ArrayList of directors
	 */
	public ArrayList<String> getSortedDirectors() {
		HashSet<String> directors = new HashSet<>();
		for (Movie movie : movies) {
			if (movie.getDirector() != null && !movie.getDirector().isEmpty()) {
				directors.add(movie.getDirector());
			}
		}
		ArrayList<String> sortedDirectors = new ArrayList<>(directors);
		Collections.sort(sortedDirectors);
		
		return sortedDirectors;
	}
	
	/**
	 * Answers "List Top Directors", takes each director and creates a HashMap that correlates the
	 * name with the amount of appearances. The directors are then sorted by the amount of occurrences,
	 * and a separate list is made off of the sorted to create the TopDirectors, the length matching
	 * the number of directors asked to be shown.
	 * @param number How many Top Directors are to be shown
	 * @return ArrayList of the Top Directors
	 */
	public ArrayList<String> getTopDirectorsByFrequency(int number) {
		// counts the frequencies of each director
		HashMap<String, Integer> directorCount = new HashMap<>();
		for (Movie movie : movies) {
			if (movie.getDirector() != null && !movie.getDirector().isEmpty()) {
				directorCount.put(movie.getDirector(), directorCount.getOrDefault(movie.getDirector(), 0) + 1);
			}
		}
		
		// takes the counts and converts it into a list of entries and sorts
		ArrayList<HashMap.Entry<String, Integer>> sortedDirectors = new ArrayList<>(directorCount.entrySet());
		sortedDirectors.sort((director, num) -> num.getValue().compareTo(director.getValue()));
		
		// extract the top number directors
		ArrayList<String> topDirectors = new ArrayList<>();
		for (int i = 0; i < Math.min(number, sortedDirectors.size()); i++) {
			topDirectors.add(sortedDirectors.get(i).getKey());
		}
		
		return topDirectors;
	}
	
	/**
	 * Searches through the cast for the actor/actress name, then gives a list of the movies that
	 * actor/actress is in.
	 * @param actorName Name of the actor to be searched for
	 * @return An ArrayList of MovieInfo(s) that contain the actor
	 */
	public ArrayList<MovieInfo> searchMoviesByActor(String actorName) {
		ArrayList<MovieInfo> actorMovies = new ArrayList<>();
		
		for (Movie movie : movies) {
			ArrayList<String> castMembers = movie.getCastMembers();
			
			if (castMembers != null && castMembers.contains(actorName)) {
				actorMovies.add(new MovieInfo(
						movie.getTitle(),
						movie.getDirector(),
						movie.getCastMembers()
				));
			}
		}
		return actorMovies;
	}
	
	/**
	 * Similar idea to loadMoviesData, central hub of finding and saving MovieInfo, whether
	 * it is for the rating movies or the gross movies
	 * @param rank The rank of the movie
	 * @param byRating Whether the search is for Rating or Gross
	 * @return 
	 */
	public MovieInfo findMovieInfo(int rank, boolean byRating) {
		for (Movie movie : movies) {
			if ((byRating && movie.getRatingRank() == rank) || (!byRating && movie.getGrossRank() == rank)) {
				return new MovieInfo(
						movie.getTitle(),
						movie.getDirector(),
						movie.getCastMembers()
				);
			}
		}
		return null;
	}
	
	/**
	 * Sub-method to findMovieInfo, is specific to Gross movies
	 * @param rank The rank of the movie
	 * @return
	 */
	public MovieInfo getMovieInfoByGrossRank(int rank) {
		return findMovieInfo(rank, false);
	}
	
	/**
	 * Sub-method to findMovieInfo, is specific to Rated movies
	 * @param rank The rank of the movie
	 * @return
	 */
	public MovieInfo getMovieInfoByRatingRank(int rank) {
		return findMovieInfo(rank, true);
	}
}