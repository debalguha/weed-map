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
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;

public class DispensaryAdministration extends AdministrationConfiguration<DispensaryEntity>{
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        return configurationBuilder
                .nameField("name")
                .singularName("Dispensary")
                .pluralName("Dispensaries").build();
    }

    public ScreenContextConfigurationUnit screenContext(ScreenContextConfigurationUnitBuilder screenContextBuilder) {
        return screenContextBuilder.screenName("Dispensary Administration").build();
    }

    public FieldSetConfigurationUnit listView(final FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
        		.field("name").caption("Name")
        		.field("phone").caption("Phone")
        		.field("email").caption("Email Address")
        		.field("website").caption("Website")
        		.field("street").caption("Street")
        		.field("city").caption("City")
        		.field("state").caption("State")
        		.field("zip").caption("Zip")
        		.field("facebookURL").caption("Facebook URL")
        		.field("twitterURL").caption("Twitter URL")
        		.field("instagramURL").caption("Instagram URL").build();
        
    }

    public FieldSetConfigurationUnit quickView(final FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("name").caption("Name")
                .field("phone").caption("Phone")
                .field("email").caption("Email Address")
                .field("website").caption("Website")
                .field("street").caption("Street")
                .field("city").caption("City")
                .field("state").caption("State")
                .field("zip").caption("Zip").build();
    }

    public FieldSetConfigurationUnit showView(final FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("name").caption("Name")
                .field("phone").caption("Phone")
                .field("email").caption("Email Address")
                .field("website").caption("Website")
                .field("street").caption("Street")
                .field("city").caption("City")
                .field("state").caption("State")
                .field("zip").caption("Zip")
                .field("facebookURL").caption("Facebook URL")
        		.field("twitterURL").caption("Twitter URL")
        		.field("instagramURL").caption("Instagram URL")
        		.field("creditCardSupport").caption("Credit card Support")
        		.field("handicapSupport").caption("Handicap Support")
        		.field("securityGuardSupport").caption("Security Guard")
        		.field("photoAvailable").caption("Photo available")
        		.field("labTested").caption("Lab tested")
        		.field("forAdult").caption("18+")
        		.field("deliverySupport").caption("Delivery")
        		.field("sundayOpen").caption("Sunday open")
        		.field("sundayClose").caption("Sunday close")
        		.field("mondayOpen").caption("Monday open")
        		.field("mondayClose").caption("Monday close")
        		.field("tuesdayOpen").caption("Tuesday open")
        		.field("tuesdayClose").caption("Tuesday close")
        		.field("wednesdayOpen").caption("Wednesday open")
        		.field("wednesdayClose").caption("Wednesday close")
        		.field("thursdayOpen").caption("Thursday open")
        		.field("thursdayClose").caption("Thursday close")
        		.field("fridayOpen").caption("Friday open")
        		.field("fridayClose").caption("Friday close")
        		.field("saturdayOpen").caption("Saturday open")
        		.field("saturdayClose").caption("Saturday close")
        		.field("dispensaryURL").caption("Dispensary URL").build();
    }

    public FieldSetConfigurationUnit formView(final PersistentFieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("name").caption("Name")
                .field("phone").caption("Phone")
                .field("email").caption("Email Address")
                .field("website").caption("Website")
                .field("street").caption("Street")
                .field("city").caption("City")
                .field("state").caption("State")
                .field("zip").caption("Zip")
        		.field("facebookURL").caption("Facebook URL")
        		.field("twitterURL").caption("Twitter URL")
        		.field("instagramURL").caption("Instagram URL")
        		.field("creditCardSupport").caption("Credit card Support")
        		.field("handicapSupport").caption("Handicap Support")
        		.field("securityGuardSupport").caption("Security Guard")
        		.field("photoAvailable").caption("Photo available")
        		.field("labTested").caption("Lab tested")
        		.field("forAdult").caption("18+")
        		.field("deliverySupport").caption("Delivery")
        		.field("sundayOpen").caption("Sunday open")
        		.field("sundayClose").caption("Sunday close")
        		.field("mondayOpen").caption("Monday open")
        		.field("mondayClose").caption("Monday close")
        		.field("tuesdayOpen").caption("Tuesday open")
        		.field("tuesdayClose").caption("Tuesday close")
        		.field("wednesdayOpen").caption("Wednesday open")
        		.field("wednesdayClose").caption("Wednesday close")
        		.field("thursdayOpen").caption("Thursday open")
        		.field("thursdayClose").caption("Thursday close")
        		.field("fridayOpen").caption("Friday open")
        		.field("fridayClose").caption("Friday close")
        		.field("saturdayOpen").caption("Saturday open")
        		.field("saturdayClose").caption("Saturday close")
        		.field("dispensaryURL").caption("Dispensary URL").build();                
    }

    public ScopesConfigurationUnit scopes(final ScopesConfigurationUnitBuilder scopeBuilder) {
        return scopeBuilder
                .scope("All", all()).defaultScope().build();
    }

    public FiltersConfigurationUnit filters(final FiltersConfigurationUnitBuilder filterBuilder) {
        return filterBuilder.filters(
                filter().field("id").caption("ID").build(),
                filter().field("name").caption("Name").build(),
                filter().field("phone").caption("Phone").build(),
                filter().field("email").caption("Email Address").build(),
                filter().field("website").caption("Website").build(),
                filter().field("street").caption("Street").build(),
                filter().field("city").caption("City").build(),
                filter().field("state").caption("State").build(),
                filter().field("zip").caption("Zip").build(),
                filter().field("facebookURL").caption("Facebook URL").build(),
                filter().field("twitterURL").caption("Twitter URL").build(),
                filter().field("instagramURL").caption("Instagram URL")  .build()              
        ).build();
    }
}
