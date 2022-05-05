package com.db.tradestore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.tradestore.repository.TradeCapturingRepository;
import com.db.tradestore.vo.Trade;

@Service
public class TradeBookingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TradeBookingService.class);

	@Autowired
	private TradeCapturingRepository tradeCapturingRepository;

	public void startTradeProcessing(Trade trade) {
		LOGGER.info("Started processing for trade with Id , {}", trade.getId());
		if (tradeCapturingRepository.getExistingTrade(trade.getId(),trade.getVersion()).isPresent()) {
			LOGGER.info("Trade with id {}  and version {} already exists ; updating ", trade.getId(),
					trade.getVersion());
			tradeCapturingRepository.updateExistingTrade(trade);
		} else {
			LOGGER.info("Creating new trade with id {}", trade.getId());
			tradeCapturingRepository.insertNewTrade(trade);
		}
	}
}
