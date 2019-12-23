package sk.tsystems.gamestudio.service;

import java.util.List;

import sk.tsystems.gamestudio.entity.Comment;

public interface CommentService {

	List<Comment> getAllComments(String game);

	void addComment(Comment comment);

}
