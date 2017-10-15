package com.sainsburys.services.plpsummary.reader;

import com.sainsburys.services.plpsummary.response.ProductListingPageSummaryResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class ProductListingPageSummaryHtmlReaderTest {

    @Mock
    private ProductListingPageSummaryResponse mockProductListingPageSummaryResponse;

    @InjectMocks
    private ProductListingPageSummaryHtmlReader productListingPageSummaryHtmlReader;

    private static final String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    @Test
    public void shouldReturnResponseObjectGivenValidUrl() throws IOException {

        //Given
        //Valid Url

        //When
        ProductListingPageSummaryResponse response = productListingPageSummaryHtmlReader.readProductListingPage(URL);

        //Then
        Assert.assertNotNull(response);
        Assert.assertEquals(mockProductListingPageSummaryResponse, response);
    }

    @Test
    public void shouldSetProductListOnResponseGivenValidUrl() throws IOException {

        //Given
        //Valid Url

        //When
        productListingPageSummaryHtmlReader.readProductListingPage(URL);

        //Then
        Mockito.verify(mockProductListingPageSummaryResponse, Mockito.times(1)).setProductList(Mockito.anyList());
    }

    @Test
    public void shouldSetTotalOnResponseGivenValidUrl() throws IOException {

        //Given
        //Valid Url

        //When
        productListingPageSummaryHtmlReader.readProductListingPage(URL);

        //Then
        Mockito.verify(mockProductListingPageSummaryResponse, Mockito.times(1)).setTotal(Mockito.any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionGivenInvalidUrl() throws IOException {

        //Given
        String invalidUrl = "INVALID_URL";

        //When
        productListingPageSummaryHtmlReader.readProductListingPage(invalidUrl);

        //Then
        //Throws IllegalArgumentException.
    }
}