package com.db.tradestore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.db.tradestore.util.TradeStoreTestHelper;
import com.db.tradestore.vo.Trade;

public class TradeCaptureInMemoryTest {
	
	@InjectMocks
	private TradeCaptureRepositoryInMemory tradeCaptureRepositoryInMemory;
	
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testInsertNewTrade() throws Exception {
		Trade trade = TradeStoreTestHelper.getTrade();
		trade.setId("Test1");
		tradeCaptureRepositoryInMemory.insertNewTrade(trade);
		Optional<Trade> existingTrade = tradeCaptureRepositoryInMemory.getExistingTrade(trade.getId(), trade.getVersion()); 
		assertTrue(existingTrade.isPresent());
	}
	
	@Test
	public void testUpdateExistingTrade() throws Exception {
		Trade trade = TradeStoreTestHelper.getTrade();
		trade.setId("Test2");
		tradeCaptureRepositoryInMemory.insertNewTrade(trade);
		Trade trade2 = TradeStoreTestHelper.getTrade();
		trade2.setId("Test2");
		trade2.setCounterpartyId("CP2");
		tradeCaptureRepositoryInMemory.updateExistingTrade(trade2);
		Optional<Trade> updatedTrade = tradeCaptureRepositoryInMemory.getExistingTrade(trade.getId(), trade.getVersion());
		assertTrue(updatedTrade.isPresent());
		assertEquals("CP2", updatedTrade.get().getCounterpartyId());
	}
	
	@Test
	public void testLatestVersionOfTrade() throws Exception {
		Trade trade = TradeStoreTestHelper.getTrade();
		trade.setId("Test3");
		tradeCaptureRepositoryInMemory.insertNewTrade(trade);
		Trade trade2 = TradeStoreTestHelper.getTrade();
		trade2.setId("Test3");
		trade2.setVersion(3);
		tradeCaptureRepositoryInMemory.insertNewTrade(trade2);
		assertEquals(3,tradeCaptureRepositoryInMemory.getLatestVersionOfTrade(trade.getId()));
	}
	
	@Test
	public void testExpireMaturedTrades() throws Exception {
		Trade trade = TradeStoreTestHelper.getTrade();
		trade.setId("test4");
		trade.setMaturityDate(new Date());
		tradeCaptureRepositoryInMemory.insertNewTrade(trade);
		tradeCaptureRepositoryInMemory.expireMaturedTrades();
		Optional<Trade> updatedTrade = tradeCaptureRepositoryInMemory.getExistingTrade(trade.getId(), trade.getVersion());
		assertTrue(updatedTrade.isPresent());
		assertTrue(updatedTrade.get().isExpired());
	}
}
