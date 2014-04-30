package org.progressivelifestyle.weedmap.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="dispensarypictureentity")
public class DispensaryPictureEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	private String pictureURL;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private DispensaryEntity dispensary;
	public String getPictureURL() {
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public Long getId() {
		return id;
	}
	public DispensaryEntity getDispensary() {
		return dispensary;
	}
	public void setDispensary(DispensaryEntity dispensary) {
		this.dispensary = dispensary;
	}
	
}
