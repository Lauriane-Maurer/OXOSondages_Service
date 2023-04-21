package fr.simplon.oxosondages_service.controller;

import fr.simplon.oxosondages_service.dao.impl.SondageRepository;
import fr.simplon.oxosondages_service.entity.Sondage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

/**

 The SondageController class defines the REST endpoints for the Sondage resource.
 It provides methods for retrieving, creating, updating and deleting sondages.

 */

@RestController
public class SondageController {

    @Autowired
    private SondageRepository repo;

    /**

     Constructor for the SondageController class.
     @param fr The SondageRepository instance to use.

     */
    public SondageController(SondageRepository fr) {
        this.repo = fr;

        //this.repo.save(new Sondage(1L, "Télétravail", "Souhaiteriez-vous pouvoir être davantage en télétravail ?", LocalDate.now(), LocalDate.of(2023,4,27), "Elara Friand"));
        //this.repo.save(new Sondage(2L, "Cantine", "Trouvez-vous que les repas proposés à la cantine sont suffisammenet diversifiés ?", LocalDate.now(), LocalDate.of(2023,4,27), "Lauriane Maurer"));
        //this.repo.save(new Sondage(3L, "Mascotte", "Aimez-vous l'idée du Capybara comme mascotte de la société ?", LocalDate.now(), LocalDate.of(2023,4,27), "Lauriane Maurer"));
    }

    /**

     Retrieves a list of all sondages from the database.
     @return A List of Sondage objects.
     */

    @GetMapping("/rest/sondages")
    public List<Sondage> getSondages() {
        return repo.findAll();
    }


    /**

     Retrieves the details of a specific sondage.
     @param id The ID of the sondage to retrieve.
     @return The Sondage object with the specified ID.
     @throws NoSuchElementException if no sondage exists with the specified ID.
     */

    @GetMapping("/rest/sondages/{id}")
    public Sondage getSurveyDetails(@PathVariable Long id)throws NoSuchElementException  {
        return repo.findById(id).orElseThrow();
    }


    /**

     Creates a new sondage.
     @param newSurvey The Sondage object to create.
     @return The newly created Sondage object.
     */
    @PostMapping("/rest/sondages")
    public Sondage addSurvey(@RequestBody Sondage newSurvey) {
        return repo.save(newSurvey);
    }


    /**

     Updates an existing sondage with new values.
     @param newSurvey The Sondage object containing the new values.
     @param id The ID of the sondage to update.
     @return The updated Sondage object.
     */
    @PutMapping("/rest/sondages/{id}")
    public Sondage updateSurvey(@RequestBody Sondage newSurvey, @PathVariable Long id) {
        return repo.findById(id)
                .map(sondage -> {
                    sondage.setDescription(newSurvey.getDescription());
                    sondage.setQuestion(newSurvey.getQuestion());
                    sondage.setDateCreation(newSurvey.getDateCreation());
                    sondage.setDateCloture(newSurvey.getDateCloture());
                    sondage.setCreateur(newSurvey.getCreateur());
                    return repo.save(sondage);
                })
                .orElseGet(() -> {
                    newSurvey.setId(id);
                    return repo.save(newSurvey);
                });
    }


    /**

     Deletes a sondage with the specified ID.
     @param id The ID of the sondage to delete.
     */

    @DeleteMapping("/rest/sondages/{id}")
    public void deleteSurvey(@PathVariable Long id) {
        repo.deleteById(id);
    }

}

