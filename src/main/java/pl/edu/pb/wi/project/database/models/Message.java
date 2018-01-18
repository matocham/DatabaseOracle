package pl.edu.pb.wi.project.database.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Message implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    Long Id;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "SENDER_ID", referencedColumnName = "ID")
    Users senderId;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "ID")
    Users receiverId;

    @Column (name = "MSG_BODY")
    String msgBody;

    @Column(name = "IS_DISPLAYED")
    Boolean isDisplayed;

    @Column(name = "SEND_DATE")
    Date sendDate;

    @JoinColumn (name = "CONVERSTATION",referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    Conversation conversation;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public Boolean getDisplayed() {
        return isDisplayed;
    }

    public void setDisplayed(Boolean displayed) {
        isDisplayed = displayed;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
