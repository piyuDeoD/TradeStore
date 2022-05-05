package com.db.tradestore.mapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import com.db.tradestore.constants.Constants;
import com.db.tradestore.vo.Trade;

@Component
public class TradeMapper {	

	public Trade mapMessageToTrade(String upstreamMessage) throws JSONException, ParseException {
		JSONObject jsonObject = new JSONObject(upstreamMessage);
		Trade trade = new Trade();
		trade.setId(jsonObject.getString("id"));
		trade.setCounterpartyId(jsonObject.getString("counterpartyId"));
		trade.setBookId(jsonObject.getString("bookId"));
		trade.setVersion(jsonObject.getLong("version"));
		trade.setExpired(false);
		trade.setCreatedDate(new Date());
		trade.setMaturityDate(getDate(jsonObject.getString("maturityDate")));
		return trade;
	}
	
	private Date getDate(String dateString) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		return dateFormat.parse(dateString);
	}
}
