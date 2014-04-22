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
import org.progressivelifestyle.weedmap.persistence.domain.MenuItemEntity;

public class MenuItemAdministration extends AdministrationConfiguration<MenuItemEntity>{
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        return configurationBuilder
                .nameField("name")
                .singularName("Medicine")
                .pluralName("Medicines").build();
    }

    public ScreenContextConfigurationUnit screenContext(ScreenContextConfigurationUnitBuilder screenContextBuilder) {
        return screenContextBuilder.screenName("Medicine Administration").build();
    }

    public FieldSetConfigurationUnit listView(final FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
        		.field("name").caption("Name")
        		.field("priceEighth").caption("Price 1/8th Gram")
        		.field("priceQuarter").caption("Price 1/4th Gram")
        		.field("priceHalfGram").caption("Price 1/2 Gram")
        		.field("priceGram").caption("Price Gram")
        		.field("priceHalfOunce").caption("Price 1/2 Ounce")
        		.field("priceOunce").caption("Price Ounce")
        		.field("priceUnit").caption("Price Unit")
        		.field("dispensaryId").caption("Dispensary ID")
        		.field("menuItemCategoryId").caption("Category ID")
        		.field("pictureURL").caption("Picture URL").build();
    }

    public FieldSetConfigurationUnit quickView(final FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
        		.field("name").caption("Name")
        		.field("priceEighth").caption("Price 1/8th Gram")
        		.field("priceQuarter").caption("Price 1/4th Gram")
        		.field("priceHalfGram").caption("Price 1/2 Gram")
        		.field("priceGram").caption("Price Gram")
        		.field("priceHalfOunce").caption("Price 1/2 Ounce")
        		.field("priceOunce").caption("Price Ounce")
        		.field("priceUnit").caption("Price Unit")
        		.field("dispensaryId").caption("Dispensary ID")
        		.field("menuItemCategoryId").caption("Category ID")        		
        		.field("pictureURL").caption("Picture URL").build();
    }

    public FieldSetConfigurationUnit showView(final FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
        		.field("name").caption("Name")
        		.field("description").caption("Description")
        		.field("priceEighth").caption("Price 1/8th Gram")
        		.field("priceQuarter").caption("Price 1/4th Gram")
        		.field("priceHalfGram").caption("Price 1/2 Gram")
        		.field("priceGram").caption("Price Gram")
        		.field("priceHalfOunce").caption("Price 1/2 Ounce")
        		.field("priceOunce").caption("Price Ounce")
        		.field("priceUnit").caption("Price Unit")
        		.field("dispensaryId").caption("Dispensary ID")
        		.field("menuItemCategoryId").caption("Category ID")        		
        		.field("strainId").caption("Strain ID")
        		.field("pictureURL").caption("Picture URL").build();
    }

    public FieldSetConfigurationUnit formView(final PersistentFieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
        		.field("name").caption("Name")
        		.field("description").caption("Description")
        		.field("priceEighth").caption("Price 1/8th Gram")
        		.field("priceQuarter").caption("Price 1/4th Gram")
        		.field("priceHalfGram").caption("Price 1/2 Gram")
        		.field("priceGram").caption("Price Gram")
        		.field("priceHalfOunce").caption("Price 1/2 Ounce")
        		.field("priceOunce").caption("Price Ounce")
        		.field("priceUnit").caption("Price Unit")
        		.field("dispensaryId").caption("Dispensary ID")
        		.field("menuItemCategoryId").caption("Category ID")        		
        		.field("strainId").caption("Strain ID")
        		.field("pictureURL").caption("Picture URL").build();                
    }

    public ScopesConfigurationUnit scopes(final ScopesConfigurationUnitBuilder scopeBuilder) {
        return scopeBuilder
                .scope("All", all()).defaultScope().build();
    }

    public FiltersConfigurationUnit filters(final FiltersConfigurationUnitBuilder filterBuilder) {
        return filterBuilder.filters(
        		filter().field("name").caption("Name").build(),
        		filter().field("description").caption("Description").build(),
        		filter().field("priceEighth").caption("Price 1/8th Gram").build(),
        		filter().field("priceQuarter").caption("Price 1/4th Gram").build(),
        		filter().field("priceHalfGram").caption("Price 1/2 Gram").build(),
        		filter().field("priceGram").caption("Price Gram").build(),
        		filter().field("priceHalfOunce").caption("Price 1/2 Ounce").build(),
        		filter().field("priceOunce").caption("Price Ounce").build(),
        		filter().field("priceUnit").caption("Price Unit").build(),
        		filter().field("dispensaryId").caption("Dispensary ID").build(),
        		filter().field("menuItemCategoryId").caption("Category ID").build(),
        		filter().field("strainId").caption("Strain ID").build(),
        		filter().field("pictureURL").caption("Picture URL").build()             
        ).build();
    }
}
