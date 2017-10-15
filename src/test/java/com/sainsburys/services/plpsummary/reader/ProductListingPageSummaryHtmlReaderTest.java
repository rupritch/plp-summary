package com.sainsburys.services.plpsummary.reader;

import com.sainsburys.services.plpsummary.response.Product;
import com.sainsburys.services.plpsummary.response.ProductListingPageSummaryResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProductListingPageSummaryHtmlReaderTest {

    @Mock
    private ProductListingPageSummaryResponse mockProductListingPageSummaryResponse;

    @Mock
    private ProductDetailsHtmlReader mockProductDetailsHtmlReader;

    @Mock
    private ProductListingPageHtmlReader mockProductListingPageHtmlReader;

    @Mock
    private Product mockProduct;

    @InjectMocks
    private ProductListingPageSummaryHtmlReader productListingPageSummaryHtmlReader;

    private static final String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    @Test
    public void shouldSetProductListOnResponseGivenValidUrlContainingPDPLinks() throws IOException {

        //Given
        //Valid Url
        ProductListingPageSummaryResponse productListingPageSummaryResponse = new ProductListingPageSummaryResponse();
        ProductListingPageSummaryHtmlReader productListingPageSummaryHtmlReader1 = new ProductListingPageSummaryHtmlReader(
                productListingPageSummaryResponse, mockProductDetailsHtmlReader, mockProductListingPageHtmlReader);
        List<String> pdpUrls = new ArrayList<>();
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-cherry-punnet-200g-468015-p-44.html");
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-raspberries--taste-the-difference-150g.html");

        Mockito.when(mockProductListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(URL)).thenReturn(pdpUrls);
        Mockito.when(mockProductDetailsHtmlReader.readProductDetails(Mockito.any())).thenReturn(mockProduct);
        Mockito.when(mockProduct.getPricePerUnit()).thenReturn(new BigDecimal(1.00));

        //When
        productListingPageSummaryResponse = productListingPageSummaryHtmlReader1.readProductListingPage(URL);

        //Then
        Assert.assertEquals(mockProduct, productListingPageSummaryResponse.getProductList().get(0));
        Assert.assertEquals(mockProduct, productListingPageSummaryResponse.getProductList().get(1));
    }

    @Test
    public void shouldSetCorrectTotalSumOnResponseGivenValidProductsWithPricesReturnedFromPDPReader() throws IOException {

        //Given
        //Valid Url
        ProductListingPageSummaryResponse productListingPageSummaryResponse = new ProductListingPageSummaryResponse();
        ProductListingPageSummaryHtmlReader productListingPageSummaryHtmlReader1 = new ProductListingPageSummaryHtmlReader(
                productListingPageSummaryResponse, mockProductDetailsHtmlReader, mockProductListingPageHtmlReader);
        List<String> pdpUrls = new ArrayList<>();
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-cherry-punnet-200g-468015-p-44.html");
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-raspberries--taste-the-difference-150g.html");

        Mockito.when(mockProductListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(URL)).thenReturn(pdpUrls);
        Mockito.when(mockProductDetailsHtmlReader.readProductDetails(Mockito.any())).thenReturn(mockProduct);
        Mockito.when(mockProduct.getPricePerUnit()).thenReturn(new BigDecimal(4.00));

        //When
        productListingPageSummaryResponse = productListingPageSummaryHtmlReader1.readProductListingPage(URL);

        //Then
        Assert.assertEquals(new BigDecimal(8.00).setScale(2, BigDecimal.ROUND_HALF_UP), productListingPageSummaryResponse.getTotal());
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

    @Test(expected = IOException.class)
    public void shouldThrowIOExceptionGivenValidPDPUrlThatIsNotReachable() throws IOException {

        //Given
        String unreachablePdpUrl = "https://www.dummy.site.com/webpage.html";

        Mockito.when(mockProductListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(URL)).thenReturn(Collections.singletonList(unreachablePdpUrl));

        //When
        productListingPageSummaryHtmlReader.readProductListingPage(URL);

        //Then
        //Throws IO Exception
    }
}