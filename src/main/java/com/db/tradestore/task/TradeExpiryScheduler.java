package com.db.tradestore.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.db.tradestore.repository.TradeCapturingRepository;

public class TradeExpiryScheduler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeExpiryScheduler.class);
	
	@Autowired
	private TradeCapturingRepository tradeCapturingRepository;
	
	@Scheduled(cron="0/30 * * * * ?")
	public void doTradeExpiry() {
		LOGGER.info("Matured trade expiry scheduler started for date {}",new Date());
		tradeCapturingRepository.expireMaturedTrades();
	}
}
