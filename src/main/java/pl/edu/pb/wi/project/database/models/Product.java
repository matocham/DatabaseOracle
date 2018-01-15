package pl.edu.pb.wi.project.database.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Product implements Serializable {
    @Id
    @JoinColumn(name = "ID")
    Long Id;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "OWNER_ID",referencedColumnName = "ID")
    Users owner;

    @Column(name = "TITLE")
    String title;

    @Column(name = "DESCRIPTION")
    String description;

    @ManyToOne (targetEntity = Category.class)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    Category category;

    @ManyToOne (targetEntity = Category.class)
    @JoinColumn(name = "EXCHANGE_FOR",referencedColumnName = "ID")
    Category exchangeFor;

    @Column(name = "ADD_DATE")
    Date addDate;

    @Column(name = "EXCHANGED")
    Boolean exchanged;

    @Column(name = "IMAGE_PATH")
    String imagePath;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users ownerId) {
        this.owner = ownerId;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category categoryId) {
        this.category = categoryId;
    }

    public Category getExchangeFor() {
        return exchangeFor;
    }

    public void setExchangeFor(Category exchangeFor) {
        this.exchangeFor = exchangeFor;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Boolean getExchanged() {
        return exchanged;
    }

    public void setExchanged(Boolean exchanged) {
        this.exchanged = exchanged;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
