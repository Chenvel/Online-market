package ru.pasha.yandexTask.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    @Type(type = "uuid-char")
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Types type;

    @Getter(AccessLevel.NONE)
    private Integer price;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Type(type = "date")
    private Date updateDate;

    @Type(type = "uuid-char")
    @Column(name = "parant_link")
    private UUID parentId;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Product parent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    private List<Product> children = new ArrayList<>();

    public int getPrice() {
        if (this.type == Types.OFFER) {
            return Objects.requireNonNullElse(this.price, -1);
        } else {
            if (this.price == null) {
                this.price = 0;
                return 0;
            }

            return this.price;
        }
    }

    public void update(Product newProduct) {
        // Update product

        this.name = newProduct.getName();
        this.type = newProduct.getType();
        this.price = newProduct.getPrice();
        this.updateDate = newProduct.getUpdateDate();
        this.parentId = newProduct.getParentId();

        if (this.parent != null) {
            this.parent.getChildren().remove(this);
            this.parent.setPrice((int) this.parent.averagePrice(0, 0));
            System.out.println("Category price: " + this.parent.averagePrice(0 ,0));
        }

        this.parent = newProduct.getParent();

        if (this.parent != null) {
            this.parent.getChildren().add(this);
        }
    }

    public int countChild() {
        if (this.children.size() == 0) return 0;

        int count = 0;
        for (Product child: children) {
            if (child.type == Types.OFFER) {
                ++count;
            } else {
                count += child.countChild();
            }
        }
        return count;
    }

    public double averagePrice(double offerPrice, int extraCount) {
        double sum = 0;
        double count = 0;
        if (children.size() == 0) return 0;

        for (Product child: children) {
            if (child.type == Types.CATEGORY) {
                sum += child.averagePrice(0, 0) * child.countChild();
                count += child.countChild();
            } else {
                sum += child.price;
                count++;
            }
        }

        if (count == 0) return 0;

        double avgPrice = (sum + offerPrice) / (count + extraCount);
        this.price = (int) avgPrice;

        return avgPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                '}';
    }
}
