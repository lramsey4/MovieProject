package Movie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import Movie.Movie;
import Movie.MovieDatabase;
import Movie.MovieInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
	
import static org.junit.jupiter.api.Assertions.*;
	
class MovieTesting {

	private MovieDatabase database;

	@BeforeEach
	public void setUp() {
		database = new MovieDatabase();

		// Manually add some test data to the database
		database.loadRatedMovies("imdb_movies_toprated.txt");
		database.loadGrossMovies("imdb_movies_gross.txt");
		database.loadCastMovies("imdb_movies_cast.txt");
	}

	@Test
	public void testLoadRatedMovies() {
		// Verify that movies are loaded correctly
		MovieInfo movieByRank = database.getMovieInfoByRatingRank(1);
		assertNotNull(movieByRank, "First rated movie should be loaded");
		assertTrue(movieByRank.getTitle() != null && !movieByRank.getTitle().isEmpty(), 
				"Movie title should not be empty");
	}

	@Test
	public void testLoadGrossMovies() {
		// Verify that gross movies are loaded correctly
		MovieInfo movieByGross = database.getMovieInfoByGrossRank(1);
		assertNotNull(movieByGross, "First gross movie should be loaded");
		assertTrue(movieByGross.getTitle() != null && !movieByGross.getTitle().isEmpty(), 
				"Movie title should not be empty");
	}

	@Test
	public void testLoadCastMovies() {
		// Verify that cast information is loaded correctly
		ArrayList<MovieInfo> moviesWithActor = database.searchMoviesByActor("Tom Hanks");
		assertFalse(moviesWithActor.isEmpty(), "Should find movies with Tom Hanks");
	}

	@Test
	public void testGetTotalEarningsForYear() {
		// Test total earnings calculation
		double earningsForYear = database.getTotalEarningsForYear(2019);
		assertTrue(earningsForYear >= 0, "Total earnings should be non-negative");
	}

	@Test
	public void testGetTotalEarningsForNonExistentYear() {
		// Test earnings for a year with no movies
		double earningsForYear = database.getTotalEarningsForYear(1900);
		assertEquals(0.0, earningsForYear, 0.001, "Earnings for non-existent year should be zero");
	}

	@Test
	public void testGetSortedDirectors() {
		// Verify directors list is generated and sorted
		ArrayList<String> directors = database.getSortedDirectors();
		assertNotNull(directors, "Directors list should not be null");
		assertTrue(directors.size() > 0, "Directors list should not be empty");

		// Check if list is sorted
		for (int i = 1; i < directors.size(); i++) {
			assertTrue(directors.get(i-1).compareTo(directors.get(i)) <= 0, 
				"Directors list should be in alphabetical order");
		}
	}

	@Test
	public void testGetTopDirectorsByFrequency() {
		// Test top directors with different numbers
		ArrayList<String> topDirectors5 = database.getTopDirectorsByFrequency(5);
		assertEquals(5, topDirectors5.size(), "Should return 5 top directors");

		ArrayList<String> topDirectors100 = database.getTopDirectorsByFrequency(100);
		assertTrue(topDirectors100.size() <= 100, "Should not return more directors than exist");
	}

	@Test
	public void testSearchMoviesByActor() {
		// Test searching movies by different actors
		String[] testActors = {"Tom Hanks", "Leonardo DiCaprio", "Meryl Streep"};

		for (String actor : testActors) {
			ArrayList<MovieInfo> movies = database.searchMoviesByActor(actor);
			assertNotNull(movies, "Movies list for " + actor + " should not be null");

			for (MovieInfo movie : movies) {
				assertTrue(movie.getCastMembers().contains(actor), 
						"All returned movies should contain " + actor);
			}
		}
	}

	@Test
	public void testSearchMoviesByNonExistentActor() {
		// Test searching for a non-existent actor
		ArrayList<MovieInfo> movies = database.searchMoviesByActor("Fictional Actor XYZ");
		assertTrue(movies.isEmpty(), "Should return empty list for non-existent actor");
	}

	@Test
	public void testGetMovieInfoByGrossRank() {
		// Test getting movie info by gross rank
		int[] testRanks = {1, 10, 50};

		for (int rank : testRanks) {
			MovieInfo movieInfo = database.getMovieInfoByGrossRank(rank);
			assertNotNull(movieInfo, "Movie info should exist for rank " + rank);
			assertNotNull(movieInfo.getTitle(), "Movie title should not be null");
		}
	}

	@Test
	public void testGetMovieInfoByRatingRank() {
		// Test getting movie info by rating rank
		int[] testRanks = {1, 10, 50};

		for (int rank : testRanks) {
			MovieInfo movieInfo = database.getMovieInfoByRatingRank(rank);
			assertNotNull(movieInfo, "Movie info should exist for rank " + rank);
			assertNotNull(movieInfo.getTitle(), "Movie title should not be null");
		}
	}

	@Test
	public void testGetMovieInfoByInvalidRank() {
		// Test getting movie info by an invalid rank
		int invalidRank = 99999;

		MovieInfo grossMovieInfo = database.getMovieInfoByGrossRank(invalidRank);
		assertNull(grossMovieInfo, "Should return null for invalid gross rank");
		
		MovieInfo ratingMovieInfo = database.getMovieInfoByRatingRank(invalidRank);
		assertNull(ratingMovieInfo, "Should return null for invalid rating rank");
	}
}