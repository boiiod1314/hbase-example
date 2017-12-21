package com.netposa.service;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;

public class HbaseService {

	private Connection conn;
	protected Table table;
	private String tableName;
	
	public HbaseService(HbaseConn hc,String tableName){
		try{
			this.tableName = tableName;
			this.conn = hc.getConnection();
			this.table = conn.getTable(TableName.valueOf(tableName));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
