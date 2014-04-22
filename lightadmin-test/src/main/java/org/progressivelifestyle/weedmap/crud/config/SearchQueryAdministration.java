package org.progressivelifestyle.weedmap.crud.config;

import static org.lightadmin.api.config.utils.FilterMetadataUtils.filter;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.all;

import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.EntityMetadataConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.FieldSetConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.FiltersConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.PersistentFieldSetConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.ScopesConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.ScreenContextConfigurationUnitBuilder;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.api.config.unit.FiltersConfigurationUnit;
import org.lightadmin.api.config.unit.ScopesConfigurationUnit;
import org.lightadmin.api.config.unit.ScreenContextConfigurationUnit;
import org.progressivelifestyle.weedmap.persistence.domain.SearchQueryEntity;

public class SearchQueryAdministration extends AdministrationConfiguration<SearchQueryEntity>{
	   public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
	        return configurationBuilder
	                .nameField("queryStr")
	                .singularName("Search Term")
	                .pluralName("Search Terms").build();
	    }

	    public ScreenContextConfigurationUnit screenContext(ScreenContextConfigurationUnitBuilder screenContextBuilder) {
	        return screenContextBuilder.screenName("Medicine Administration").build();
	    }

	    public FieldSetConfigurationUnit listView(final FieldSetConfigurationUnitBuilder fragmentBuilder) {
	        return fragmentBuilder
	        		.field("queryStr").caption("Search Term")
	        		.field("hasResult").caption("Result Exists")
	        		.field("count").caption("Popularity").build();
	        
	    }

	    public FieldSetConfigurationUnit quickView(final FieldSetConfigurationUnitBuilder fragmentBuilder) {
	        return fragmentBuilder
	        		.field("queryStr").caption("Search Term")
	        		.field("hasResult").caption("Result Exists")
	        		.field("count").caption("Popularity").build();
	    }

	    public FieldSetConfigurationUnit showView(final FieldSetConfigurationUnitBuilder fragmentBuilder) {
	        return fragmentBuilder
	        		.field("queryStr").caption("Search Term")
	        		.field("hasResult").caption("Result Exists")
	        		.field("count").caption("Popularity").build();
	    }

	    public FieldSetConfigurationUnit formView(final PersistentFieldSetConfigurationUnitBuilder fragmentBuilder) {
	        return fragmentBuilder
	        		.field("queryStr").caption("Search Term")
	        		.field("hasResult").caption("Result Exists")
	        		.field("count").caption("Popularity").build();                
	    }

	    public ScopesConfigurationUnit scopes(final ScopesConfigurationUnitBuilder scopeBuilder) {
	        return scopeBuilder
	                .scope("All", all()).defaultScope().build();
	    }

	    public FiltersConfigurationUnit filters(final FiltersConfigurationUnitBuilder filterBuilder) {
	        return filterBuilder.filters(
	        		filter().field("queryStr").caption("Search Term").build(),
	        		filter().field("hasResult").caption("Result Exists").build(),
	        		filter().field("count").caption("Popularity").build()           
	        ).build();
	    }
}
