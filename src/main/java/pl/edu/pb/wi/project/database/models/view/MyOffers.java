package pl.edu.pb.wi.project.database.models.view;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "MYOFFERS", schema = "SYSTEM", catalog = "")
public class MyOffers {
    private Long id;
    private String buyerLogin;
    private String ownerLogin;
    private String exchangeFor;
    private Time offeredDate;
    private Time exchangeDate;
    private Long rate;
    private String name;
    private String title;
    private Time addDate;
    private String description;
    private Boolean exchanged;
    private String imagePath;

    @javax.persistence.Id
    @Basic
    @Column(name = "ID", nullable = true, precision = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "BUYER_LOGIN", nullable = true, length = 50)
    public String getBuyerLogin() {
        return buyerLogin;
    }

    public void setBuyerLogin(String buyerLogin) {
        this.buyerLogin = buyerLogin;
    }

    @Basic
    @Column(name = "OWNER_LOGIN", nullable = true, length = 50)
    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    @Basic
    @Column(name = "EXCHANGE_FOR", nullable = true, length = 100)
    public String getExchangeFor() {
        return exchangeFor;
    }

    public void setExchangeFor(String exchangeFor) {
        this.exchangeFor = exchangeFor;
    }

    @Basic
    @Column(name = "OFFERED_DATE", nullable = false)
    public Time getOfferedDate() {
        return offeredDate;
    }

    public void setOfferedDate(Time offeredDate) {
        this.offeredDate = offeredDate;
    }

    @Basic
    @Column(name = "EXCHANGE_DATE", nullable = true)
    public Time getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Time exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    @Basic
    @Column(name = "RATE", nullable = true, precision = 0)
    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "TITLE", nullable = true, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "ADD_DATE", nullable = false)
    public Time getAddDate() {
        return addDate;
    }

    public void setAddDate(Time addDate) {
        this.addDate = addDate;
    }

    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "EXCHANGED", nullable = true, precision = 0)
    public Boolean getExchanged() {
        return exchanged;
    }

    public void setExchanged(Boolean exchanged) {
        this.exchanged = exchanged;
    }

    @Basic
    @Column(name = "IMAGE_PATH", nullable = true, length = 1000)
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyOffers that = (MyOffers) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(buyerLogin, that.buyerLogin) &&
                Objects.equals(ownerLogin, that.ownerLogin) &&
                Objects.equals(exchangeFor, that.exchangeFor) &&
                Objects.equals(offeredDate, that.offeredDate) &&
                Objects.equals(exchangeDate, that.exchangeDate) &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(name, that.name) &&
                Objects.equals(title, that.title) &&
                Objects.equals(addDate, that.addDate) &&
                Objects.equals(description, that.description) &&
                Objects.equals(exchanged, that.exchanged) &&
                Objects.equals(imagePath, that.imagePath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, buyerLogin, ownerLogin, exchangeFor, offeredDate, exchangeDate, rate, name, title, addDate, description, exchanged, imagePath);
    }
}
