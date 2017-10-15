package com.sainsburys.services.plpsummary.service;

import com.sainsburys.services.plpsummary.reader.ProductListingPageSummaryReader;
import com.sainsburys.services.plpsummary.request.ProductListingPageSummaryRequest;
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
public class ProductListingPageSummaryServiceTest {

    @Mock
    private ProductListingPageSummaryReader mockProductListingPageSummaryReader;

    @Mock
    private ProductListingPageSummaryRequest mockProductListingPageSummaryRequest;

    @Mock
    private ProductListingPageSummaryResponse mockListingPageSummaryResponse;

    @InjectMocks
    private ProductListingPageSummaryService productListingPageSummaryService;

    @Test
    public void shouldCallReadProductListingPageGivenValidRequest() throws IOException {

        //Given
        String url = "url";
        Mockito.when(mockProductListingPageSummaryRequest.getUrl()).thenReturn(url);

        //When
        productListingPageSummaryService.createProductListingPageSummary(mockProductListingPageSummaryRequest);

        //Then
        Mockito.verify(mockProductListingPageSummaryReader, Mockito.times(1)).readProductListingPage(url);
    }

    @Test
    public void shouldReturnResponseGivenValidRequest() throws IOException {

        //Given
        String url = "url";
        Mockito.when(mockProductListingPageSummaryRequest.getUrl()).thenReturn(url);
        Mockito.when(mockProductListingPageSummaryReader.readProductListingPage(url)).thenReturn(mockListingPageSummaryResponse);

        //When
        ProductListingPageSummaryResponse response = productListingPageSummaryService.createProductListingPageSummary(mockProductListingPageSummaryRequest);

        //Then
        Assert.assertEquals(response, mockListingPageSummaryResponse);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenNullUrl() throws IOException {

        //Given
        Mockito.when(mockProductListingPageSummaryRequest.getUrl()).thenReturn(null);

        //When
        productListingPageSummaryService.createProductListingPageSummary(mockProductListingPageSummaryRequest);

        //Then
        //throws IllegalArgumentException.
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenEmptyUrl() throws IOException {

        //Given
        Mockito.when(mockProductListingPageSummaryRequest.getUrl()).thenReturn("");

        //When
        productListingPageSummaryService.createProductListingPageSummary(mockProductListingPageSummaryRequest);

        //Then
        //throws IllegalArgumentException.
    }
}
