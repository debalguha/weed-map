package org.progressivelifestyle.weedmap.persistence.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(
	name = "findSearchText",
	query = "from SearchQueryEntity r where r.queryStr = :queryStr"
	),
	@NamedQuery(
	name = "mostSearchedText",
	query = "from SearchQueryEntity r where r.hasResult = 1 order by r.count desc"
	)
})
public class SearchQueryEntity implements BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	private String queryStr;
	private boolean hasResult;
	private long count;
	
	private Date creationDate;
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
	
	
}