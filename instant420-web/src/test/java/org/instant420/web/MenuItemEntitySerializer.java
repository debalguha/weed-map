package org.instant420.web;

import java.io.IOException;

import org.progressivelifestyle.weedmap.persistence.domain.MenuItemEntity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MenuItemEntitySerializer extends JsonSerializer<MenuItemEntity> {

	@Override
	public void serialize(MenuItemEntity entity, JsonGenerator jsonGen, SerializerProvider provider) throws IOException, JsonProcessingException {
		
	}

}
