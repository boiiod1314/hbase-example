package etl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class EtlTest02 {

	public static void main(String[] args) throws Exception {
		
		String filename = "2014080100_all_column";
		
		File file = new File("D:/bigdata/01/"+filename+".txt");
		//File file = new File("c:/aa.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("D:/bigdata/01/"+filename+"_filter.txt"),true)));
		
		String line = null;
		int count = 1;
		while((line = br.readLine()) !=null){
			
			System.out.println("开始导第"+count+"行");
			if(line.trim().length()==0){
				System.out.println("第"+count+"行为空跳过");
				continue;
			}
			
			//System.out.println(line);
			//System.exit(-1);
			
			count++;
			
			String[] columns = line.split(",");
			int len = columns.length;
			
			if(len!=27){
				System.out.println("不符合规范：少于27列"+line);
				continue;
			}
			
			//kkbh,hphm,jgsj,cdfx   hphm != '00000000' and hphm != '11111111'and hpys=2 and hpzl != "01";

			String hphm = columns[2].trim();
			String hpys = columns[9].trim();
			String hpzl = columns[3].trim();
			
			String kkbh = columns[0].trim();
			String jgsj = columns[4].trim();
			String cdfx = columns[7].trim();
			String clsd = columns[6].trim();
			// \t 
			//if(!"'00000000'".equals(hphm) && !"'11111111'".equals(hphm) && "'2'".equals(hpys) && !"'01'".equals(hpzl)){  //01
		//	if(!"'00000000'".equals(hphm) && !"'11111111'".equals(hphm) && "'2'".equals(hpys)){  //02
			if("0".equals(clsd)||"-1".equals(clsd)){
				kkbh = kkbh.substring(1,kkbh.length()-1);
				hphm = hphm.substring(1,hphm.length()-1);
				jgsj = jgsj.substring(1,jgsj.length()-1);
				cdfx = cdfx.substring(1,cdfx.length()-1);
				
				String value = kkbh+"\t"+hphm+"\t"+jgsj+"\t"+clsd;
				bw.write(value);
				bw.newLine();
			}
			
		};
		
		bw.close();
		br.close();
		
	}
}
