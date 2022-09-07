package com.ezen.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Product {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long P_ID;
	
	@Column(nullable = false, length = 50)
	private String P_TITLE;

	private String CATEGORY1;
	private String CATEGORY2;
	private String CATEGORY3;
	
	private String image1;
	private String image2;
	private String image3;
	private String image4;
	private String image5;
	
	private String REGION;
	
	private String CONDITION;  // 0 중고 , 1 새상품
	
	private String EXCHANGE;   // 0 교환 불가, 1 교환가능
	
	private int PRICE;
	
	private String SHIPPINGPEE; // 0 배송비 없음 , 1 배송비 있음
	
	private String P_EXPLAIN;
	
	private String P_TAG;
	
	private String COUNT;
	
	@Column(insertable=true, updatable=true, columnDefinition="date default current_date")
	private Date P_DATE;
	
	private String email;
	
	private String username;

	private Long likesCount;
}

