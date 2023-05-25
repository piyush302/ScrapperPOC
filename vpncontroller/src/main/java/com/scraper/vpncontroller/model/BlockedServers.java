package com.scraper.vpncontroller.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class BlockedServers {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	int id;
	String serverNo;
	String state;
	String country;
	public BlockedServers(int id, String serverNo, String state, String country) {
		super();
		this.id = id;
		this.serverNo = serverNo;
		this.state = state;
		this.country = country;
	}
	public BlockedServers(String serverNo, String state, String country) {
		super();
		this.serverNo = serverNo;
		this.state = state;
		this.country = country;
	}
	public BlockedServers() {
		super();
	}
	
}
