package pl.edu.pb.wi.project.database.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
public class Product implements Serializable {
    @JoinColumn(name = "ID")
    @Id
    Long Id;

    @Column(name = "TITLE")
    String title;

    @Column(name = "DESCRIPTION")
    String description;

    @OneToMany(targetEntity = Category.class)
    @JoinColumn(name = "CATEGORYID")
    Set<Category> categoryId;

    @OneToMany(targetEntity = Category.class)
    @JoinColumn(name = "EXCGANEFOR", referencedColumnName = "ID")
    Set<Category> exchangeFor;

    @Column(name = "DATE")
    Date date;

    @Column(name = "ISBOUGHT")
    Boolean isBought;

    @Column(name = "IMAGEURL")
    String imageUrl;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Category> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Set<Category> categoryId) {
        this.categoryId = categoryId;
    }

    public Set<Category> getExchangeFor() {
        return exchangeFor;
    }

    public void setExchangeFor(Set<Category> exchangeFor) {
        this.exchangeFor = exchangeFor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getBought() {
        return isBought;
    }

    public void setBought(Boolean bought) {
        isBought = bought;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
