package Movie;


public enum MenuOption {
	// List of enums consisting of the number, title, and where the call should go.
	QUERY_EARNINGS(1, "Query Total Earnings for a Year", app -> app.queryTotalEarningsForYear()),
	LIST_ALL_DIRECTORS(2, "List All Directors", MovieApplication::listAllDirectors),
	LIST_TOP_DIRECTORS(3, "List Top Directors", MovieApplication::listTopDirectors),
	QUERY_MOVIE_BY_GROSS_RANK(4, "Query Movie by Gross Rank", MovieApplication::queryMovieByGrossRank),
	SEARCH_MOVIES_BY_ACTOR(5, "Search Movies by Actor", MovieApplication::searchMoviesByActor),
	EXIT(6, "Exit", null);

	private final int key;
	private final String description;
	private final MenuAction action;

	MenuOption(int key, String description, MenuAction action) {
		this.key = key;
		this.description = description;
		this.action = action;
	}

	public int getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	public MenuAction getAction() {
		return action;
	}

	/**
	 * Finds a MenuOption by its key
	 * @param key The key to search for
	 * @return The corresponding MenuOption
	 * @throws IllegalArgumentException if no matching option is found
	 */
	public static MenuOption fromKey(int key) {
		for (MenuOption option : values()) {
			if (option.key == key) {
				return option;
			}
		}
		throw new IllegalArgumentException("Invalid menu option: " + key);
	}
}


