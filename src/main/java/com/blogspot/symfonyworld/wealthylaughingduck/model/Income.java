package com.blogspot.symfonyworld.wealthylaughingduck.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "income")
public class Income {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "category_id")
    private long categoryId;

    @Column(name = "description")
    private String comment;

    @Column(name = "amount")
    private float amount;

    @ManyToOne
    @JoinColumn(name="created_by")
    private User user;

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
     * @return the amount
     */
    public float getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(float amount) {
        this.amount = amount;
    }

    /**
     * @return outcome string representation
     */
    public String toString() {
        String comment = getComment();
        return getAmount() + "zł"
                + ((comment != null && !comment.isEmpty()) ? " (" + comment + ")" : "")
                + " by " + getUser().getName();
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
