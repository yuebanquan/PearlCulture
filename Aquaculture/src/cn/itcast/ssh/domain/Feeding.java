package cn.itcast.ssh.domain;
import java.io.Serializable;
import java.util.Date;
/**科学投喂*/
public class Feeding implements Serializable{
	private static final long serialVersionUID = 5775708481380353028L;
	
	private String weather;         //天气  晴/阴
	private String waterquality;    //水质  好/不好
	private String situationGrowth; //生长情况  良好/不好
	private Date feedTime; //投喂时间
	private String currPeriodBreed;   //此刻属于哪个放养时段
	
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	
	public String getWaterquality() {
		return waterquality;
	}
	public void setWaterquality(String waterquality) {
		this.waterquality = waterquality;
	}
	
	public String getSituationGrowth() {
		return situationGrowth;
	}
	public void setSituationGrowth(String situationGrowth) {
		this.situationGrowth = situationGrowth;
	}
	
	public Date getFeedTime() {
		return feedTime;
	}
	public void setFeedTime(Date feedTime) {
		this.feedTime = feedTime;
	}
	
	public String getCurrPeriodBreed() {
		return currPeriodBreed;
	}
	public void setCurrPeriodBreed(String currPeriodBreed) {
		this.currPeriodBreed = currPeriodBreed;
	}
	
}
