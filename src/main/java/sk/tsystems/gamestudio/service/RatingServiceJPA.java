package sk.tsystems.gamestudio.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import sk.tsystems.gamestudio.entity.Rating;

@Component
@Transactional
public class RatingServiceJPA implements RatingService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addRating(Rating rating) {
		entityManager.persist(rating);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rating> getRatingsByGameName(String game) {
		try {
			return (List<Rating>) entityManager
					.createQuery("select r from Rating r where r.game = :game order by r desc")
					.setParameter("game", game).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Rating getRatingByGameNameAndUserName(String game, String name) {
		try {
			return (Rating) entityManager
					.createQuery("select r from Rating r where r.game = :game and r.username = :name")
					.setParameter("game", game).setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void updateRatingValueForUserAndGame(String game, String name, int value) {
		entityManager.createQuery("update Rating r set r.value = :value where r.game = :game and r.username = :name")
				.setParameter("game", game).setParameter("name", name).setParameter("value", value).executeUpdate();
	}

	@Override
	public double getAverageRatingByGameName(String game) {
		try {
			return (double) entityManager
					.createQuery("SELECT AVG(value) FROM Rating r where r.game= :game group by(game)")
					.setParameter("game", game).getSingleResult();
		} catch (NoResultException e) {
			return 0;
		}

	}
}
