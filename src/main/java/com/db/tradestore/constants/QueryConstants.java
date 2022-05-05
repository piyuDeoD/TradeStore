package com.db.tradestore.constants;

public class QueryConstants {
	
	public static final String INSERT="insert into Trade (id,version,counterPartyId,bookId,maturityDate,createdDate,expired) values (?,?,?,?,?,?,?)";
	public static final String UPDATE="update trade set counterPartyId=?,bookId=?,maturityDate=?,expired=? where id=? and version=?";
	public static final String SELECT="select MAX(version) from Trade where id=?";
    public static final String UPDATE_EXPIRED="update trade set expired=true where maturityDate= cast(getDate() as Date)";
    public static final String EXISTING_COUNT="select count(*) from trade where id=?";
    public static final String GET_EXISTING="select * from Trade where id = ? and version=?";
}
