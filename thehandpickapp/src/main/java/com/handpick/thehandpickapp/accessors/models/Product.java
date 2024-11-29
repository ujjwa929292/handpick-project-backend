package com.handpick.thehandpickapp.accessors.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false, length = 255, name="title")
    private String title;

    @Column(columnDefinition = "TEXT", name="shortdescription")
    private String shortDescription;

    @Column(length = 255, name="url")
    private String url;

    @Column(columnDefinition = "TEXT", name="description")
    private String description;

    @Column(length = 255,name="headline")
    private String headline;

    @Column(length = 255, name = "headlinedescription")
    private String headlineDescription;


    @Column(length = 1000, name="price")
    private String price;

    @ManyToOne
    @JoinColumn(name = "maincategory_id")
    private Category mainCategory;


    @Column(name="fileids", columnDefinition = "UUID[]")
    private List<UUID> fileIds;

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated = LocalDateTime.now();

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updated = LocalDateTime.now();
    }

}
