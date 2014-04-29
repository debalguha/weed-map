package org.progressivelifestyle.weedmaps.objects;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({ "approved_by_admin", "banned_at", "community_post_uuid", "created_at", "deleted_at", "hits_count","image_content_type", "image_file_name", "image_file_size", "image_updated_at", "name", "picturable_id", "picturable_type", "position", "reported_count", "reviews_count", "updated_at", "user_id" })
public class Picture {
	@JsonProperty("id")
	private Long pictureId;
	@JsonProperty("user_id")
	private Long userId;
	private PictureImage image;
	private Map<String , Object> otherProperties = new HashMap<String , Object>();
	
	public Long getPictureId() {
		return pictureId;
	}

	public Long getUserId() {
		return userId;
	}

    @JsonAnyGetter
    public Map<String , Object> any() {
        return otherProperties;
    }
 
    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }

	public PictureImage getImage() {
		return image;
	}

	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setImage(PictureImage image) {
		this.image = image;
	}
	
}
