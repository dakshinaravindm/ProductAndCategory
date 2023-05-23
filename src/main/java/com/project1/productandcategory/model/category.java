package com.project1.productandcategory.model;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "categories")
public class category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    public Long category_id;
    @Column(name = "category_Name")
    public String category_Name;
    @Column(name = "c_description")
    public String c_description;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"categories", "hibernateLazyInitializer"})
    public List<product> products;
    public category() {
    	
    }
    public category(String name,String desc) {
        this.category_Name=name;
        this.c_description=desc;
    }
//    //for testing
//    public category(Long id, String categoryName, String description) {
//        this.category_id = id;
//        this.category_Name = categoryName;
//        this.c_description = description;
//    }

    public String getCategory_Name() {
        return category_Name;
    }

    public void setCategory_Name(String name) {
        this.category_Name = name;
    }

	public String getC_description() {
		return c_description;
	}

	public void setC_description(String c_description) {
		this.c_description = c_description;
	}
	public List<product> getProducts() {
        return products;
    }

    public void setProducts(List<product> products) {
        this.products = products;
    }
}

