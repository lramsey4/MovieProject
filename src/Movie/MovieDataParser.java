package Movie;

public interface MovieDataParser {
	/**
	 * Interface used to separate each file by a line, and then by the specific sections of 
	 * each movie.
	 * @param movie Specific movie in the list
	 * @param parts The separate sections of the file line
	 */
	void parseMovieData(Movie movie, String[] parts);
}
