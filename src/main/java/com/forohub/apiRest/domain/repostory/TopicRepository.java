package com.forohub.apiRest.domain.repostory;

import com.forohub.apiRest.domain.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("""
            SELECT t FROM Topic t 
            ORDER BY FUNCTION('STR_TO_DATE', t.createDate, '%m/%d/%Y') ASC
            """)
    List<Topic> findAllByCreateDate();
}
