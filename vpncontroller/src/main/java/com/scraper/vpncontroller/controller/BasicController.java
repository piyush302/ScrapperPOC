package com.scraper.vpncontroller.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scraper.vpncontroller.dao.ServerRepository;
import com.scraper.vpncontroller.model.CommandResponse;
import com.scraper.vpncontroller.model.Server;
import com.scraper.vpncontroller.service.ConnectionService;

@RestController
public class BasicController {
	@Autowired
	ConnectionService connectionService;
	
	@Autowired
	ServerRepository serverRepository;

	@GetMapping("/connect")
	public CommandResponse connect() {
		return connectionService.connect();
	}
	
	
	@GetMapping("/addserver")
	public void addserver() {
		CommandResponse countries = connectionService.executeCommand("nordvpn countries");
		System.out.println(countries.getResponse());
		countries.setResponse(new StringBuilder(countries.getResponse().toString().replace("New feature - Meshnet! Link remote devices in Meshnet to connect to them directly over encrypted private tunnels, and route your traffic through another device. Use the `nordvpn meshnet --help` command to get started. Learn more: https://nordvpn.com/features/meshnet/", "").replace("-", "").trim()));
		String[] contry = countries.getResponse().toString().split(",");
		List <Server> server = new ArrayList<>();
		for(int i=0;i<contry.length;i++) {
			CommandResponse cities = connectionService.executeCommand("nordvpn cities "+contry[i]);
			cities.setResponse(new StringBuilder(cities.getResponse().toString().replace("New feature - Meshnet! Link remote devices in Meshnet to connect to them directly over encrypted private tunnels, and route your traffic through another device. Use the `nordvpn meshnet --help` command to get started. Learn more: https://nordvpn.com/features/meshnet/", "").replace("-", "").trim()));
			String[] cty = cities.getResponse().toString().split(",");
			for(int j=0;j<cty.length;j++) {
				server.add(new Server(contry[i].trim(), cty[j].trim()));
			}
		}
		serverRepository.saveAll(server);
	}

	
}
