package pl.edu.pb.wi.project.database.models;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class Conversation implements Serializable {
    @Id
    @Column(name = "ID")
    Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "INIT_SENDER",referencedColumnName = "ID")
    User initSender;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "INIT_RECIVER",referencedColumnName = "ID")
    User initReciver;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "PRODUCT_ID",referencedColumnName = "ID")
    Product productId;

    @Column(name = "SENDER_DELATED")
    Boolean senderDelated;

    @Column(name = "RECEIVER_DELATED")
    Boolean receiverDelated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getInitSender() {
        return initSender;
    }

    public void setInitSender(User initSender) {
        this.initSender = initSender;
    }

    public User getInitReciver() {
        return initReciver;
    }

    public void setInitReciver(User initReciver) {
        this.initReciver = initReciver;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Boolean getSenderDelated() {
        return senderDelated;
    }

    public void setSenderDelated(Boolean senderDelated) {
        this.senderDelated = senderDelated;
    }

    public Boolean getReceiverDelated() {
        return receiverDelated;
    }

    public void setReceiverDelated(Boolean receiverDelated) {
        this.receiverDelated = receiverDelated;
    }
}
