package com.db.tradestore.vo;

import java.util.Date;

public class Trade {

	private String id;
	private long version;
	private String counterpartyId;
	private String bookId;
	private Date maturityDate;
	private Date createdDate;
	private boolean isExpired;	

	public Trade(String id, long version, String counterpartyId, String bookId, Date maturityDate, Date createdDate,
			boolean isExpired) {
		super();
		this.id = id;
		this.version = version;
		this.counterpartyId = counterpartyId;
		this.bookId = bookId;
		this.maturityDate = maturityDate;
		this.createdDate = createdDate;
		this.isExpired = isExpired;
	}

	public Trade() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getCounterpartyId() {
		return counterpartyId;
	}

	public void setCounterpartyId(String counterpartyId) {
		this.counterpartyId = counterpartyId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	@Override
	public String toString() {
		return "Trade [id=" + id + ", version=" + version + ", counterpartyId=" + counterpartyId + ", bookId=" + bookId
				+ ", maturityDate=" + maturityDate + ", createdDate=" + createdDate + ", isExpired=" + isExpired + "]";
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	
}
