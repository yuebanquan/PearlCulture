package cn.itcast.ssh.domain;
import java.io.Serializable;
import java.util.Date;
/**实时水位监测*/
public class WaterLevel implements Serializable{

	private static final long serialVersionUID = 8975203803410616735L;
	private double height; //水位高度  1.5-1.8m
	private Date waterLevelTime; //水位监测时间
	
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	
	public Date getWaterLevelTime() {
		return waterLevelTime;
	}
	public void setWaterLevelTime(Date waterLevelTime) {
		this.waterLevelTime = waterLevelTime;
	}
	
	@Override
	public String toString() {
		return "WaterLevelTest [height=" + height + "]";
	}
	
}
