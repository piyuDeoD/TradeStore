package com.db.tradestore.transmission;

import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.db.tradestore.constants.Constants;
import com.db.tradestore.exception.TradeStoreException;
import com.db.tradestore.mapper.TradeMapper;
import com.db.tradestore.service.TradeBookingService;
import com.db.tradestore.validator.TradeValidator;
import com.db.tradestore.vo.Trade;

@Component
public class TradeBookingHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeBookingHandler.class);
	
	@Autowired
	private TradeBookingService tradeBookingService;
	
	@Autowired 
	private TradeMapper tradeMapper;
	
	@Autowired 
	private TradeValidator tradeValidator;
	
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@PostConstruct
	private void initializeConfigs() {
		if(threadPoolTaskExecutor==null) {
			threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
			threadPoolTaskExecutor.setAllowCoreThreadTimeOut(false);
			threadPoolTaskExecutor.setCorePoolSize(10);
			threadPoolTaskExecutor.setMaxPoolSize(30);
			threadPoolTaskExecutor.setKeepAliveSeconds(5);
			threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(false);
			threadPoolTaskExecutor.setThreadNamePrefix("TRADE_STORE_POOL-");
			threadPoolTaskExecutor.afterPropertiesSet();
			threadPoolTaskExecutor.getThreadPoolExecutor().prestartAllCoreThreads();
			
		}
	}

	@PreDestroy
	private void clearConfigs() {
         if(threadPoolTaskExecutor!=null) {
        	 threadPoolTaskExecutor.shutdown();
         }
	}
	
	public void handleMessage(String message) {
		CompletableFuture.runAsync(() -> handleMessageEvent(message));
	}
	
	
	public void handleMessageEvent(String message) {
		try {
			LOGGER.info("Recieved trade from upstream");
			Trade trade =  tradeMapper.mapMessageToTrade(message);
			if(tradeValidator.isValidTrade(trade)) {
				tradeBookingService.startTradeProcessing(trade);
			}else {
				LOGGER.error("Trade message is not a valid message for processing");
			}
		} catch (Exception e) {
			handleFailure(e);
		} 
	}
	
	private void handleFailure(Exception e) {
		if(e instanceof TradeStoreException) {
			if(Constants.INVALID_VERSION.equalsIgnoreCase(((TradeStoreException)e).getErrorCode())) {
				LOGGER.error("Invalid veriosn recieved , sending error notification to upstream");
				//send an email to upstream
			}
		}else {
			// handle other exceptions
		}
	}
}
