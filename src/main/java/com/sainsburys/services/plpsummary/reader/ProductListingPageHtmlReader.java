package com.sainsburys.services.plpsummary.reader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reads through a PLP html document and returns a list of PDP urls contained within it.
 */
public class ProductListingPageHtmlReader {

    private static final Logger logger = LoggerFactory.getLogger(ProductListingPageHtmlReader.class);

    /**
     * Creates a list of product detail page absolute urls for each product on the passed in product listing page.
     * Every passed in PLP is expected to contain products.
     * @param plpUrl: html document for the product listing page.
     * @return list of product details page urls.
     */
    public List<String> createListOfProductDetailPageUrlsForPage(final String plpUrl) throws IOException {

        logger.debug("Reading PDP urls from PLP");
        Document plpDocument = parseHtmlFromUrl(plpUrl);
        Elements products = plpDocument.getElementsByClass("productInfo");

        return products.stream()
                .map(element -> element.getElementsByTag("a").attr("abs:href"))
                .collect(Collectors.toList());
    }

    /**
     * Reads the title of the PLP. Each PLP is expected to contain a title.
     * @param plpUrl: url of the PLP.
     * @return String: PLP title.
     * @throws IOException: Something when wrong connecting to url. This is critical.
     */
    public String readProductListingPageTitle(final String plpUrl) throws IOException {

        logger.debug("Reading PLP title");
        Document plpDocument = parseHtmlFromUrl(plpUrl);
        return plpDocument.getElementById("resultsHeading").text();
    }

    /**
     * Reads in the html from a given url.
     * @param url: url of the webpage to be read.
     * @return Document: Html document containing all the html from the given url.
     * @throws IOException: Something has gone wrong connecting to the url.
     */
    private Document parseHtmlFromUrl(final String url) throws IOException {

        logger.info("Parsing PLP url: " + url);
        return Jsoup.connect(url).get();
    }
}
