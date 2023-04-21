package fr.simplon.oxosondages_service;

import fr.simplon.oxosondages_service.entity.Sondage;
import org.apiguardian.api.API;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class OxoSondagesServiceApplicationTests {

    /**
     * Tests the display of all surveys.
     */

    @Test
    public void testDisplayAllSurveys() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<Sondage>> response = restTemplate.exchange(
                "http://localhost:8080/rest/sondages",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Sondage>>() {
                }
        );
        List<Sondage> sondages = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(sondages);
        Assertions.assertFalse(sondages.isEmpty());
    }

    /**
     * Tests the display of a specific survey.
     */

    @Test
    public void testDisplaySurvey() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Création d'un sondage à partir de l'API REST
        Sondage sondageCreate = new Sondage("test GetSurvey", "test GetSurvey", LocalDate.now(), LocalDate.now().plusDays(7), "test GetSurvey");
        ResponseEntity<Sondage> responseCreate = restTemplate.postForEntity(
                "http://localhost:8080/rest/sondages",
                sondageCreate,
                Sondage.class
        );
        assertEquals(HttpStatus.OK, responseCreate.getStatusCode());

        // Récupération du sondage créé à partir de l'API REST
        Sondage sondageGet = responseCreate.getBody();
        assertNotNull(sondageGet);

        ResponseEntity<Sondage> responseGet = restTemplate.exchange(
                "http://localhost:8080/rest/sondages/{id}",
                HttpMethod.GET,
                null,
                Sondage.class,
                sondageGet.getId()
        );

        // Vérification des données du sondage récupéré
        assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        Sondage sondage = responseGet.getBody();
        assertNotNull(sondage);
        assertEquals(sondageGet.getId(), sondage.getId());
        assertEquals(sondageGet.getDescription(), sondage.getDescription());
        assertEquals(sondageGet.getQuestion(), sondage.getQuestion());
        assertEquals(sondageGet.getDateCreation(), sondage.getDateCreation());
        assertEquals(sondageGet.getDateCloture(), sondage.getDateCloture());
        assertEquals(sondageGet.getCreateur(), sondage.getCreateur());
    }

    /**
     * Tests the addition of a new survey.
     */
    @Test
    public void testAddSurvey() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Sondage sondage = new Sondage("test", "test", LocalDate.now(), LocalDate.of(2023, 4, 27), "Anonyme");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Sondage> request = new HttpEntity<>(sondage, headers);

        ResponseEntity<Sondage> response = restTemplate.exchange("http://localhost:8080/rest/sondages",
                HttpMethod.POST,
                request,
                Sondage.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals(sondage.getQuestion(), response.getBody().getQuestion());
        assertEquals(sondage.getDescription(), response.getBody().getDescription());
        assertEquals(sondage.getDateCreation(), response.getBody().getDateCreation());
        assertEquals(sondage.getDateCloture(), response.getBody().getDateCloture());
        assertEquals(sondage.getCreateur(), response.getBody().getCreateur());
    }

    /**

     This method tests the update of a survey.

     It creates a survey and then modifies the data of the survey.

     The ID of the created survey is retrieved to modify it.

     The test then verifies if the update was successful by comparing the updated survey data with the retrieved data.
     */

    @Test
    public void testUpdateSurvey() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Création  d'un sondage pour ensuite modifier les données
        Sondage sondageUpdate = new Sondage("test Update", "test Update", LocalDate.now(), LocalDate.now().plusDays(7), "test Update");

        HttpEntity<Sondage> requestCreate = new HttpEntity<>(sondageUpdate, headers);
        ResponseEntity<Sondage> responseCreate = restTemplate.exchange(
                "http://localhost:8080/rest/sondages",
                HttpMethod.POST,
                requestCreate,
                Sondage.class);

        assertEquals(HttpStatus.OK, responseCreate.getStatusCode());
        assertNotNull(responseCreate.getBody().getId());

        // Récupération de l'identifiant du sondage créé
        Long sondageupdateId = responseCreate.getBody().getId();

        // Modification du sondage créé
        String nouvelleDescription = "Test Update réussi";

        Sondage sondage = restTemplate.getForObject("http://localhost:8080/rest/sondages/{id}", Sondage.class, sondageupdateId);
        sondage.setDescription(nouvelleDescription);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Sondage> request = new HttpEntity<>(sondage, headers);

        ResponseEntity<Sondage> response = restTemplate.exchange(
                "http://localhost:8080/rest/sondages/{id}",
                HttpMethod.PUT,
                request,
                Sondage.class,
                sondageupdateId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sondageupdateId, response.getBody().getId());
        assertEquals(nouvelleDescription, response.getBody().getDescription());
        assertEquals(sondage.getDateCreation(), response.getBody().getDateCreation());
        assertEquals(sondage.getDateCloture(), response.getBody().getDateCloture());
        assertEquals(sondage.getCreateur(), response.getBody().getCreateur());
    }


    /**

     This method tests the deletion of a survey.

     It creates a survey and then deletes it.

     The test then verifies if the deletion was successful by checking the HTTP status code of the response.
     */

    @Test
    public void testDeleteSurvey() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Création  d'un sondage pour le supprimer ensuite
        Sondage sondageDelete = new Sondage("test Delete", "test Delete", LocalDate.now(), LocalDate.now().plusDays(7), "test Delete");


        HttpEntity<Sondage> requestCreate = new HttpEntity<>(sondageDelete, headers);
        ResponseEntity<Sondage> responseCreate = restTemplate.exchange(
                "http://localhost:8080/rest/sondages",
                HttpMethod.POST,
                requestCreate,
                Sondage.class);

        assertEquals(HttpStatus.OK, responseCreate.getStatusCode());
        assertNotNull(responseCreate.getBody().getId());

        // Récupération de l'identifiant du sondage créé
        Long sondagedeleteId = responseCreate.getBody().getId();

        // Suppression du sondage créé
        HttpEntity<Void> requestDelete = new HttpEntity<>(headers);
        ResponseEntity<Void> responseDelete = restTemplate.exchange(
                "http://localhost:8080/rest/sondages/{id}",
                HttpMethod.DELETE,
                requestDelete,
                Void.class,
                sondagedeleteId);

        //Verification du code de statut HTTP de la réponse de la requête HTTP. Il doit être OK si la suppression a bien été effectuée.
        assertEquals(HttpStatus.OK, responseDelete.getStatusCode());
    }

}



