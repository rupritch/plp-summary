package com.sainsburys.services.plpsummary.request;

import org.junit.Assert;
import org.junit.Test;

public class ProductListingPageSummaryRequestTest {

    @Test
    public void shouldSetUrlGivenCorrectValuePassedIntoConstructor() {

        //Given
        String url = "dummyUrl";

        //When
        ProductListingPageSummaryRequest productListingPageSummaryRequest = new ProductListingPageSummaryRequest(url);

        //then
        Assert.assertEquals("List should be equal to url passed in.", url, productListingPageSummaryRequest.getUrl());
    }
}
