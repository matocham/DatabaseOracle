package pl.edu.pb.wi.project.database.models;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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

    @OneToMany (targetEntity = User.class)
    @JoinColumn (name="BUYER_ID",referencedColumnName = "ID")
    Set<User> buyer;

    @OneToMany (cascade=CascadeType.ALL, targetEntity=Product.class)
    @JoinColumn(name="PRODUCT_ID")
    Set<Product> productId;

    @Column(name="RATE")
    Integer rate;

}
