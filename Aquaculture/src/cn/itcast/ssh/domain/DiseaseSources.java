package cn.itcast.ssh.domain;

import java.io.Serializable;
import java.util.Date;

public class DiseaseSources implements Serializable{
	private static final long serialVersionUID = -115711740941474863L;
	private String plankton;    //浮游(微)生物         (病源监测) 
	private Date diseasesourcesTime; //病源监测时间
	
	public String getPlankton() {
		return plankton;
	}
	public void setPlankton(String plankton) {
		this.plankton = plankton;
	}
	
	public Date getDiseasesourcesTime() {
		return diseasesourcesTime;
	}
	public void setDiseasesourcesTime(Date diseasesourcesTime) {
		this.diseasesourcesTime = diseasesourcesTime;
	}

}
