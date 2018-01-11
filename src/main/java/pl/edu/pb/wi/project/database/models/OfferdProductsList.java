package pl.edu.pb.wi.project.database.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class OfferdProductsList implements Serializable {

    @Id
    @ManyToOne(targetEntity = Offer.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_OFFER"))
    Offer offerId;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Product.class)
    @JoinColumn(name = "PRODUCTID")
    Set<Product> productId;
}
