package pl.edu.pb.wi.project.database.models;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Message implements Serializable {
    @Id
    @Column(name = "ID")
    Long Id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "SENDER_ID", referencedColumnName = "ID")
    User  senderId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "ID")
    User receiverId;

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

    public User getSenderId() {
        return senderId;
    }

    public void setSenderId(User senderId) {
        this.senderId = senderId;
    }

    public User getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(User receiverId) {
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
