package cn.itcast.ssh.domain;
import java.io.Serializable;
import java.util.Date;
/**实时水质监测*/
public class WaterQuality implements Serializable{
	private static final long serialVersionUID = -3176648166520152723L;
	
	private double temperature; //水温         22-35
	private double valuePH;     //ph值        7.8-9.2
	private double contentDO;   //溶解氧含量    >=4mg/l
	private double contentAN;   //氨氮含量       <=0.2mg/l
	private double contentN;    //亚硝酸盐含量 <=0.1mg/l
	private double contentS;    //硫化氢含量    <=0.1mg/l
	private double turbidity;   // 透明度       25-40cm
	private Date waterQualityTime; //水质监测时间
	
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	public double getValuePH() {
		return valuePH;
	}
	public void setValuePH(double valuePH) {
		this.valuePH = valuePH;
	}
	
	public double getContentDO() {
		return contentDO;
	}
	public void setContentDO(double contentDO) {
		this.contentDO = contentDO;
	}
	
	public double getContentAN() {
		return contentAN;
	}
	public void setContentAN(double contentAN) {
		this.contentAN = contentAN;
	}
	
	public double getContentN() {
		return contentN;
	}
	public void setContentN(double contentN) {
		this.contentN = contentN;
	}
	
	public double getContentS() {
		return contentS;
	}
	public void setContentS(double contentS) {
		this.contentS = contentS;
	}
	
	public double getTurbidity() {
		return turbidity;
	}
	public void setTurbidity(double turbidity) {
		this.turbidity = turbidity;
	}
	
	public Date getWaterQualityTime() {
		return waterQualityTime;
	}
	public void setWaterQualityTime(Date waterQualityTime) {
		this.waterQualityTime = waterQualityTime;
	}
	
}
