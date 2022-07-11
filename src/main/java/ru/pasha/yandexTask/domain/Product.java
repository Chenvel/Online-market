package ru.pasha.yandexTask.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!Objects.equals(id, product.id)) return false;
        if (!Objects.equals(name, product.name)) return false;
        if (type != product.type) return false;
        if (!Objects.equals(price, product.price)) return false;
        return (Objects.equals(parentId, product.parentId));
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", updateDate=" + updateDate +
                ", parentId=" + parentId +
                '}';
    }
}
