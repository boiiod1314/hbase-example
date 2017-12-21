package com.netposa.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.hadoop.hbase.client.coprocessor.Batch;
import org.apache.hadoop.hbase.ipc.BlockingRpcCallback;
import org.apache.hadoop.hbase.ipc.ServerRpcController;

import com.google.protobuf.ByteString;
import com.netposa.coprocessor.ServerHelloworld.HelloWorld;
import com.netposa.coprocessor.ServerHelloworld.LineRequest;
import com.netposa.coprocessor.ServerHelloworld.LineResponse;
import com.netposa.model.User;


public class HelloworldService extends HbaseService{

	public HelloworldService(HbaseConn hc, String tableName) {
		super(hc, tableName);
	}

	
	public List<User> queryByCondition(String line) throws Throwable{
		List<User> result = new ArrayList<User>();
		final LineRequest request = LineRequest.newBuilder()
				.setLine(ByteString.copyFromUtf8(line))
				.setTableName(ByteString.copyFromUtf8(this.getTableName()))
				.build();
		 
		Map<byte[], LineResponse> results = table.coprocessorService(HelloWorld.class, null, null,new Batch.Call<HelloWorld, LineResponse>() {

			public LineResponse call(HelloWorld instance) throws IOException {
				
				ServerRpcController controller = new ServerRpcController();
				
				BlockingRpcCallback<LineResponse> rpccall = new BlockingRpcCallback<LineResponse>();
				
				instance.requstByLine(controller, request, rpccall);
				return rpccall.get();
			}
		});
		
		Set<Entry<byte[], LineResponse>> entrySet = results.entrySet();
		for(Map.Entry<byte[], LineResponse> entry : entrySet){
			LineResponse value = entry.getValue();
			String resultstr = value.getResult().toStringUtf8().trim();
			if(resultstr!=null && resultstr.length()>0){
				String[] userarr = resultstr.split("\n");
				for(String userstr:userarr){
					if(userstr.trim().length()>0){
						User user = new User();
						user.parse(userstr);
						result.add(user);
					}
				}
			}

		}
		
		return result;
	}
}
