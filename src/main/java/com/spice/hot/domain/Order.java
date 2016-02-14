package com.spice.hot.domain;
import java.util.List;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.persistence.Embedded;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Order.
 */

@Document(collection = "order")
public class Order implements Serializable {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("shipping_address")
    private String shippingAddress;

    @Field("shipping")
    private BigDecimal shipping;

    @Field("tax")
    private BigDecimal tax;

    @Field("discount")
    private BigDecimal discount;

    @Field("sub_total")
    private BigDecimal subTotal;

    @Field("order_date")
    private ZonedDateTime orderDate=ZonedDateTime.now();

    @Field("city")
    private String city;

    @Field("state")
    private String state;

    @NotNull
    @Field("total")
    private BigDecimal total;

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderDetails> getItems() {
		return items;
	}

	public void setItems(List<OrderDetails> items) {
		this.items = items;
	}

	@Field("status")
    private String status;
    
	@Embedded
	public User user;

	@Embedded
	public List<OrderDetails> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public BigDecimal getShipping() {
        return shipping;
    }

    public void setShipping(BigDecimal shipping) {
        this.shipping = shipping;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", shippingAddress='" + shippingAddress + "'" +
            ", shipping='" + shipping + "'" +
            ", tax='" + tax + "'" +
            ", discount='" + discount + "'" +
            ", subTotal='" + subTotal + "'" +
            ", orderDate='" + orderDate + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", total='" + total + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
