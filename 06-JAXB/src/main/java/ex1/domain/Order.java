package ex1.domain;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Set;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(propOrder = {"id","totalPrice","category","shoppingList","tags","address"})
public class Order {

    @XmlAttribute(name="id")
    private long id;
    private String category;

    @XmlElementWrapper(name = "shopping_list")
    @XmlElement(name = "shopping_item")
    private List<ShoppingItem> shoppingList;

    @XmlElementWrapper(name = "tags")
    @XmlElement(name = "tag")
    private Set<String> tags;

    @XmlElement(name = "addr", required = true)
    private Address address;

    @XmlElement(name = "total_price")
    private float totalPrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ShoppingItem> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<ShoppingItem> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", shoppingList=" + shoppingList +
                ", tags=" + tags +
                ", address=" + address +
                ", totalPrice=" + totalPrice +
                '}';
    }
}