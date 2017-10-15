package com.sainsburys.services.plpsummary.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Test;

public class ProductListingPageSummaryServiceModuleTest {

    @Test
    public void shouldRunGuiceConfigWithoutExceptions() {

        //Given
        ProductListingPageSummaryServiceModule productListingPageSummaryServiceModule = new ProductListingPageSummaryServiceModule();

        //When
        Injector injector = Guice.createInjector(productListingPageSummaryServiceModule);

        //Then
        Assert.assertNotNull("A null Injector is not valid", injector);
    }
}
