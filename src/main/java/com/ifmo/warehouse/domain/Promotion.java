package com.ifmo.warehouse.domain;

import java.time.ZonedDateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Promotion.
 */
@Entity
@Table(name = "promotion")
@Document(indexName = "promotion")
public class Promotion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotNull
    @Column(name = "date_from", nullable = false)
    private ZonedDateTime dateFrom;
    
    @NotNull
    @Column(name = "date_to", nullable = false)
    private ZonedDateTime dateTo;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "other_details")
    private String otherDetails;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDateFrom() {
        return dateFrom;
    }
    
    public void setDateFrom(ZonedDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public ZonedDateTime getDateTo() {
        return dateTo;
    }
    
    public void setDateTo(ZonedDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getOtherDetails() {
        return otherDetails;
    }
    
    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Promotion promotion = (Promotion) o;
        if(promotion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, promotion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Promotion{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", dateFrom='" + dateFrom + "'" +
            ", dateTo='" + dateTo + "'" +
            ", description='" + description + "'" +
            ", otherDetails='" + otherDetails + "'" +
            '}';
    }
}
