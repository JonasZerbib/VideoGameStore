package modle;

import java.util.Collections;
import java.util.Vector;

public class Genre {
	// Genre Fields
	String name;
	Vector<Game> games;

	// Constructors
	public Genre(String name) {
		super();
		this.name = name;
		this.games = new Vector<Game>();

	}

	public Genre(String name, Vector<Game> games) {
		super();
		this.name = name;

		if (this.games == null) {
			this.games = new Vector<Game>();
		} else {
			this.games.clear();
		}
		if (games != null) {
			this.games.setSize(games.size());
			Collections.copy(this.games, games);
		}

	}

	// Get and Set functions
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<Game> getGames() {
		return games;
	}

	public void setGame(Vector<Game> games) {
		this.games.clear();
		this.games.setSize(games.size());
		Collections.copy(this.games, games);
	}

}
