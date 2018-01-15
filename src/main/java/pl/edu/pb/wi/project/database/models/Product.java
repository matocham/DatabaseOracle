package pl.edu.pb.wi.project.database.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
public class Product implements Serializable {
    @Id
    @JoinColumn(name = "ID")
    Long Id;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "OWNER_ID",referencedColumnName = "ID")
    Users ownerId;

    @Column(name = "TITLE")
    String title;

    @Column(name = "DESCRIPTION")
    String description;

    @OneToMany (targetEntity = Category.class)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    Set<Category> categoryId;

    @OneToMany (targetEntity = Category.class)
    @JoinColumn(name = "EXCHANGE_FOR",referencedColumnName = "ID")
    Set<Category> exchangeFor;

    @Column(name = "ADD_DATE")
    Date date;

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

    public Users getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Users ownerId) {
        this.ownerId = ownerId;
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
