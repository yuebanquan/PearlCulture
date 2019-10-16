package cn.itcast.ssh.domain;
import java.io.Serializable;
import java.util.Date;

/**巡塘管理*/
public class PondInspection implements Serializable{
	private static final long serialVersionUID = -8222185695337488710L;
	private String situationPond;   //池塘情况    异常/正常
	private String measure;			//相应处理措施
	private Date pondInspectionTime; //巡塘时间
	
	
	public String getSituationPond() {
		return situationPond;
	}
	public void setSituationPond(String situationPond) {
		this.situationPond = situationPond;
	}
	
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	
	public Date getPondInspectionTime() {
		return pondInspectionTime;
	}
	public void setPondInspectionTime(Date pondInspectionTime) {
		this.pondInspectionTime = pondInspectionTime;
	}
	
}
