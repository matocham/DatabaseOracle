package pl.edu.pb.wi.project.database.models.view;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "OFFEREDME", schema = "SYSTEM", catalog = "")
public class OfferedMe {
    private Long id;
    private String ownerLogin;
    private long offerId;
    private String productTitle;
    private String productName;
    private String productImage;
    private String forLogin;
    private long forProductId;
    private Long forOwnerId;
    private String forTitle;
    private String forDescription;
    private String forName;
    private String forExchangeFor;
    private Boolean forExchanged;
    private String forImagePath;

    @javax.persistence.Id
    @Column(name = "ID", nullable = true, precision = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    @Column(name = "OFFER_ID", nullable = false, precision = 0)
    public long getOfferId() {
        return offerId;
    }

    public void setOfferId(long offerId) {
        this.offerId = offerId;
    }

    @Basic
    @Column(name = "PRODUCT_TITLE", nullable = true, length = 100)
    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    @Basic
    @Column(name = "PRODUCT_NAME", nullable = true, length = 100)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "PRODUCT_IMAGE", nullable = true, length = 1000)
    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    @Basic
    @Column(name = "FOR_LOGIN")
    public String getForLogin() {
        return forLogin;
    }

    public void setForLogin(String forLogin) {
        this.forLogin = forLogin;
    }

    @Basic
    @Column(name = "FOR_PRODUCT_ID", nullable = false, precision = 0)
    public long getForProductId() {
        return forProductId;
    }

    public void setForProductId(long forProductId) {
        this.forProductId = forProductId;
    }

    @Basic
    @Column(name = "FOR_OWNER_ID", nullable = true, precision = 0)
    public Long getForOwnerId() {
        return forOwnerId;
    }

    public void setForOwnerId(Long forOwnerId) {
        this.forOwnerId = forOwnerId;
    }

    @Basic
    @Column(name = "FOR_TITLE", nullable = true, length = 100)
    public String getForTitle() {
        return forTitle;
    }

    public void setForTitle(String forTitle) {
        this.forTitle = forTitle;
    }

    @Basic
    @Column(name = "FOR_DESCRIPTION", nullable = true, length = 1000)
    public String getForDescription() {
        return forDescription;
    }

    public void setForDescription(String forDescription) {
        this.forDescription = forDescription;
    }

    @Basic
    @Column(name = "FOR_NAME", nullable = true, length = 100)
    public String getForName() {
        return forName;
    }

    public void setForName(String forName) {
        this.forName = forName;
    }

    @Basic
    @Column(name = "FOR_EXCHANGE_FOR", nullable = true, length = 100)
    public String getForExchangeFor() {
        return forExchangeFor;
    }

    public void setForExchangeFor(String forExchangeFor) {
        this.forExchangeFor = forExchangeFor;
    }

    @Basic
    @Column(name = "FOR_EXCHANGED", nullable = true, precision = 0)
    public Boolean getForExchanged() {
        return forExchanged;
    }

    public void setForExchanged(Boolean forExchanged) {
        this.forExchanged = forExchanged;
    }

    @Basic
    @Column(name = "FOR_IMAGE_PATH", nullable = true, length = 1000)
    public String getForImagePath() {
        return forImagePath;
    }

    public void setForImagePath(String forImagePath) {
        this.forImagePath = forImagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferedMe offeredMe = (OfferedMe) o;
        return offerId == offeredMe.offerId &&
                forProductId == offeredMe.forProductId &&
                Objects.equals(id, offeredMe.id) &&
                Objects.equals(forLogin, offeredMe.forLogin) &&
                Objects.equals(ownerLogin, offeredMe.ownerLogin) &&
                Objects.equals(productTitle, offeredMe.productTitle) &&
                Objects.equals(productName, offeredMe.productName) &&
                Objects.equals(productImage, offeredMe.productImage) &&
                Objects.equals(forOwnerId, offeredMe.forOwnerId) &&
                Objects.equals(forTitle, offeredMe.forTitle) &&
                Objects.equals(forDescription, offeredMe.forDescription) &&
                Objects.equals(forName, offeredMe.forName) &&
                Objects.equals(forExchangeFor, offeredMe.forExchangeFor) &&
                Objects.equals(forExchanged, offeredMe.forExchanged) &&
                Objects.equals(forImagePath, offeredMe.forImagePath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ownerLogin, offerId, productTitle, productName, productImage, forLogin, forProductId, forOwnerId, forTitle, forDescription, forName, forExchangeFor, forExchanged, forImagePath);
    }
}
