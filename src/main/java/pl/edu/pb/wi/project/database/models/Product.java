package pl.edu.pb.wi.project.database.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
public class Product implements Serializable {
    @Id
    @JoinColumn(name = "ID")
    Long id;

    @Column(name = "TITLE")
    String title;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "OWNER_ID",referencedColumnName = "ID")
    Users owner;


    @Column(name = "DESCRIPTION")
    String description;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "CATEGORYID")
    Category categoryId;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "EXCHANGEFOR", referencedColumnName = "ID")
    Category exchangeFor;

    @Column(name = "DATE")
    Date date;

    @Column(name = "EXCHANGED")
    Boolean exchanged;

    @Column(name = "IMAGEURL")
    String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public Category getExchangeFor() {
        return exchangeFor;
    }

    public void setExchangeFor(Category exchangeFor) {
        this.exchangeFor = exchangeFor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getExchanged() {
        return exchanged;
    }

    public void setExchanged(Boolean exchanged) {
        exchanged = exchanged;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }
}
