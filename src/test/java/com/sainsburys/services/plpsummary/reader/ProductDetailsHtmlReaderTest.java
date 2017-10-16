package com.sainsburys.services.plpsummary.reader;

import com.sainsburys.services.plpsummary.response.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Test class for ProductDetailsHtmlReader.
 */
public class ProductDetailsHtmlReaderTest {

    @Test
    public void shouldReadAndSetProductTitleOnInitialisedProductGivenValidPDPHtmlDocument() throws IOException {

        //Given
        File file = new File(getClass().getClassLoader().getResource("PdpOneLineDescNutritionTab.html").getFile());
        Document examplePDPDocument = Jsoup.parse(file, "UTF-8");
        ProductDetailsHtmlReader productDetailsHtmlReader = new ProductDetailsHtmlReader();

        //When
        Product product = productDetailsHtmlReader.readProductDetails(examplePDPDocument);

        //Then
        Assert.assertNotNull(product);
        Assert.assertEquals("Sainsbury's Blueberries 200g", product.getTitle());
    }

    @Test
    public void shouldReadAndSetProductDescriptionOnInitialisedProductGivenValidPDPHtmlDocumentWithOneLineDescription() throws IOException {

        //Given
        File file = new File(getClass().getClassLoader().getResource("PdpOneLineDescNutritionTab.html").getFile());
        Document examplePDPDocument = Jsoup.parse(file, "UTF-8");
        ProductDetailsHtmlReader productDetailsHtmlReader = new ProductDetailsHtmlReader();

        //When
        Product product = productDetailsHtmlReader.readProductDetails(examplePDPDocument);

        //Then
        Assert.assertNotNull(product);
        Assert.assertEquals("by Sainsbury's blueberries", product.getDescription());
    }

    @Test
    public void shouldReadAndSetOnlyFirstLineProductDescriptionOnInitialisedProductGivenValidPDPHtmlDocumentWithMultipleLineDescription() throws IOException {

        //Given
        File file = new File(getClass().getClassLoader().getResource("PdpMultiLineDescNoNutritionTab.html").getFile());
        Document examplePDPDocument = Jsoup.parse(file, "UTF-8");
        ProductDetailsHtmlReader productDetailsHtmlReader = new ProductDetailsHtmlReader();

        //When
        Product product = productDetailsHtmlReader.readProductDetails(examplePDPDocument);

        //Then
        Assert.assertNotNull(product);
        Assert.assertEquals("British Cherry & Strawberry Mixed Pack", product.getDescription());
    }

    @Test
    public void shouldReadAndSetProductPricePerUnitOnInitialisedProductGivenValidPDPHtmlDocument() throws IOException {

        //Given
        File file = new File(getClass().getClassLoader().getResource("PdpOneLineDescNutritionTab.html").getFile());
        Document examplePDPDocument = Jsoup.parse(file, "UTF-8");
        ProductDetailsHtmlReader productDetailsHtmlReader = new ProductDetailsHtmlReader();

        //When
        Product product = productDetailsHtmlReader.readProductDetails(examplePDPDocument);

        //Then
        Assert.assertNotNull(product);
        Assert.assertEquals(new BigDecimal(1.75), product.getPricePerUnit());
    }

    @Test
    public void shouldReadAndSetProductKcalPer100gOnInitialisedProductGivenValidPDPHtmlDocumentWithNutritionTable() throws IOException {

        //Given
        File file = new File(getClass().getClassLoader().getResource("PdpOneLineDescNutritionTab.html").getFile());
        Document examplePDPDocument = Jsoup.parse(file, "UTF-8");
        ProductDetailsHtmlReader productDetailsHtmlReader = new ProductDetailsHtmlReader();

        //When
        Product product = productDetailsHtmlReader.readProductDetails(examplePDPDocument);

        //Then
        Assert.assertNotNull(product);
        Assert.assertEquals(new Integer(45), product.getKcalPer100g());
    }

    @Test
    public void shouldReadAndSetNullProductKcalPer100gOnInitialisedProductGivenValidPDPHtmlDocumentWithNoNutritionTable() throws IOException {

        //Given
        File file = new File(getClass().getClassLoader().getResource("PdpMultiLineDescNoNutritionTab.html").getFile());
        Document examplePDPDocument = Jsoup.parse(file, "UTF-8");
        ProductDetailsHtmlReader productDetailsHtmlReader = new ProductDetailsHtmlReader();

        //When
        Product product = productDetailsHtmlReader.readProductDetails(examplePDPDocument);

        //Then
        Assert.assertNotNull(product);
        Assert.assertNull(product.getKcalPer100g());
    }
}
