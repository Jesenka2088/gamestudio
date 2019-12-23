package sk.tsystems.gamestudio.service;

import java.util.List;

import sk.tsystems.gamestudio.entity.Rating;

public interface RatingService {

	void addRating(Rating rating);


	List<Rating> getRatingsByGameName(String game);


	Rating getRatingByGameNameAndUserName(String game, String name);


	void updateRatingValueForUserAndGame(String game, String name, int value);


	double getAverageRatingByGameName(String game);


	


	

}
