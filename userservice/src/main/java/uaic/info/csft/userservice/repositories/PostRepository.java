package uaic.info.csft.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uaic.info.csft.userservice.entities.Post;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
}

