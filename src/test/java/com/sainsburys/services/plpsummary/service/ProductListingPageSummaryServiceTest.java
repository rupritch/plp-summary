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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public void shouldCallReadSingleProductListingPageGivenSingleSourceInRequest() throws IOException {

        //Given
        List<String> urlList = new ArrayList<>();
        urlList.add("url");
        Mockito.when(mockProductListingPageSummaryRequest.getSourcesList()).thenReturn(urlList);

        //When
        productListingPageSummaryService.createProductListingPageSummary(mockProductListingPageSummaryRequest);

        //Then
        Mockito.verify(mockProductListingPageSummaryReader, Mockito.times(1)).readSingleProductListingPage(urlList.get(0));
    }

    @Test
    public void shouldCallReadMultipleProductListingPagesGivenMultipleSourcesInRequest() throws IOException {

        //Given
        List<String> urlList = new ArrayList<>();
        urlList.add("url");
        urlList.add("url1");
        Mockito.when(mockProductListingPageSummaryRequest.getSourcesList()).thenReturn(urlList);

        //When
        productListingPageSummaryService.createProductListingPageSummary(mockProductListingPageSummaryRequest);

        //Then
        Mockito.verify(mockProductListingPageSummaryReader, Mockito.times(1)).readMultipleProductListingPages(urlList);
    }

    @Test
    public void shouldReturnResponseGivenValidSingleSourceRequest() throws IOException {

        //Given
        List<String> urlList = new ArrayList<>();
        urlList.add("url");
        Mockito.when(mockProductListingPageSummaryRequest.getSourcesList()).thenReturn(urlList);
        Mockito.when(mockProductListingPageSummaryReader.readSingleProductListingPage("url")).thenReturn(mockListingPageSummaryResponse);

        //When
        ProductListingPageSummaryResponse response = productListingPageSummaryService.createProductListingPageSummary(mockProductListingPageSummaryRequest);

        //Then
        Assert.assertEquals(response, mockListingPageSummaryResponse);
    }

    @Test
    public void shouldReturnResponseGivenValidMultipleSourceRequest() throws IOException {

        //Given
        List<String> urlList = new ArrayList<>();
        urlList.add("url");
        urlList.add("url1");
        Mockito.when(mockProductListingPageSummaryRequest.getSourcesList()).thenReturn(urlList);
        Mockito.when(mockProductListingPageSummaryReader.readMultipleProductListingPages(urlList)).thenReturn(mockListingPageSummaryResponse);

        //When
        ProductListingPageSummaryResponse response = productListingPageSummaryService.createProductListingPageSummary(mockProductListingPageSummaryRequest);

        //Then
        Assert.assertEquals(response, mockListingPageSummaryResponse);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenNullUrl() throws IOException {

        //Given
        Mockito.when(mockProductListingPageSummaryRequest.getSourcesList()).thenReturn(null);

        //When
        productListingPageSummaryService.createProductListingPageSummary(mockProductListingPageSummaryRequest);

        //Then
        //throws IllegalArgumentException.
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenEmptyUrl() throws IOException {

        //Given
        Mockito.when(mockProductListingPageSummaryRequest.getSourcesList()).thenReturn(Collections.emptyList());

        //When
        productListingPageSummaryService.createProductListingPageSummary(mockProductListingPageSummaryRequest);

        //Then
        //throws IllegalArgumentException.
    }
}
