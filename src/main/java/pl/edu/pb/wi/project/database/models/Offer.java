package pl.edu.pb.wi.project.database.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "OFFER")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "finalize_exchange",
                procedureName = "utilities.finalize_exchange",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "offer_id", type = Long.class)
                })
})
public class Offer implements Serializable {

    @JoinColumn(name = "ID")
    @Id
    Long id;

    @Column(name = "OFFERED_DATE")
    Date offeredDate;

    @Column(name = "EXCHANGE_DATE")
    Date exchangeDate;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "BUYER_ID", referencedColumnName = "ID")
    Users buyer;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "PRODUCT_ID")
    Product product;

    @ManyToMany
    @JoinTable(name = "OFFERED_PRODUCTS_LIST", joinColumns = @JoinColumn(name = "OFFER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID"))
    List<Product> offeredProducts;

    @Column(name = "RATE")
    Integer rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOfferedDate() {
        return offeredDate;
    }

    public void setOfferedDate(Date offeredDate) {
        this.offeredDate = offeredDate;
    }

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public Users getBuyer() {
        return buyer;
    }

    public void setBuyer(Users buyer) {
        this.buyer = buyer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public List<Product> getOfferedProducts() {
        return offeredProducts;
    }

    public void setOfferedProducts(List<Product> offeredProducts) {
        this.offeredProducts = offeredProducts;
    }
}
