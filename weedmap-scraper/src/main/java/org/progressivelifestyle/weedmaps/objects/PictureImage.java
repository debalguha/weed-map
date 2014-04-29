package org.progressivelifestyle.weedmaps.objects;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"leader", "large_pad", "large", "medium", "medium_fill", "medium_pad", "medium_boxed", "medium_oriented", "square", "square_fill", "square_boxed", "small_wide"})
public class PictureImage {
	private String url;
	private Map<String , Object> otherProperties = new HashMap<String , Object>();

	public String getUrl() {
		return url;
	}
	
    @JsonAnyGetter
    public Map<String , Object> any() {
        return otherProperties;
    }
 
    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }

	public void setUrl(String url) {
		this.url = url;
	}
}
