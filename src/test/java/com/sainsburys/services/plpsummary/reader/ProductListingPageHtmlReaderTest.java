package com.sainsburys.services.plpsummary.reader;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListingPageHtmlReaderTest {

    private static final String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    @Test
    public void shouldReturnListOfPdpUrlsGivenPlpUrlThatContainsPdpUrls() throws IOException {

        //Given
        List<String> pdpUrls = new ArrayList<>();
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-cherry-punnet-200g-468015-p-44.html");
        pdpUrls.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-raspberries--taste-the-difference-150g.html");
        ProductListingPageHtmlReader productListingPageHtmlReader = new ProductListingPageHtmlReader();

        //When
        List<String> pdpUrlsResponse = productListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(URL);

        //Then
        Assert.assertTrue(pdpUrlsResponse.contains(pdpUrls.get(0)));
        Assert.assertTrue(pdpUrlsResponse.contains(pdpUrls.get(1)));
        Assert.assertFalse(pdpUrlsResponse.contains("https://dummyurl.com/page.html"));
    }

    @Test(expected = IOException.class)
    public void shouldThrowIOExceptionGivenUnreachablePlpUrl() throws IOException {

        //Given
        ProductListingPageHtmlReader productListingPageHtmlReader = new ProductListingPageHtmlReader();
        String unreachablePdpUrl = "https://www.dummy.site.com/webpage.html";

        //When
        productListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(unreachablePdpUrl);

        //Then
        //Throws IO Exception
    }
}
