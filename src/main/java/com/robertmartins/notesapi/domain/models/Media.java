package com.robertmartins.notesapi.domain.models;

import com.robertmartins.notesapi.infraestructure.configs.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "koda_media")
@Getter
@Setter
public class Media extends BaseEntity {

    @Column(name = "uri", nullable = false)
    private String uri;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "size", nullable = false)
    private Integer size;

}
