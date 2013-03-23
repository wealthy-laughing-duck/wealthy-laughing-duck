package com.blogspot.symfonyworld.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "outcome")
public class Outcome {

    private long id;
    private long categoryId;
    private String comment;
    private float cashTotal;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public long getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the categoryId
     */
    @Column(name = "category_id")
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the comment
     */
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the totalCash
     */
    @Column(name = "cash_total")
    public float getTotalCash() {
        return cashTotal;
    }

    /**
     * @param cashTotal the cashTotal to set
     */
    public void setTotalCash(float totalCash) {
        this.cashTotal = totalCash;
    }
}
