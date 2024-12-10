package Movie;

public interface MenuAction {
	/**
	 * Executes the specific action for a menu option
	 * @param app The MovieApplication instance to provide context and access
	 */
	void execute(MovieApplication app);
}
