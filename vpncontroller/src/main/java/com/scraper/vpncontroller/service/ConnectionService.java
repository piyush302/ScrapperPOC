package com.scraper.vpncontroller.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scraper.vpncontroller.dao.BlockedServerRepo;
import com.scraper.vpncontroller.dao.ServerRepository;
import com.scraper.vpncontroller.model.BlockedServers;
import com.scraper.vpncontroller.model.CommandResponse;
import com.scraper.vpncontroller.model.Server;

@Service
public class ConnectionService {

	@Value("${linux.requestconnect}")
	private String linuxConnect;

	@Value("${linux.disconnect}")
	private String linuxDisconnect;

	@Value("${linux.login}")
	private String linuxLogin;

	@Value("${linux.islogin}")
	private String linuxIsLogin;

	@Value("${windows.disconnect}")
	private String windowsDisconnect;

	@Value("${windows.requestconnect}")
	private String windowsConnect;

	@Value("${windows.login}")
	private String windowsLogin;

	@Value("${windows.islogin}")
	private String windowsIsLogin;

	@Autowired
	ServerRepository serverRepository;

	@Autowired
	BlockedServerRepo blockedServerRepo;

	public CommandResponse connect() {
		boolean isLogged = checkLogin();
		System.out.println("isLogged "+isLogged);
		if (!isLogged) {
			CommandResponse loginResponse = executeCommand("login");
			System.out.println("loginResponse "+loginResponse.getResponse().toString());
			if (loginResponse.getResponse().toString().contains("Welcome to NordVPN")) {
				System.out.println("Login Successful");
			} else {
				System.out.println("Login Failed");
				return new CommandResponse(1, new StringBuilder("Login Failed"));
			}
		}
		Server serverCity = getServerToConnect();
		CommandResponse commandResponse = executeCommand("requestconnect" + serverCity.getState());
		commandResponse = addServerToResponse(commandResponse);
		while (checkBlockeServer(commandResponse.getServer())) {
			serverCity = getServerToConnect();
			commandResponse = executeCommand("requestconnect" + serverCity.getState());
			commandResponse = addServerToResponse(commandResponse);
		}
		if (commandResponse.getResponse().toString().contains("failed")) {
			return new CommandResponse(2, new StringBuilder("Connection Failed"));
		}
		System.out.println("Saving blocked Server");
		blockedServerRepo
				.save(new BlockedServers(commandResponse.getServer(), serverCity.getState(), serverCity.getCountry()));
		System.out.println("blocked Server saved");
		commandResponse.setMessage("Connection Established");
		return commandResponse;
	}

	private boolean checkBlockeServer(String server) {
		List<BlockedServers> blockedServers = blockedServerRepo.findAll();
		for (int i = 0; i < blockedServers.size(); i++) {
			if (blockedServers.get(i).getServerNo().equals(server)) {
				return true;
			}
		}
		return false;
	}

	private CommandResponse addServerToResponse(CommandResponse commandResponse) {
		StringBuilder response = commandResponse.getResponse();
		int startIndex = response.indexOf("(");
		int endIndex = response.indexOf(")");
		String output = response.substring(startIndex + 1, endIndex);
		commandResponse.setServer(output.substring(0, output.indexOf(".")));
		return commandResponse;
	}

	// choose city from 85 cities at random
	private Server getServerToConnect() {
		System.out.println("Inside getServerToConnect");
		Random random = new Random();
		int randomCity = random.nextInt(85) + 1;
		System.out.println("randomCity "+randomCity);
		Optional<Server> city = serverRepository.findById(randomCity);
		System.out.println("city - "+city.get());
		return city.get();
	}

	private boolean checkLogin() {
		System.out.println("Inside checkLogin");
		CommandResponse commandResponse = executeCommand("islogin");
		System.out.println("Response isLogin login "+commandResponse.getResponse().toString()+"--------------");
		if (commandResponse.getResponse().toString().contains("already")) {
			System.out.println("Already Logged In");
			return true;
		}
		return false;
	}

	public CommandResponse executeCommand(String command) {
//		CommandResponse commandResponse = new CommandResponse();
		int exitVal = 1;
		StringBuilder output = new StringBuilder();
		try {
			boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
			ProcessBuilder builder = new ProcessBuilder();
			String cmd = command;
			if (!(command.contains("cities") || command.contains("countries"))) {
				System.out.println("Inside getCommands 1");
				cmd = getCommands(isWindows, command);
			}
			System.out.println("Inside executeCommand");
			System.out.println("cmd - "+cmd);
			if (isWindows) {
				builder.command("cmd.exe", "/c", cmd);
			} else {
				builder.command("sh", "-c", cmd);
			}
			builder.directory(new File(System.getProperty("user.home")));
			Process process = builder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println("**************************** The Output is ******************************");
				System.out.println(output);
				System.out.println("**************************** Output Ends ******************************");
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new CommandResponse(exitVal, output);
	}

	private String getCommands(boolean isWindows, String command) {
		System.out.println("Inside getCommands"+isWindows+" "+command);
		if (isWindows) {
			if (command.contains("requestconnect")) {
				return windowsConnect + " " + command.replace("requestconnect", "");
			} else if (command.equals("disconnect")) {
				return windowsDisconnect;
			} else if (command.equals("login")) {
				return windowsLogin;
			} else if (command.equals("islogin")) {
				return windowsIsLogin;
			}
		} else {
			System.out.println("Inside getCommands 2");
			if (command.contains("requestconnect")) {
				return linuxConnect + " " + command.replace("requestconnect", "");
			} else if (command.equals("disconnect")) {
				return linuxDisconnect;
			} else if (command.equals("login")) {
				return linuxLogin;
			} else if (command.equals("islogin")) {
				System.out.println("linuxIsLogin command -"+linuxIsLogin);
				return linuxIsLogin;
			}

		}
		return "";
	}

}
