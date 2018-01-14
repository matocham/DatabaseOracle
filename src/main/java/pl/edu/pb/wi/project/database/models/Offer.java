package pl.edu.pb.wi.project.database.models;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
@Entity
public class Offer implements Serializable {

    @JoinColumn(name="ID")
    @Id
    Long id;

    @Column(name="OFFERED_DATE")
    Date ooferedDate;

    @Column(name="EXCHANGE_DATE")
    Date exchangeDate;

    @OneToMany (targetEntity = Users.class)
    @JoinColumn (name="BUYER_ID",referencedColumnName = "ID")
    Set<Users> buyer;

    @OneToMany (cascade=CascadeType.ALL, targetEntity=Product.class)
    @JoinColumn(name="PRODUCT_ID")
    Set<Product> productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOoferedDate() {
        return ooferedDate;
    }

    public void setOoferedDate(Date ooferedDate) {
        this.ooferedDate = ooferedDate;
    }

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public Set<Users> getBuyer() {
        return buyer;
    }

    public void setBuyer(Set<Users> buyer) {
        this.buyer = buyer;
    }

    public Set<Product> getProductId() {
        return productId;
    }

    public void setProductId(Set<Product> productId) {
        this.productId = productId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Column(name="RATE")
    Integer rate;

}
