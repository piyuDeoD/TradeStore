package com.db.tradestore.repository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.db.tradestore.constants.QueryConstants;
import com.db.tradestore.vo.Trade;

public class TradeCaptureReposioryImpl implements TradeCapturingRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeCaptureReposioryImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertNewTrade(Trade trade) {
		LOGGER.info("Creating new record for trade id ,{}",trade.getId());
		jdbcTemplate.update(QueryConstants.INSERT, trade.getId(), trade.getVersion(), trade.getBookId(),
				trade.getMaturityDate(), trade.getCreatedDate(), trade.isExpired());
	}

	@Override
	public void updateExistingTrade(Trade trade) {
		LOGGER.info("Updating trade with id {} and version {}",trade.getId(),trade.getVersion());
		jdbcTemplate.update(QueryConstants.UPDATE, trade.getCounterpartyId(), trade.getBookId(),
				trade.getMaturityDate(), trade.isExpired(), trade.getId(), trade.getVersion());
	}

	@Override
	public long getLatestVersionOfTrade(String id) {
		LOGGER.info("Retreiving latest version for a trade with id {}" , id);
		return jdbcTemplate.queryForObject(QueryConstants.SELECT, new Object[] { id }, new int[] { 12 }, long.class);
	}

	@Override
	public void expireMaturedTrades() {
		LOGGER.info("Expiring all trades with maturity date as today");
		jdbcTemplate.update(QueryConstants.UPDATE_EXPIRED);
	}
	
	@Override
	public Optional<Trade> getExistingTrade(String id, long version) {
		LOGGER.info("Retreiving a trade with id {} and version {}" , id,version);
		return jdbcTemplate.queryForObject(QueryConstants.GET_EXISTING,
				new Object[] { id, version },new int[] {12,-5},
				(rs, rowNum) -> Optional.of(new Trade(rs.getString("id"), rs.getLong("version"),
						rs.getString("counterPartyId"), rs.getString("bookId"), rs.getDate("maturityDate"),
						rs.getDate("createdDate"), rs.getBoolean("isExpired"))));
	}

}
