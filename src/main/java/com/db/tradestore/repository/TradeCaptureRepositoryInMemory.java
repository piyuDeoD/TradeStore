package com.db.tradestore.repository;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.db.tradestore.vo.Trade;

@Repository
public class TradeCaptureRepositoryInMemory implements TradeCapturingRepository{
	private static final Map<String, Trade> tradeMap = new ConcurrentHashMap<>();

	@Override
	public void insertNewTrade(Trade trade) {
		String key = trade.getId()+trade.getVersion();
		tradeMap.put(key, trade);
		
	}

	@Override
	public void updateExistingTrade(Trade trade) {
		String key = trade.getId()+trade.getVersion();
		tradeMap.put(key, trade);
	}

	@Override
	public long getLatestVersionOfTrade(String id) {
		List<Trade> trades = tradeMap.values().stream().filter(t -> t.getId().equalsIgnoreCase(id)).collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(trades))
			return trades.stream().max(Comparator.comparing(Trade::getVersion)).get().getVersion();
		return 0;
	}

	@Override
	public Optional<Trade> getExistingTrade(String id, long version) {	
		Optional<Trade> t = Optional.ofNullable(tradeMap.get(id+version));
		return t;
	}

	@Override
	public void expireMaturedTrades() {
		for(Trade t : tradeMap.values()) {
			Date d = new Date();
			Calendar mDate = Calendar.getInstance();
			Calendar cDate = Calendar.getInstance();
			mDate.setTime(t.getMaturityDate());
			cDate.setTime(d);
			
			mDate.set(Calendar.MILLISECOND, 0);
			mDate.set(Calendar.SECOND, 0);
			
			cDate.set(Calendar.MILLISECOND, 0);
			cDate.set(Calendar.SECOND, 0);
			
			if(mDate.compareTo(cDate)==0) {
				t.setExpired(true);
				tradeMap.put(t.getId()+t.getVersion(), t);
			}
			
		}
	}
}
