package com.scraper.vpncontroller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CommandResponse {
	@NonNull
	int exitCode;
	@NonNull
	StringBuilder response;
	String message;
	String server;
}
