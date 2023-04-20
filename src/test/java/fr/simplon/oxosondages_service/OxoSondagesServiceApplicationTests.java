package fr.simplon.oxosondages_service;

import fr.simplon.oxosondages_service.entity.Sondage;
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

    @Test
    public void testDisplaySurvey() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Sondage> request = new HttpEntity<>(headers);
        ResponseEntity<Sondage> response = restTemplate.exchange(
                "http://localhost:8080/rest/sondages/{id}",
                HttpMethod.GET,
                request,
                Sondage.class,
                2L);
        Sondage sondage = response.getBody();
        assertNotNull(sondage);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2L, response.getBody().getId());
        assertEquals("Description", sondage.getDescription());
        assertEquals("Question", sondage.getQuestion());
        assertEquals("DateCreation", sondage.getDateCreation());
        assertEquals("DateCloture", sondage.getDateCloture());
        assertEquals("Createur", sondage.getCreateur());
    }

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



