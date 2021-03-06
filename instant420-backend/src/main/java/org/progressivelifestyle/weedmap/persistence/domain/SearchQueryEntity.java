package org.progressivelifestyle.weedmap.persistence.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="searchqueryentity")
@NamedQueries({
	@NamedQuery(
	name = "findSearchText",
	query = "from SearchQueryEntity r where r.queryStr = :queryStr"
	),
	@NamedQuery(
	name = "mostSearchedText",
	query = "from SearchQueryEntity r where r.hasResult = 1 and r.queryStr like :queryStr order by r.count desc"
	)
})
public class SearchQueryEntity extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	private String queryStr;
	private boolean hasResult;
	private long count;
	private String imageURL;
	private String instagramURL;
	private String facebookURL;
	private String twitterURL;
	
	@Column(nullable = true)
	private Integer hitCount;
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private EntityType type;
	
	@Column(nullable = true)
	private Long searchId;
	
	@JsonIgnore
	private Date creationDate;
	@JsonIgnore
	private Date lastUpdateDate;
	
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	public boolean isHasResult() {
		return hasResult;
	}
	public void setHasResult(boolean hasResult) {
		this.hasResult = hasResult;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getInstagramURL() {
		return instagramURL;
	}
	public void setInstagramURL(String instagramURL) {
		this.instagramURL = instagramURL;
	}
	public String getFacebookURL() {
		return facebookURL;
	}
	public void setFacebookURL(String facebookURL) {
		this.facebookURL = facebookURL;
	}
	public String getTwitterURL() {
		return twitterURL;
	}
	public void setTwitterURL(String twitterURL) {
		this.twitterURL = twitterURL;
	}
	public Integer getHitCount() {
		return hitCount;
	}
	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}
	public EntityType getType() {
		return type;
	}
	public void setType(EntityType type) {
		this.type = type;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSearchId() {
		return searchId;
	}
	public void setSearchId(Long searchId) {
		this.searchId = searchId;
	}
	
	
}
