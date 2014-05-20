package org.instant420.web;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.instant420.web.httpclient.PreemptiveHttpClient;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

public class SearchSolrAndUpdatePictureInMedicineTest {
	//private static SolrServer solrServerForDispensary;
	private static SolrServer solrServerForMedicines;
	
	@BeforeClass
	public static void setup(){
		//solrServerForDispensary = new HttpSolrServer("http://localhost:8080/solr/dispensary");
		//solrServerForMedicines = new HttpSolrServer("http://localhost:8080/solr/medicine", new PreemptiveHttpClient("tomcat", "s3cret", 1000));
		solrServerForMedicines = new HttpSolrServer("http://server651.spikecloud.net.in:8080/solr/medicine", new PreemptiveHttpClient("instant420-admin", "admin@123", 1000));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void shouldBeAbleToUpdateMedicinesWithPicturesFromDropBox() throws Exception{
		List<String> lines = FileUtils.readLines(new File("C:/DebWork/oDesk/instant420/dropbox-urls.txt"));
		System.out.println("Total "+lines.size()+" images");
		List<String> updateStatements = Lists.newArrayList();
		for(String line : lines){
			String searchTermWithExt = line.substring(line.lastIndexOf('/')+1).trim();
			String searchTerm = searchTermWithExt.substring(0, searchTermWithExt.indexOf('.'));
			SolrQuery query = new SolrQuery();
			query.setRequestHandler("/select");
			query.setParam(CommonParams.Q, new String[]{"name:"+searchTerm});
			query.setParam(CommonParams.START, "0");
			query.setParam(CommonParams.ROWS,"100" );
			query.setFields("*", "score");
			query.setParam(CommonParams.WT, "xml");
			SolrDocumentList documentList = solrServerForMedicines.query(query).getResults();
			if(documentList.getNumFound()>0){
				for(SolrDocument doc : documentList){
					Float score = (Float)doc.getFieldValue("score");;
					if(score > 1){
						updateStatements.add(createPictureURLUpdateStatements(Integer.parseInt(doc.getFieldValue("id").toString()), line));
					}
				}
			}
		}
		System.out.println("Listing update statements..."+updateStatements.size());
		for(String stmt : updateStatements)
			System.out.println(stmt);
	}

	private String createPictureURLUpdateStatements(Integer fieldValue, String line) {
		return new StringBuilder().append("update menuitementity set lastUpdateDate=now(), pictureURL='").append(line).append("'")
				.append(" where id=").append(fieldValue).append(";").toString();
	}
	
}
