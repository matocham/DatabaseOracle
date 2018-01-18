package pl.edu.pb.wi.project.database.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Product implements Serializable {
    @Id
    @JoinColumn(name = "ID")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    Long id;

    @Column(name = "TITLE")
    String title;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "OWNER_ID", referencedColumnName = "ID")
    Users owner;


    @Column(name = "DESCRIPTION")
    String description;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "CATEGORY_ID")
    Category category;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "EXCHANGE_FOR", referencedColumnName = "ID")
    Category exchangeFor;

    @Column(name = "ADD_DATE")
    Date addDate;

    @Column(name = "EXCHANGED")
    boolean exchanged;

    @Column(name = "IMAGE_PATH")
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public boolean getExchanged() {
        return exchanged;
    }

    public void setExchanged(boolean exchanged) {
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
