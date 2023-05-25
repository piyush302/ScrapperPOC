package com.scraper.vpncontroller.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scraper.vpncontroller.model.BlockedServers;

public interface BlockedServerRepo extends JpaRepository<BlockedServers, Integer>{

}
