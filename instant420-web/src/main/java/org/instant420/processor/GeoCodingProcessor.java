package org.instant420.processor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;

@Service
public class GeoCodingProcessor implements ApplicationListener<Instant420ApplicationEvent>{

	@Autowired
	private DispensaryService service;
	private static final Log logger = LogFactory.getLog(GeoCodingProcessor.class);
	private int limit=-1;
	public void onApplicationEvent(Instant420ApplicationEvent event) {
		if(event.getEvent().equals(Instant420ApplicationEvent.Event.START_GEO_CODING))
			beginGeoCoding();
	}
	private void beginGeoCoding() {
		List<DispensaryEntity> allDispensaries = service.loadAllDispensaryForCache();
		int counter=0;
		final Geocoder geocoder = new Geocoder();
		for(DispensaryEntity dispensary : allDispensaries){
			if(limit>0 && counter==limit)
				break;
			GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(buildAddress(dispensary)).setLanguage("en").getGeocoderRequest();
			GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
			if(geocoderResponse.getStatus().equals(GeocoderStatus.OK)){
				List<GeocoderResult> results = geocoderResponse.getResults();
				for(GeocoderResult result : results){
					LatLng location = result.getGeometry().getLocation();
					logger.error(dispensary.getName()+"-->"+location.getLat()+"-->"+location.getLat());
					dispensary.setLat(location.getLat());
					dispensary.setLang(location.getLng());
					service.updateEntity(dispensary);
				}
			}
			counter++;
		}
	}
	private String buildAddress(DispensaryEntity dispensary){
		return new StringBuilder().append(dispensary.getStreet()).append(", ").append(dispensary.getCity()).append(", ").append(dispensary.getState()).toString();
	}
	
}
