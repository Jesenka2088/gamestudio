package sk.tsystems.gamestudio.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import sk.tsystems.gamestudio.entity.Comment;

@Component
@Transactional
public class CommentServiceJPA implements CommentService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addComment(Comment comment) {
		entityManager.persist(comment);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Comment> getAllComments(String game) {
		return (List<Comment>) entityManager.createQuery("select p from Comment p where p.game = :game order by p desc").setParameter("game", game).getResultList();
	}
}
