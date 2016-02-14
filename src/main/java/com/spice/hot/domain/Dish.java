package com.spice.hot.domain;
import javax.persistence.Embedded;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Dish.
 */

@Document(collection = "dish")
public class Dish implements Serializable {

    @Id
    private String id;

    @NotNull
    @Indexed
    @Field("name")
    private String name;

    @NotNull
    @Field("price")
    private BigDecimal price;

    @Min(value = 0)
    @Field("stock")
    private Integer stock;

    @Field("description")
    @Indexed
    private String description;
    
    @Field("imageUrl")
    private String imageUrl;
    
	@Embedded
    private Category category;

	public Dish(String id,String name,BigDecimal price,Integer stock, String description,Category category){
    	this.id=id;
    	this.name=name;
    	this.description=description;
    	this.stock=stock;
    	this.price=price;
    	this.category=category;
    }
	public Dish() {
		// TODO Auto-generated constructor stub
	}
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Dish{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", price='" + price + "'" +
            ", stock='" + stock + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
