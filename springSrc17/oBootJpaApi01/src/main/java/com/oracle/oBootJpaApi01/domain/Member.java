package com.oracle.oBootJpaApi01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "member5")
@Data
@Entity
@SequenceGenerator(
					name = "member_seq_gen5",					
			sequenceName = "member_seq_generator5",
			initialValue = 1,
		  allocationSize = 1
					)

public class Member {
	@Id
	@GeneratedValue(
				strategy = GenerationType.SEQUENCE,
				generator = "member_seq_gen5"
					)
	@Column(name = "member_id")
	private Long id;
	@Column(name = "userName")
	private String name;
	private Long sal;

	// 연관관계
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;
}
