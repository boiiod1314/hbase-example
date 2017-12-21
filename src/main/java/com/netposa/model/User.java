package com.netposa.model;

public class User {

	private String rowkey;
	private String name;
	private String age;
	private String sex;
	private String height;
	
	public String getRowkey() {
		return rowkey;
	}
	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	
	@Override
	public String toString() {
		return "User [rowkey=" + rowkey + ", name=" + name + ", age=" + age
				+ ", sex=" + sex + ", height=" + height + "]";
	}
	//rowkey=0000000000000001|19	age=76	height=127	name=zhang80	sex=女
	public void parse(String line){
		if(line!=null){
			String[] kvarr = line.split("\t");
			for(String kvstr:kvarr){
				String[] kv = kvstr.split("=");
				if(kv.length==2){
					String k = kv[0].trim();
					String v = kv[1].trim();
					if("rowkey".equalsIgnoreCase(k)){
						this.setRowkey(v);
					}
					if("name".equalsIgnoreCase(k)){
						this.setName(v);
					}
					if("age".equalsIgnoreCase(k)){
						this.setAge(v);
					}
					if("height".equalsIgnoreCase(k)){
						this.setHeight(v);
					}
					if("sex".equalsIgnoreCase(k)){
						this.setSex(v);
					}
				}
				
			}
		}
	}
}
