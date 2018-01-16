package pl.edu.pb.wi.project.database.models;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;

@Entity
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "finalize_exchange",
                procedureName = "utilities.finalize_exchange",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "offer_id", type = Long.class)
                })
})
public class Offer implements Serializable {

    @JoinColumn(name="ID")
    @Id
    Long id;

    @Column(name="OFFERED_DATE")
    Date offeredDate;

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
