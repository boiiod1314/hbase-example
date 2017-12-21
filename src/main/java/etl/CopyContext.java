package etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CopyContext {

	public static void main(String[] args) throws Exception {
		
		String src = "D:/bigdata/01/2014080218_all_column.txt";
		String desc = "D:/bigdata/01/2014080218_all_column.part1-100.txt";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(src)));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(desc)));
		
		String line = null;
		
		int count = 0;
		
		while((line=br.readLine())!=null){
			if(count==100){
				break;
			}
			bw.write(line);
			bw.newLine();
			count++;
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}
