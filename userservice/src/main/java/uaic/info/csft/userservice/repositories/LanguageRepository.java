package uaic.info.csft.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uaic.info.csft.userservice.entities.Language;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LanguageRepository extends JpaRepository<Language, Long> {
}