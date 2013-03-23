package com.blogspot.symfonyworld.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "outcome")
public class Outcome {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "category_id")
    private long categoryId;
    @Column(name = "comment")
    private String comment;
    @Column(name = "cash_total")
    private float cashTotal;

    /**
     * @return the id
     */
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
    public float getTotalCash() {
        return cashTotal;
    }

    /**
     * @param cashTotal the cashTotal to set
     */
    public void setTotalCash(float totalCash) {
        this.cashTotal = totalCash;
    }

    /**
     * @return outcome string representation
     */
    public String toString() {
        String comment = getComment();
        return getTotalCash() + "zł"
                + ((comment != null && !comment.isEmpty()) ? " (" + comment + ")" : "");
    }
}
