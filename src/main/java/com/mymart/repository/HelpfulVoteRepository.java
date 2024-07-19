package com.mymart.repository;

import com.mymart.model.HelpfulVote;
import com.mymart.model.Rating;
import com.mymart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpfulVoteRepository extends JpaRepository<HelpfulVote, Long> {
    HelpfulVote findByUserAndRating(User user, Rating rating);
}