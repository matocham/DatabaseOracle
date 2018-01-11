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
}
