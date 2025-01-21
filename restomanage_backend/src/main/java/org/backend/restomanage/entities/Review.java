package org.backend.restomanage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}