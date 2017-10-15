package com.sainsburys.services.plpsummary.reader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reads through a PLP html document and returns a list of PDP urls containing within it.
 */
public class ProductListingPageHtmlReader {

    /**
     * Creates a list of product detail page absolute urls for each product on the passed in product listing page.
     * @param plpUrl: html document for the product listing page.
     * @return list of product details page urls.
     */
    public List<String> createListOfProductDetailPageUrlsForPage(String plpUrl) throws IOException {

        Document plpDocument = parseHtmlFromUrl(plpUrl);
        Elements products = plpDocument.getElementsByClass("productInfo");

        return products.stream()
                .map(element -> element.getElementsByTag("a").attr("abs:href"))
                .collect(Collectors.toList());
    }

    /**
     * Reads in the html from a given url.
     * @param url: url of the webpage to be read.
     * @return Document: Html document containing all the html from the given url.
     * @throws IOException: Something has gone wrong connecting to the url.
     */
    private Document parseHtmlFromUrl(String url) throws IOException {

        return Jsoup.connect(url).get();
    }
}
