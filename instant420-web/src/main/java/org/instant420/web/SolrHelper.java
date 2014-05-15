package org.instant420.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.instant420.processor.BoundingBox;
import org.instant420.processor.GeoCodingHelper;
import org.instant420.processor.MapPoint;
import org.instant420.web.domain.SearchType;

public class SolrHelper {
	public static final Log logger = LogFactory.getLog(SolrHelper.class);
	public static SolrDocumentList simpleSearchWithSorting(SolrServer solrServer, String fieldNameToSortOn, int start, int rows, String name, String category, SearchType searchType) throws SolrServerException{
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/select");
		query.setParam(CommonParams.Q, new String[]{"name:"+(name==null?"*":"*"+name+"*")});
		if(searchType.equals(SearchType.MEDICINE))
			query.setFilterQueries("category:".concat(category!=null?category:"*"));
		query.setParam(CommonParams.START, String.valueOf(start));
		query.setParam(CommonParams.ROWS, String.valueOf(rows==0?10:rows));
		query.setParam(CommonParams.WT, "xml");
		query.addSort(fieldNameToSortOn, ORDER.desc);
		logger.info("Query:: "+query);
		return solrServer.query(query).getResults();
	}
	public static SolrDocumentList doSearch(SolrServer solrServer, String region, String searchText, String category, String subCategory, int start, int rows, MapPoint mapPoint, SearchType searchType) throws SolrServerException, UnsupportedEncodingException{
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/select");
		String qParamVal = convertSearchTextToQParamValue(searchText);
		if(mapPoint==null)
			query.setParam(CommonParams.Q, new String[]{qParamVal});
		else{
			BoundingBox box = GeoCodingHelper.GetBoundingBox(mapPoint, 10);
			query.setParam(CommonParams.Q, new String[]{qParamVal});
			if(searchType.equals(SearchType.MEDICINE))
				query.setFilterQueries("lat_coordinate:[".concat(String.valueOf(box.getMinPoint().getLatitude())).concat(" TO ").concat(String.valueOf(box.getMaxPoint().getLatitude()).concat("]")), 
						"lang_coordinate:[".concat(String.valueOf(box.getMinPoint().getLongitude())).concat(" TO ").concat(String.valueOf(box.getMaxPoint().getLongitude()).concat("]")),
						"category:".concat(category!=null?category:"*"), "subCategoryName:".concat(subCategory!=null?subCategory:"*"));
			else
				query.setFilterQueries("lat_coordinate:[".concat(String.valueOf(box.getMinPoint().getLatitude())).concat(" TO ").concat(String.valueOf(box.getMaxPoint().getLatitude()).concat("]")), 
					"lang_coordinate:[".concat(String.valueOf(box.getMinPoint().getLongitude())).concat(" TO ").concat(String.valueOf(box.getMaxPoint().getLongitude()).concat("]")));
		}

		query.setParam(CommonParams.START, String.valueOf(start));
		query.setParam(CommonParams.ROWS, String.valueOf(rows==0?10:rows));
		query.setParam(CommonParams.WT, "xml");
		System.out.println(query.toString());
		QueryResponse response = solrServer.query(query);
		if(response.getResults().isEmpty())
			return doSearchWithRegionForTextSearch(solrServer, region, category, subCategory,searchText, start, rows, searchType);
		return response.getResults();
		
	}
	
	private static String convertSearchTextToQParamValue(String searchText) throws UnsupportedEncodingException {
		if(searchText.contains("\"")){
			return "name:".concat(URLEncoder.encode(searchText.replaceAll("\"", ""), "UTF-8"));
		}
		String []textSplit = searchText.split("\\s");
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<textSplit.length;i++){
			if(i>0)
				builder.append(" AND ");
			builder.append("name:*").append(textSplit[i]).append("*");
		}
		return builder.toString();
	}

	public static SolrDocumentList doSearchWithRegionForTextSearch(SolrServer solrServer, String region, String category, String subCategory, String searchText, int start, int rows, SearchType searchType) throws SolrServerException, UnsupportedEncodingException {
		SolrQuery query = new SolrQuery();
		String qParamVal = convertSearchTextToQParamValue(searchText);
		query.setRequestHandler("/select");
		query.setParam(CommonParams.Q, new String[]{qParamVal});
		if(searchType.equals(SearchType.MEDICINE))
			query.setFilterQueries("region:".concat(region), "category:".concat(category!=null?category:"*"), "subCategoryName:".concat(subCategory!=null?subCategory:"*"));
		else
			query.setFilterQueries("region:".concat("*").concat(region).concat("*"));
		query.setParam(CommonParams.START, String.valueOf(start));
		query.setParam(CommonParams.ROWS, String.valueOf(rows==0?10:rows));
		query.setParam(CommonParams.WT, "xml");
		query.setParam(CommonParams.START, String.valueOf(start));
		return solrServer.query(query).getResults();
	}
	
	public static SolrDocumentList doSearch(SolrServer solrServer, String fieldName, String region, Collection<String> searchTexts, int start, int rows, MapPoint mapPoint) throws SolrServerException{
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/select");
		String searchParam = convertSearhTextsToString(fieldName,searchTexts);
		if(mapPoint==null)
			query.setParam(CommonParams.Q, new String[]{searchParam});
		else{
			BoundingBox box = GeoCodingHelper.GetBoundingBox(mapPoint, 10);
			query.setParam(CommonParams.Q, new String[]{searchParam});
			query.setFilterQueries("lat_coordinate:[".concat(String.valueOf(box.getMinPoint().getLatitude())).concat(" TO ").concat(String.valueOf(box.getMaxPoint().getLatitude()).concat("]")), 
					"lang_coordinate:[".concat(String.valueOf(box.getMinPoint().getLongitude())).concat(" TO ").concat(String.valueOf(box.getMaxPoint().getLongitude()).concat("]")));

		}
		query.setParam(CommonParams.START, String.valueOf(start));
		query.setParam(CommonParams.ROWS, String.valueOf(rows==0?10:rows));
		query.setParam(CommonParams.WT, "xml");
		query.setParam(CommonParams.START, String.valueOf(start));
		logger.info("Query with lat/long: "+query.toString());
		QueryResponse response = solrServer.query(query);
		if(response.getResults().isEmpty())
			return doSearchWithRegion(solrServer, fieldName, region, searchParam, start, rows);
		return response.getResults();
		
	}
	
	public static String convertSearhTextsToString(String fieldName, Collection<String> searchTexts) {
		StringBuilder builder = new StringBuilder();
		Iterator<String> iterator = searchTexts.iterator();
		for(int i=0;i<searchTexts.size();i++){
			if(i!=0)
				builder.append(" OR ");
			builder.append(fieldName).append(":").append(iterator.next());
			
		}
		return builder.toString();
	}

	public static SolrDocumentList doSearchWithRegion(SolrServer solrServer, String fieldName, String region, String searchText, int start, int rows) throws SolrServerException {
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/select");
		query.setParam(CommonParams.Q, new String[]{searchText});
		query.setFilterQueries("region:".concat(region));
		query.setParam(CommonParams.START, String.valueOf(start));
		query.setParam(CommonParams.ROWS, String.valueOf(rows==0?10:rows));
		query.setParam(CommonParams.WT, "xml");
		query.setParam(CommonParams.START, String.valueOf(start));
		logger.info("Query with region: "+query.toString());
		return solrServer.query(query).getResults();
	}
}
