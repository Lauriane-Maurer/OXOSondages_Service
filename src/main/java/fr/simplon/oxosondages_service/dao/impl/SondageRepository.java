package fr.simplon.oxosondages_service.dao.impl;

import fr.simplon.oxosondages_service.entity.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;


/**

 A repository interface for managing "Sondage" entities.
 It extends the JpaRepository interface with "Sondage" entity type and "Long" id type.
 */

public interface SondageRepository extends JpaRepository <Sondage, Long> {
}
