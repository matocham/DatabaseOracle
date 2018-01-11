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

    @OneToMany (targetEntity = Category.class)
    @JoinColumn(name = "CATEGORYID")
    Set<Category> categoryId;

    @OneToMany (targetEntity = Category.class)
    @JoinColumn(name = "EXCGANEFOR",referencedColumnName = "ID")
    Set<Category> exchangeFor;

    @Column(name = "DATE")
    Date date;

    @Column(name = "ISBOUGHT")
    Boolean isBought;

    @Column(name = "IMAGEURL")
    String imageUrl;
}
