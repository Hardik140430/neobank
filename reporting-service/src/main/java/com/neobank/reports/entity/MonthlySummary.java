package com.neobank.reports.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "monthly_summaries", uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "year" ,"month"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlySummary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "account_id")
	private String accountId;
	
	private Integer year;
	private Integer month;
	private BigDecimal totalCredit;
	private BigDecimal totalDebit;
	private BigDecimal openingBalance;
	private BigDecimal closingBalance;
	private Long transactionsCount;
	
	
}
