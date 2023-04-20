package fr.simplon.oxosondages_service.controller;

import fr.simplon.oxosondages_service.dao.impl.SondageRepository;
import fr.simplon.oxosondages_service.entity.Sondage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class SondageController {

    @Autowired
    private SondageRepository repo;


    public SondageController(SondageRepository fr) {
        this.repo = fr;

        //this.repo.save(new Sondage(1L, "Télétravail", "Souhaiteriez-vous pouvoir être davantage en télétravail ?", LocalDate.now(), LocalDate.of(2023,4,27), "Elara Friand"));
        //this.repo.save(new Sondage(2L, "Cantine", "Trouvez-vous que les repas proposés à la cantine sont suffisammenet diversifiés ?", LocalDate.now(), LocalDate.of(2023,4,27), "Lauriane Maurer"));
        //this.repo.save(new Sondage(3L, "Mascotte", "Aimez-vous l'idée du Capybara comme mascotte de la société ?", LocalDate.now(), LocalDate.of(2023,4,27), "Lauriane Maurer"));
    }

    @GetMapping("/rest/sondages")
    public List<Sondage> getSondages() {
        return repo.findAll();
    }

    @GetMapping("/rest/sondages/{id}")
    public Sondage getSurveyDetails(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping("/rest/sondages")
    public Sondage addSurvey(@RequestBody Sondage newSurvey) {
        return repo.save(newSurvey);
    }

    @PutMapping("/rest/sondages/{id}")
    public Sondage updateSurvey(@RequestBody Sondage newSurvey, @PathVariable Long id) {
        return repo.findById(id)
                .map(sondage -> {
                    sondage.setDescription(newSurvey.getDescription());
                    sondage.setQuestion(newSurvey.getQuestion());
                    sondage.setDateCreation(newSurvey.getDateCreation());
                    sondage.setDateCloture(newSurvey.getDateCloture());
                    return repo.save(sondage);
                })
                .orElseGet(() -> {
                    newSurvey.setId(id);
                    return repo.save(newSurvey);
                });
    }

    @DeleteMapping("/rest/sondages/{id}")
    public void deleteSurvey(@PathVariable Long id) {
        repo.deleteById(id);
    }

}

