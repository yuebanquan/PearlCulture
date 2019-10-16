package cn.itcast.ssh.domain;

import java.io.Serializable;

public class ValueR implements Serializable{
	private static final long serialVersionUID = -3813690721714793039L;
	//水位监控规则范围值
	private double heightmax = 1.8;  //水位上线   1.5-1.8m
	private double heightmin = 1.5;  //水位下线
	//水质监控规则范围值
	private double tempermax = 35.0;   //水温  
	private double tempermin = 22.0;
	private double valuePHmax = 8.8;  //ph值 
	private double valuePHmin = 8.0;
	private double contentDOmin = 4.0;   //溶解氧含量    >=4mg/l
	private double contentANmax = 0.02;   //氨氮含量       <=0.02mg/l
	private double contentNmax = 0.1;    //亚硝酸盐含量 <=0.1mg/l
	private double contentSmax = 0.1;    //硫化氢含量    <=0.1mg/l
	private double turbiditymax = 40.0;   //浊度              透明度=25-40cm
	private double turbiditymin = 25.0;
	//虾情分析规则范围值	
	private double aveWeigthA = 5.0;//平均体重间值
	private double aveWeigthB = 10.0;	    
	private double aveWeigthC = 15.0;
	private double aveWeigthD = 25.0;    
	private double aveLengthA = 2.0;      //平均体长间值
	private double aveLengthB = 3.0;    
	private double aveLengthC = 10.0;
	private double aveLengthD = 23.0;    
	private double aveMortality = 0.03; //平均死亡率 3%	
	
	public double getHeightmax() {  //水位
		return heightmax;
	}
	public void setHeightmax(double heightmax) {
		this.heightmax = heightmax;
	}
	public double getHeightmin() {
		return heightmin;
	}
	public void setHeightmin(double heightmin) {
		this.heightmin = heightmin;
	}
	
	public double getTempermax() {    //水质
		return tempermax;
	}
	public void setTempermax(double tempermax) {
		this.tempermax = tempermax;
	}
	public double getTempermin() {
		return tempermin;
	}
	public void setTempermin(double tempermin) {
		this.tempermin = tempermin;
	}
	public double getValuePHmax() {
		return valuePHmax;
	}
	public void setValuePHmax(double valuePHmax) {
		this.valuePHmax = valuePHmax;
	}
	public double getValuePHmin() {
		return valuePHmin;
	}
	public void setValuePHmin(double valuePHmin) {
		this.valuePHmin = valuePHmin;
	}
	public double getContentDOmin() {
		return contentDOmin;
	}
	public void setContentDOmin(double contentDOmin) {
		this.contentDOmin = contentDOmin;
	}
	public double getContentANmax() {
		return contentANmax;
	}
	public void setContentANmax(double contentANmax) {
		this.contentANmax = contentANmax;
	}
	public double getContentNmax() {
		return contentNmax;
	}
	public void setContentNmax(double contentNmax) {
		this.contentNmax = contentNmax;
	}
	public double getContentSmax() {
		return contentSmax;
	}
	public void setContentSmax(double contentSmax) {
		this.contentSmax = contentSmax;
	}
	public double getTurbiditymax() {
		return turbiditymax;
	}
	public void setTurbiditymax(double turbiditymax) {
		this.turbiditymax = turbiditymax;
	}
	public double getTurbiditymin() {
		return turbiditymin;
	}
	public void setTurbiditymin(double turbiditymin) {
		this.turbiditymin = turbiditymin;
	}
	
	public double getAveWeigthA() {   //虾情分析
		return aveWeigthA;
	}
	public void setAveWeigthA(double aveWeigthA) {
		this.aveWeigthA = aveWeigthA;
	}
	public double getAveWeigthB() {
		return aveWeigthB;
	}
	public void setAveWeigthB(double aveWeigthB) {
		this.aveWeigthB = aveWeigthB;
	}
	public double getAveWeigthC() {
		return aveWeigthC;
	}
	public void setAveWeigthC(double aveWeigthC) {
		this.aveWeigthC = aveWeigthC;
	}
	public double getAveWeigthD() {
		return aveWeigthD;
	}
	public void setAveWeigthD(double aveWeigthD) {
		this.aveWeigthD = aveWeigthD;
	}
	public double getAveLengthA() {
		return aveLengthA;
	}
	public void setAveLengthA(double aveLengthA) {
		this.aveLengthA = aveLengthA;
	}
	public double getAveLengthB() {
		return aveLengthB;
	}
	public void setAveLengthB(double aveLengthB) {
		this.aveLengthB = aveLengthB;
	}
	public double getAveLengthC() {
		return aveLengthC;
	}
	public void setAveLengthC(double aveLengthC) {
		this.aveLengthC = aveLengthC;
	}
	public double getAveLengthD() {
		return aveLengthD;
	}
	public void setAveLengthD(double aveLengthD) {
		this.aveLengthD = aveLengthD;
	}
	public double getAveMortality() {
		return aveMortality;
	}
	public void setAveMortality(double aveMortality) {
		this.aveMortality = aveMortality;
	}
	
	
}
