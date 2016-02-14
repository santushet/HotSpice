package com.spice.hot.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Embedded;

/**
 * A OrderDetails.
 */

public class OrderDetails implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Dish dish;

    private Integer quantity;

    public OrderDetails(Dish dish, Integer quantity, BigDecimal total) {
		super();
		this.dish = dish;
		this.quantity = quantity;
		this.total = total;
	}

	public OrderDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	private BigDecimal total;

    
    public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}
	

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

    
   
}
