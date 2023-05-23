package com.project1.productandcategory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table(name = "products")
public class product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "product_id")
	    private Long product_id;
		@Column(name = "product_name")
	    private String product_name;
		@Column(name = "price")
	    private double price;
		@Column(name = "quantity")
	    private Long quantity;
		@Column(name = "description")
	    private String description;
	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "category_id")
	    @JsonIgnoreProperties("products")
	    private category category;
	    public product() {
	    }
	    public product(String name,double price,Long quantity,String description,category category) {
	        this.product_name = name;
	        this.price=price;
	        this.quantity=quantity;
	        this.description=description;
	        this.category=category;
	    }
	    public Long getProduct_id() {
			return product_id;
		}
		public void setProduct_id(Long product_id) {
			this.product_id = product_id;
		}
		public String getProduct_name() {
			return product_name;
		}
		public void setProduct_name(String product_name) {
			this.product_name = product_name;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public Long getQuantity() {
			return quantity;
		}
		public void setQuantity(Long quantity) {
			this.quantity = quantity;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public category getCategory() {
		        return category;
		  }
		public void setCategory(category category) {
		        this.category = category;
		    }

	    
	

}
