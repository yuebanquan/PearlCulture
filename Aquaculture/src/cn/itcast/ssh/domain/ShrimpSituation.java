package cn.itcast.ssh.domain;
import java.io.Serializable;
import java.util.Date;

/**日常虾情分析*/
public class ShrimpSituation implements Serializable{
	private static final long serialVersionUID = 1978240315146508841L;

	private double averageWeigth;    //平均体重
	private double averageLength;    //平均体长
	private double averageMortality; //平均死亡率		
	
	private String colorBody;        //体色
	private String behaviorBody;     //行为                                     /*对虾身体特征 === 虾疾病症状*/
	private String surfaceBody;      //体表
	private String situationFeed;    //摄食情况
	
	private Date shrimpSituationTime; //虾情分析时间
	private String currPeriodBreed;   //此刻属于哪个放养时段
	
	public double getAverageWeigth() {
		return averageWeigth;
	}
	public void setAverageWeigth(double averageWeigth) {
		this.averageWeigth = averageWeigth;
	}
	
	public double getAverageLength() {
		return averageLength;
	}
	
	public void setAverageLength(double averageLength) {
		this.averageLength = averageLength;
	}
	
	public double getAverageMortality() {
		return averageMortality;
	}
	public void setAverageMortality(double averageMortality) {
		this.averageMortality = averageMortality;
	}
	
	public String getColorBody() {
		return colorBody;
	}
	public void setColorBody(String colorBody) {
		this.colorBody = colorBody;
	}
	
	public String getBehaviorBody() {
		return behaviorBody;
	}
	public void setBehaviorBody(String behaviorBody) {
		this.behaviorBody = behaviorBody;
	}
	
	public String getSurfaceBody() {
		return surfaceBody;
	}
	public void setSurfaceBody(String surfaceBody) {
		this.surfaceBody = surfaceBody;
	}
	
	public String getSituationFeed() {
		return situationFeed;
	}
	public void setSituationFeed(String situationFeed) {
		this.situationFeed = situationFeed;
	}
	
	public Date getShrimpSituationTime() {
		return shrimpSituationTime;
	}
	public void setShrimpSituationTime(Date shrimpSituationTime) {
		this.shrimpSituationTime = shrimpSituationTime;
	}

	public String getCurrPeriodBreed() {
		return currPeriodBreed;
	}
	public void setCurrPeriodBreed(String currPeriodBreed) {
		this.currPeriodBreed = currPeriodBreed;
	}
}
