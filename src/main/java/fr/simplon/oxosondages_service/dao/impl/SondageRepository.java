package fr.simplon.oxosondages_service.dao.impl;

import fr.simplon.oxosondages_service.entity.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SondageRepository extends JpaRepository <Sondage, Long> {
}
