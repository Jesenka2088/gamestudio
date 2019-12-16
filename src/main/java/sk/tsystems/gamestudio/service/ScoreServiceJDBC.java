package sk.tsystems.gamestudio.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sk.tsystems.gamestudio.entity.Score;

/* CREATE TABLE score (username VARCHAR(64) NOT NULL, game VARCHAR(64) NOT NULL, value INT NOT NULL)
 */

public class ScoreServiceJDBC implements ScoreService {

	private static final String LOGIN = "postgres";
	private static final String PASSWORD = "jahodka";
	private static final String URL = "jdbc:postgresql://localhost/gamestudio";
	private static final String INSERT = "INSERT INTO score(username, game, value) VALUES (?,?,?)";

	private static final String SELECT = "SELECT username, game, value FROM score Where game = ? ORDER BY value DESC LIMIT 10";

	public void addScore(Score score) {
		try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(INSERT)) {
			ps.setString(1, score.getUsername());
			ps.setString(2, score.getGame());
			ps.setInt(3, score.getValue());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new GameStudioException(e);
		}
	}

	public List<Score> getTopScores(String game) {
		try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(SELECT)) {
			ps.setString(1, game);
			try (ResultSet rs = ps.executeQuery()) {
				List<Score> scores = new ArrayList<>();
				while (rs.next()) {
					Score score = new Score(rs.getString(1), rs.getString(2), rs.getInt(3));
					scores.add(score);
				}
				return scores;
			}
		} catch (SQLException e) {
			throw new GameStudioException(e);
		}
	}
}
