package pl.edu.pb.wi.project.database.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "conversation_heading")
@Entity
public class ConversationView implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    Long id;

    @Column(name = "title")
    String title;
    @Column(name = "image_path")
    String imagePath;
    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id", referencedColumnName = "ID")
    Product productId;
    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "sender", referencedColumnName = "ID")
    Users senderId;
    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "receiver", referencedColumnName = "ID")
    Users receiverId;
    @Column(name = "msg_body")
    String msgBody;
    @Column(name = "send_date")
    Date sendDate;
    @Column(name = "sender_deleted")
    Boolean senderDeleted;
    @Column(name = "receiver_deleted")
    Boolean reveiverDeleted;
    @Column(name = "is_displayed")
    Boolean isDisplayed;

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Users getSenderId() {
        return senderId;
    }

    public void setSenderId(Users senderId) {
        this.senderId = senderId;
    }

    public Users getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Users receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Boolean getSenderDeleted() {
        return senderDeleted;
    }

    public void setSenderDeleted(Boolean senderDeleted) {
        this.senderDeleted = senderDeleted;
    }

    public Boolean getReveiverDeleted() {
        return reveiverDeleted;
    }

    public void setReveiverDeleted(Boolean reveiverDeleted) {
        this.reveiverDeleted = reveiverDeleted;
    }

    public Boolean getDisplayed() {
        return isDisplayed;
    }

    public void setDisplayed(Boolean displayed) {
        isDisplayed = displayed;
    }
}