package cn.itcast.ssh.domain;
import java.io.Serializable;
import java.util.Date;

/**科学投药*/
public class Medicine implements Serializable{
	private static final long serialVersionUID = 7886269154335418839L;

	private String disease;    //疾病
	private String treatment;  //治疗措施
	private Date medicineTime; //投药时间
	
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
	
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
	public Date getMedicineTime() {
		return medicineTime;
	}
	public void setMedicineTime(Date medicineTime) {
		this.medicineTime = medicineTime;
	}
	
}
