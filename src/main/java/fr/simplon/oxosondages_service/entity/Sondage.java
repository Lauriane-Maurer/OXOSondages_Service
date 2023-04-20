package fr.simplon.oxosondages_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name="sondages")
public class Sondage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @NotBlank
    @Size(min = 3, max = 120)
    private String description;

    @NotNull
    @Size(max = 120)
    private String question;

    @NotNull
    private LocalDate dateCreation;

    @NotNull @Future
    private LocalDate dateCloture;

    @NotNull
    @Size(max = 64)
    private String createur;


    public Sondage() {
    }

    public Sondage(String description, String question, LocalDate dateCreation, LocalDate dateCloture, String createur) {
        this.description = description;
        this.question = question;
        this.dateCreation = dateCreation;
        this.dateCloture = dateCloture;
        this.createur = createur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    /* Fixe la date de création du sondage à la date du jour en utilisant l’annotation
    @PrePersist pour déclencher cette méthode automatiquement. */
    @PrePersist
    private void setDateCreation() {
        this.dateCreation = LocalDate.now();
    }

    public LocalDate getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(LocalDate dateCloture) {
        this.dateCloture = dateCloture;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }
}
