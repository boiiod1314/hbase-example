package etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CopyOfEtlTest {

	public static void main(String[] args) throws Exception {

		String filename = "2014080100";
		
		File file = new File("D:/bigdata/01/"+filename+".sql");

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("D:/bigdata/01/"+filename+"_all_column.txt"))));
		
		char[] cbuf = new char[200];
		StringBuilder sb = new StringBuilder();
		
		char[] beforebuf = new char[0];
		int size = -1;
		int linecount = 1;
		while((size=br.read(cbuf))!=-1){
			int length = size;
			if(beforebuf!=null){
				length+=beforebuf.length;
			}
			char[] newbuf = new char[length];
			if(newbuf.length!=length){
				System.arraycopy(beforebuf, 0, newbuf, 0, beforebuf.length);
				
				System.arraycopy(cbuf, 0, newbuf, beforebuf.length, cbuf.length);
			}else{
				newbuf = cbuf;
			}
			
			for(int n=0;n<newbuf.length;n++){
				sb.append(newbuf[n]);
				if(newbuf[n]==(char)')'){
					String str = sb.toString().trim();
					if(str.startsWith("insert")){
						sb.setLength(0);
						continue;
					}
					
					if(str.startsWith("values (")){
						str = str.substring("values (".length(),str.length()-1);
					}
					
					if(str.startsWith(",(")){
						str = str.substring(",(".length(),str.length()-1);
					}
					System.out.println(linecount+" 行");
					
					bw.write(str);
					bw.newLine();
					
					sb.setLength(0);
					linecount++;
					continue;
				}
			}
			
			bw.flush();
			beforebuf = sb.toString().toCharArray();
		}
	
		bw.close();
		br.close();
		
	}
}
