package Movie;
import java.util.*;

/*
 * Lauren Ramsey
 * CSC3465 Software Design
 * Richard Dutton and Shannon Duvall
 * ExtraCredit MovieDatabase
 * December 2-12th, 2024
 */

public class MovieApplication {
	private MovieDatabase database;
	private Scanner scanner;
	
	public MovieApplication() {
		database = new MovieDatabase();
		scanner = new Scanner(System.in);
	}
	
	/**
	 * Used to create the database that will be used for the rest of the logic
	 */
	public void initializeDatabase() {
		try {
			database.loadRatedMovies("imdb_movies_toprated.txt");
			database.loadGrossMovies("imdb_movies_gross.txt");
			database.loadCastMovies("imdb_movies_cast.txt");
		} catch (Exception e) {
			System.err.println("Error loading database: " + e.getMessage());
			System.exit(1);
		}
	}
	
	/**
	 * Main interface, is the method called by Main() and uses the interface MenuAction along
	 * with enum MenuOption to choose a query.
	 */
	public void run() {
		initializeDatabase();
		
		while (true) {
			try {
				displayMenu();
				int choice = getUserChoice();
				MenuOption selectedOption = MenuOption.fromKey(choice);
				
				if (selectedOption == MenuOption.EXIT) {
					System.out.println("Exiting Movie Database. Goodbye!");
					break;
				}
				
				if (selectedOption.getAction() != null) {
					selectedOption.getAction().execute(this);
					promptToContinue();
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid choice. Please try again.");
				promptToContinue();
			}
		}
	}
	
	/**
	 * used to format the menu of options
	 */
	private void displayMenu() {
		System.out.println("\n--- Movie Database ---");
		for (MenuOption option : MenuOption.values()) {
			if (option != MenuOption.EXIT) {
				System.out.printf("%d. %s%n", option.getKey(), option.getDescription());
			}
		}
		
		System.out.printf("%d. %s%n", MenuOption.EXIT.getKey(), MenuOption.EXIT.getDescription());
		System.out.println("Enter your choice: ");
	}
	
	/**
	 * Takes the user's number that corresponds to the list in MenuOption
	 * @return the user input, if given in an integer format
	 */
	private int getUserChoice() {
		while (true) {
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.print("Invalid input. Please enter a number: ");
			}
		}
	}
	
	/**
	 * Formats "Query Total Earnings for a Year", runs the database method for logic and checks
	 * for validity
	 */
	protected void queryTotalEarningsForYear() {
		System.out.print("Enter the year to check total earnings: ");
		try {
			int year = Integer.parseInt(scanner.nextLine());
			double earnings = database.getTotalEarningsForYear(year);
			System.out.printf("Total box office earnings in %d: $%.2f million%n", year, earnings);
		} catch (NumberFormatException e) {
			System.out.println("Invalid year format.");
		}
	}
	
	/**
	 * Formats "Lists All Directors", runs the database method for logic and prints in a list
	 */
	protected void listAllDirectors() {
		ArrayList<String> directors = database.getSortedDirectors();
		System.out.println("\nList of all directors (alphabetically):");
		for (String director : directors) {
			System.out.println(director);
		}
		System.out.printf("Total directors: %d%n", directors.size());
	}
	
	/**
	 * Formats "List Top Directors", runs the database method for logic, sets up the formatting of
	 * the list printing and checks for validity.
	 */
	protected void listTopDirectors() {
		System.out.print("Enter number of top directors to list: ");
		try {
			int n = Integer.parseInt(scanner.nextLine());
			ArrayList<String> topDirectors = database.getTopDirectorsByFrequency(n);
			
			System.out.println("\nTop " + n + " Directors by Number of Movies:");
			for (int i = 0; i < topDirectors.size(); i++) {
				System.out.printf("%d. %s%n", i+1, topDirectors.get(i));
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid number format.");
		}
	}
	
	/**
	 * Formats "Query Movie By Gross Rank", runs the database method for logic, checks for validity.
	 */
	protected void queryMovieByGrossRank() {
		System.out.print("Enter the gross rank of the movie: ");
		try {
			int rank = Integer.parseInt(scanner.nextLine());
			MovieInfo movieInfo = database.getMovieInfoByGrossRank(rank);
			displayMovieInfo(movieInfo, true);
		} catch (NumberFormatException e) {
			System.out.println("Invalid rank format.");
		}
	}
	
	/**
	 * Formats "Search Movies by Actor", runs the database method for logic
	 */
	protected void searchMoviesByActor() {
		System.out.print("Enter actor name: ");
		String actorName = scanner.nextLine();
		ArrayList<MovieInfo> movies = database.searchMoviesByActor(actorName);
		for (MovieInfo movie : movies) {
			displayMovieInfo(movie, false);
		}
	}
	
	/**
	 * Creates a hub for setting MovieInfo by specific characteristics
	 * @param info Movie information
	 * @param gross checks whether the info is for Gross Rank
	 */
	private void displayMovieInfo(MovieInfo info, boolean gross) {
		if (info == null) {
			System.out.println("No movie found with the given rank.");
			return;
		}
		
		System.out.println("\nMovie Information:");
		System.out.println("Title: " + info.getTitle());
		
		if (gross) {
			System.out.println("Year: " + info.getYear());
			System.out.printf("Total Box Office Earnings: $%.2f million%n", info.getBoxOfficeEarnings());
		}
		else {
			System.out.println("Director: " + info.getDirector());
			System.out.println("Cast:");
			ArrayList<String> cast = info.getCastMembers();
			if (cast != null && !cast.isEmpty()) {
				for (String actor : cast) {
					System.out.println("- " + actor);
				}
			} else {
				System.out.println("No cast information available.");
			}
		}
	}
	
	/**
	 * Allows for as many user prompts as long as it doesn't equal EXIT
	 */
	private void promptToContinue() {
		System.out.print("\nPress Enter to continue...");
		scanner.nextLine();
	}
	
}