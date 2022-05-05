package com.db.tradestore.validator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.tradestore.constants.Constants;
import com.db.tradestore.exception.TradeStoreException;
import com.db.tradestore.repository.TradeCapturingRepository;
import com.db.tradestore.vo.Trade;

@Component
public class TradeValidator {
	
	@Autowired 
	private TradeCapturingRepository tradeCapturingRepository;

	public boolean isValidTrade(Trade trade) throws TradeStoreException {
		if(!isValidMaturityDate(trade.getMaturityDate()))
			return false;
		if(!isValidTradeVersion(trade)) {
			throw new TradeStoreException("Recieved older version of trade ",Constants.INVALID_VERSION);
		}
		return true;		
	}
	
	private boolean isValidMaturityDate(Date date) {
		if(date.compareTo(new Date()) > 0)
			return true;
		return false;		
	}
	
	private boolean isValidTradeVersion(Trade trade) {
		if(tradeCapturingRepository.getLatestVersionOfTrade(trade.getId())>trade.getVersion())
				return false;
		return true;
	}
	
}
