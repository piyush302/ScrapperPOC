package com.scraper.vpncontroller.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
public class Server {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	public int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Server(int id, String country, String state) {
		super();
		this.id = id;
		this.country = country;
		this.state = state;
	}
	public Server(String country, String state) {
		super();
		this.country = country;
		this.state = state;
	}
	public Server() {
		super();
	}
	public String country;
	public String state;
	@Override
	public String toString() {
		return "Server [id=" + id + ", country=" + country + ", state=" + state + "]";
	}
}
