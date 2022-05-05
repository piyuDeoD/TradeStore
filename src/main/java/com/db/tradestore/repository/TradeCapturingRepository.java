package com.db.tradestore.repository;

import java.util.Optional;

import com.db.tradestore.vo.Trade;

public interface TradeCapturingRepository {
	public void insertNewTrade(Trade trade);
	
	public void updateExistingTrade(Trade trade);
	
	public long getLatestVersionOfTrade(String id);
	
	public Optional<Trade> getExistingTrade(String id,long version);
	
	public void expireMaturedTrades();
}
