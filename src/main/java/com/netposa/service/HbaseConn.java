package com.netposa.service;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class HbaseConn {

	private Configuration configuration;

	public HbaseConn() {
		this.configuration = HBaseConfiguration.create();
	}
	
	public Connection getConnection(){
		Connection conn = null;
		try{
			conn = ConnectionFactory.createConnection(configuration);
		}catch (IOException e){
			e.printStackTrace();
		}
		return conn;
	}
	
	public void close(Connection conn){
		if(conn!=null){
			try {
				conn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
