package com.sainsburys.services.plpsummary.reader;

import com.sainsburys.services.plpsummary.response.Product;
import com.sainsburys.services.plpsummary.response.ProductListingPageSummary;
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

/**
 * Test class for ProductListingPageSummaryHtmlReader.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductListingPageSummaryHtmlReaderTest {

    @Mock
    private ProductListingPageSummaryResponse mockProductListingPageSummaryResponse;

    @Mock
    private ProductDetailsHtmlReader mockProductDetailsHtmlReader;

    @Mock
    private ProductListingPageHtmlReader mockProductListingPageHtmlReader;

    @Mock
    private ProductListingPageSummary mockProductListingPageSummary;

    @Mock
    private Product mockProduct;

    @InjectMocks
    private ProductListingPageSummaryHtmlReader productListingPageSummaryHtmlReader;

    private static final String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
    private static final String URL1 = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    @Test
    public void shouldSetProductListOnResponseGivenValidSingleUrlContainingPDPLinks() throws IOException {

        //Given
        //Valid Url
        ProductListingPageSummaryResponse productListingPageSummaryResponse = new ProductListingPageSummaryResponse();
        ProductListingPageSummaryHtmlReader productListingPageSummaryHtmlReader = new ProductListingPageSummaryHtmlReader(
                productListingPageSummaryResponse, mockProductDetailsHtmlReader, mockProductListingPageHtmlReader, mockProductListingPageSummary);
        List<String> pdpUrls = new ArrayList<>();
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-cherry-punnet-200g-468015-p-44.html");
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-raspberries--taste-the-difference-150g.html");

        Mockito.when(mockProductListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(URL)).thenReturn(pdpUrls);
        Mockito.when(mockProductDetailsHtmlReader.readProductDetails(Mockito.any())).thenReturn(mockProduct);
        Mockito.when(mockProduct.getPricePerUnit()).thenReturn(new BigDecimal(1.00));

        //When
        productListingPageSummaryResponse = productListingPageSummaryHtmlReader.readSingleProductListingPage(URL);

        //Then
        Assert.assertEquals(mockProduct, productListingPageSummaryResponse.getProductList().get(0));
        Assert.assertEquals(mockProduct, productListingPageSummaryResponse.getProductList().get(1));
    }

    @Test
    public void shouldSetCorrectTotalSumOnResponseGivenValidSingleProductsWithPricesReturnedFromPDPReader() throws IOException {

        //Given
        //Valid Url
        ProductListingPageSummaryResponse productListingPageSummaryResponse = new ProductListingPageSummaryResponse();
        ProductListingPageSummaryHtmlReader productListingPageSummaryHtmlReader = new ProductListingPageSummaryHtmlReader(
                productListingPageSummaryResponse, mockProductDetailsHtmlReader, mockProductListingPageHtmlReader, mockProductListingPageSummary);
        List<String> pdpUrls = new ArrayList<>();
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-cherry-punnet-200g-468015-p-44.html");
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-raspberries--taste-the-difference-150g.html");

        Mockito.when(mockProductListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(URL)).thenReturn(pdpUrls);
        Mockito.when(mockProductDetailsHtmlReader.readProductDetails(Mockito.any())).thenReturn(mockProduct);
        Mockito.when(mockProduct.getPricePerUnit()).thenReturn(new BigDecimal(4.00));

        //When
        productListingPageSummaryResponse = productListingPageSummaryHtmlReader.readSingleProductListingPage(URL);

        //Then
        Assert.assertEquals(new BigDecimal(8.00).setScale(2, BigDecimal.ROUND_HALF_UP), productListingPageSummaryResponse.getTotal());
    }

    @Test
    public void shouldSetProductListingPageSummariesOnResponseGivenValidMultiplePLPUrlsContainingPDPLinks() throws IOException {

        //Given
        List<String> plpUrls = new ArrayList<>();
        plpUrls.add(URL);
        plpUrls.add(URL1);
        ProductListingPageSummaryResponse productListingPageSummaryResponse = new ProductListingPageSummaryResponse();
        ProductListingPageSummary productListingPageSummary = new ProductListingPageSummary();
        ProductListingPageSummaryHtmlReader productListingPageSummaryHtmlReader1 = new ProductListingPageSummaryHtmlReader(
                productListingPageSummaryResponse, mockProductDetailsHtmlReader, mockProductListingPageHtmlReader, productListingPageSummary);
        List<String> pdpUrls = new ArrayList<>();
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-cherry-punnet-200g-468015-p-44.html");
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-raspberries--taste-the-difference-150g.html");

        Mockito.when(mockProductListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(URL)).thenReturn(pdpUrls);
        Mockito.when(mockProductDetailsHtmlReader.readProductDetails(Mockito.any())).thenReturn(mockProduct);
        Mockito.when(mockProduct.getPricePerUnit()).thenReturn(new BigDecimal(3.50));

        //When
        productListingPageSummaryResponse = productListingPageSummaryHtmlReader1.readMultipleProductListingPages(plpUrls);

        //Then
        Assert.assertEquals(mockProduct, productListingPageSummaryResponse.getProductListingPageSummaries().get(0).getProductList().get(0));
        Assert.assertEquals(mockProduct, productListingPageSummaryResponse.getProductListingPageSummaries().get(0).getProductList().get(1));

        Assert.assertEquals(mockProduct, productListingPageSummaryResponse.getProductListingPageSummaries().get(1).getProductList().get(0));
        Assert.assertEquals(mockProduct, productListingPageSummaryResponse.getProductListingPageSummaries().get(1).getProductList().get(1));
    }

    @Test
    public void shouldSetCorrectTotalSumOnEachPLPSummaryGivenValidMultipleProductsWithPricesReturnedFromPDPReader() throws IOException {

        //Given
        List<String> plpUrls = new ArrayList<>();
        plpUrls.add(URL);
        plpUrls.add(URL1);
        ProductListingPageSummaryResponse productListingPageSummaryResponse = new ProductListingPageSummaryResponse();
        ProductListingPageSummary productListingPageSummary = new ProductListingPageSummary();
        ProductListingPageSummaryHtmlReader productListingPageSummaryHtmlReader1 = new ProductListingPageSummaryHtmlReader(
                productListingPageSummaryResponse, mockProductDetailsHtmlReader, mockProductListingPageHtmlReader, productListingPageSummary);
        List<String> pdpUrls = new ArrayList<>();
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-cherry-punnet-200g-468015-p-44.html");
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-raspberries--taste-the-difference-150g.html");

        Mockito.when(mockProductListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(plpUrls.get(0))).thenReturn(pdpUrls);
        Mockito.when(mockProductListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(plpUrls.get(1))).thenReturn(pdpUrls);
        Mockito.when(mockProductDetailsHtmlReader.readProductDetails(Mockito.any())).thenReturn(mockProduct);
        Mockito.when(mockProduct.getPricePerUnit()).thenReturn(new BigDecimal(4.00));

        //When
        productListingPageSummaryResponse = productListingPageSummaryHtmlReader1.readMultipleProductListingPages(plpUrls);

        //Then
        Assert.assertEquals(new BigDecimal(8.00).setScale(2, BigDecimal.ROUND_HALF_UP), productListingPageSummaryResponse.getProductListingPageSummaries().get(0).getTotal());
        Assert.assertEquals(new BigDecimal(8.00).setScale(2, BigDecimal.ROUND_HALF_UP), productListingPageSummaryResponse.getProductListingPageSummaries().get(1).getTotal());

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionGivenInvalidSingleUrl() throws IOException {

        //Given
        String invalidUrl = "INVALID_URL";

        //When
        productListingPageSummaryHtmlReader.readSingleProductListingPage(invalidUrl);

        //Then
        //Throws IllegalArgumentException.
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionGivenInvalidUrlInListOfMultipleUrls() throws IOException {

        //Given
        List<String> urls = new ArrayList<>();
        urls.add("INVALID_URL");
        urls.add("INVALID_URL2");

        //When
        productListingPageSummaryHtmlReader.readMultipleProductListingPages(urls);

        //Then
        //Throws IllegalArgumentException.
    }

    @Test(expected = IOException.class)
    public void shouldThrowIOExceptionGivenValidPDPUrlThatIsNotReachable() throws IOException {

        //Given
        String unreachablePdpUrl = "https://www.dummy.site.com/webpage.html";

        Mockito.when(mockProductListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(URL)).thenReturn(Collections.singletonList(unreachablePdpUrl));

        //When
        productListingPageSummaryHtmlReader.readSingleProductListingPage(URL);

        //Then
        //Throws IO Exception
    }
}
