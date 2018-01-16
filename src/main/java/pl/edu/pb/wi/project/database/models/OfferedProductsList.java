package pl.edu.pb.wi.project.database.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Table(name = "OFFERED_PRODUCTS_LIST")
@Entity
public class OfferedProductsList implements Serializable {

    @Id
    @ManyToOne(targetEntity = Offer.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_OFFER"))
    Offer offerId;

    @OneToMany(targetEntity = Product.class)

    @JoinColumn(name = "PRODUCTID", foreignKey = @ForeignKey(name = "FK_PRODUCT"))
    Set<Product> productId;

    public Offer getOfferId() {
        return offerId;
    }

    public void setOfferId(Offer offerId) {
        this.offerId = offerId;
    }

    public Set<Product> getProductId() {
        return productId;
    }

    public void setProductId(Set<Product> productId) {
        this.productId = productId;
    }
}
