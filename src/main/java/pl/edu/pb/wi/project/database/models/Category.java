package pl.edu.pb.wi.project.database.models;
import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Category implements Serializable {
    @Id
    @JoinColumn(name = "ID")
    Long id;

    @Column(name = "NAME")
    String name;

    @ManyToOne (targetEntity = Category.class)
    @JoinColumn(name = "PARENTCATEGORY",referencedColumnName = "ID")
    Category parentCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
}
