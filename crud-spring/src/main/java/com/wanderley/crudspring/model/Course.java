package com.wanderley.crudspring.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@SQLDelete(sql = "UPDATE Course SET status = 'Inativo' WHERE id=?")
@SQLRestriction("status <> 'Inativo'")// apenas registros com o campo status diferente de 'Inativo' serão retornados
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private long id;

    @NotBlank
    @NotNull
    @Length(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @NotNull
    @Length(max = 30)
    @Column(length = 30, nullable = false)
    private String category;

    @NotNull
    @Length(max = 30)
    @Column(length = 30, nullable = false)
    private String status = "Ativo";

}
