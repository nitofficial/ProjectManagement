/**
	 * @author Sriram	
*/
package com.example.demo.utilities;

import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class ProjectUtility {

	public static Query getQueryByKeyValue(Map<String,String> conditionsValuePair)
	{
		Query query = new Query();
		for (Map.Entry<String, String> queries :conditionsValuePair.entrySet())
		query.addCriteria(Criteria.where(queries.getKey()).is(queries.getValue()));
		
		return query;
	}
}
