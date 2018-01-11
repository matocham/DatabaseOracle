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


}
