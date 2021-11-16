package uaic.info.csft.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uaic.info.csft.userservice.entities.Language;
import uaic.info.csft.userservice.entities.Proficiencies;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface LanguageRepository extends JpaRepository<Language, Long> {

    List<Language> findByName(String name);
    Optional<Language> findByNameAndProficiency(String name, Proficiencies proficiency);
}