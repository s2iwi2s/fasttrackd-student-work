package com.cooksys.assessment.socialmedia.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.assessment.socialmedia.entities.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
	List<Tweet> findAllByMentionsCredentialsUsername(String username);

	List<Tweet> findByMentionsCredentialsUsername(String username);

	List<Tweet> findAllByDeletedFalseOrderByPostedDesc();

	List<Tweet> findByDeletedFalseOrderByPostedDesc();

	Optional<Tweet> findByDeletedFalseAndId(Long id);

	List<Tweet> findByInReplyToAndDeletedFalseOrderByPostedDesc(Tweet inReplyTo);

	Set<Tweet> findByInReplyToOrderByPostedDesc(Tweet inReplyTo);

	List<Tweet> findByRepostOfAndDeletedFalse(Tweet repostOf);

	List<Tweet> findAllByInReplyToAndDeletedFalseOrderByPostedDesc(Tweet inReplyTo);

	List<Tweet> findAllByRepostOfAndDeletedFalse(Tweet repostOf);

	Optional<List<Tweet>> findAllByHashtagsLabelAndDeletedFalseOrderByPostedDesc(String label);

	@Query("SELECT t FROM Tweet t JOIN t.author a WHERE t.deleted=false and a.credentials.username = :username")
	List<Tweet> getUsersTweets(String username);

	@Query("select distinct t from Tweet t left join fetch t.inReplyTo where t.id = :id order by t.posted")
	List<Tweet> getTweetsInReplyToBefore(Long id);
}
