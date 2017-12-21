package etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class EtlTest {

	public static void main(String[] args) throws Exception {
		
		String filename = "2014080100_1";
		
		File file = new File("D:/bigdata/01/"+filename+".sql");
		//File file = new File("c:/aa.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("D:/bigdata/01/"+filename+"_all_column.txt"),true)));
		
		String line = null;
		String str = "values";
		int count = 1;
		while((line = br.readLine()) !=null){
			System.out.println("开始导第"+count+"行");
			if(line.trim().length()==0){
				System.out.println("第"+count+"行为空跳过");
				continue;
			}
			count++;
			/*System.out.println(line);
			System.exit(-1);*/
			String values = line.substring(line.indexOf(str)+str.length(), line.length());
			String[] strs = values.split("\\),\\(");
			int length = strs.length;
			for(int i=0;i<length;i++){
				String value = strs[i];
				if(i==0){
					value = value.substring(value.indexOf("(")+1);
				}
				if(i==length-1){
					value = value.substring(0, value.lastIndexOf(");"));
				}
				
				bw.write(value);
				bw.newLine();
			}
			bw.flush();
		};
		
		bw.close();
		br.close();
		
	}
}
