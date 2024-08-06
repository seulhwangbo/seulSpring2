package com.oracle.oBootJpa02.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;

@Data
//@Getter
//@Setter
//@ToString
@Table(name ="member2")
@SequenceGenerator(
					name 		   = "member_seq_gen", 		//seq 객체
					sequenceName   = "member_seq_generator",//seq  DB 
					initialValue   = 1,
					allocationSize = 1
				   )

@Entity
public class Member {
	@Id
	@GeneratedValue(
					strategy  = GenerationType.SEQUENCE,
					generator = "member_seq_gen" 
			)
	@Column(name = "member_id" , precision = 10)
	private Long id;
	@Column(name = "user_name" , length = 50)
	private String name;
	private Long sal;
	
	
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;
	@Transient
	private String teamname;
	
}
