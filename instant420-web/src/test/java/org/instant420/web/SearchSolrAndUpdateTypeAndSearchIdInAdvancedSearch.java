package org.instant420.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.instant420.web.httpclient.PreemptiveHttpClient;
import org.junit.BeforeClass;
import org.junit.Test;
import org.progressivelifestyle.weedmap.persistence.domain.EntityType;

public class SearchSolrAndUpdateTypeAndSearchIdInAdvancedSearch {
	private static SolrServer solrServerForDispensary;
	private static SolrServer solrServerForMedicines;
	private static final Log logger = LogFactory.getLog(SearchSolrAndUpdateTypeAndSearchIdInAdvancedSearch.class);
	@BeforeClass
	public static void setup(){
		solrServerForDispensary = new HttpSolrServer("http://localhost:8080/solr/dispensary", new PreemptiveHttpClient("tomcat", "s3cret", 1000));
		solrServerForMedicines = new HttpSolrServer("http://localhost:8080/solr/medicine", new PreemptiveHttpClient("tomcat", "s3cret", 1000));
	}
	
	@Test
	public void shouldBeAbleToUpdateTypeAndSearchIdInAdvancedSearch() throws Exception{
		String sql = "select id, queryStr from searchqueryentity where hitcount is null";
		String updateQuery = "update searchqueryentity set type = ?, searchId = ?, lastUpdateDate = now(), hasResult=1 where id = ?";
		Connection conn = null;
		try{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/instant420", "instant420", "instant420");
			ResultSet rs = conn.createStatement().executeQuery(sql);
			PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
			while(rs.next()){
				long id = rs.getLong(1);
				String term = rs.getString(2);
				SolrDocument medDoc = searchInSolrIndex(term, solrServerForMedicines);
				SolrDocument dispDoc = searchInSolrIndex(term, solrServerForDispensary);
				float medScore =  medDoc==null?0:(Float)medDoc.getFieldValue("score");
				float dispScore =  dispDoc==null?0:(Float)dispDoc.getFieldValue("score");
				if(medScore == 0 && dispScore == 0)
					continue;
				EntityType type = null;
				long searchId = -1;
				if(medScore>dispScore){
					type = EntityType.fromName(medDoc.getFieldValue("category").toString());
					searchId = Long.parseLong(medDoc.getFieldValue("id").toString()); 
				}else{
					type = EntityType.DISPENSARY;
					try {
						searchId = Long.parseLong(dispDoc.getFieldValue("id").toString());
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
				try {
					logger.info("Search Term: "+term+", classified to "+type.name()+", with Id: "+id);
				} catch (Exception e) {
					e.printStackTrace();
				}
				preparedStatement.setString(1, type.name());
				preparedStatement.setLong(2, searchId);
				preparedStatement.setLong(3, id);
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			//conn.commit();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			if(conn!=null)
				conn.close();
		}
	}

	private SolrDocument searchInSolrIndex(String searchTerm, SolrServer solrServer) throws SolrServerException {
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/select");
		query.setParam(CommonParams.Q, new String[]{"name:"+searchTerm});
		query.setParam(CommonParams.START, "0");
		query.setParam(CommonParams.ROWS,"10" );
		query.setFields("*", "score");
		query.setParam(CommonParams.WT, "xml");
		SolrDocumentList documentList = solrServer.query(query).getResults();
		if(documentList.getNumFound()>0)
			return documentList.get(0);
		return null;
	}
}
