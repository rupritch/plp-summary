package com.sainsburys.services.plpsummary.reader;

import com.google.inject.Inject;
import com.sainsburys.services.plpsummary.response.Product;
import com.sainsburys.services.plpsummary.response.ProductListingPageSummary;
import com.sainsburys.services.plpsummary.response.ProductListingPageSummaryResponse;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a summary of single or multiple product listing pages.
 */
public class ProductListingPageSummaryHtmlReader implements ProductListingPageSummaryReader {

    private static final Logger logger = LoggerFactory.getLogger(ProductListingPageSummaryHtmlReader.class);

    private ProductListingPageSummaryResponse productListingPageSummaryResponse;

    private ProductDetailsHtmlReader productDetailsHtmlReader;

    private ProductListingPageHtmlReader productListingPageHtmlReader;

    private ProductListingPageSummary productListingPageSummary;

    private BigDecimal total = BigDecimal.valueOf(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);

    @Inject
    public ProductListingPageSummaryHtmlReader(final ProductListingPageSummaryResponse productListingPageSummaryResponse,
                                               final ProductDetailsHtmlReader productDetailsHtmlReader,
                                               final ProductListingPageHtmlReader productListingPageHtmlReader,
                                               final ProductListingPageSummary productListingPageSummary) {
        this.productListingPageSummaryResponse = productListingPageSummaryResponse;
        this.productDetailsHtmlReader = productDetailsHtmlReader;
        this.productListingPageHtmlReader = productListingPageHtmlReader;
        this.productListingPageSummary = productListingPageSummary;
    }

    @Override
    public ProductListingPageSummaryResponse readMultipleProductListingPages(final List<String> urlList) throws IOException {

        logger.debug("Entering PLP Summary HTML Reader for multiple urls");
        List<ProductListingPageSummary> productListingPageSummaryList = new ArrayList<>();
        if (isUrlListValid(urlList)) {
            for (String url : urlList) {
                List<String> pdpUrlList = productListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(url);
                productListingPageSummary.setProductListingPageTitle(productListingPageHtmlReader.readProductListingPageTitle(url));
                productListingPageSummary.setProductList(createListOfProducts(pdpUrlList));
                productListingPageSummary.setTotal(total);
                total = BigDecimal.valueOf(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);;
                productListingPageSummaryList.add(productListingPageSummary);
            }
        } else {
            throw new IllegalArgumentException("One of the passed in urls is not valid, please review list: " + urlList);
        }
        productListingPageSummaryResponse.setProductListingPageSummaries(productListingPageSummaryList);
        return productListingPageSummaryResponse;
    }

    @Override
    public ProductListingPageSummaryResponse readSingleProductListingPage(final String url) throws IOException {

        logger.debug("Entering PLP Summary HTML Reader for single url");
        if (isUrlValid(url)) {
            List<String> pdpUrlList = productListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(url);
            productListingPageSummaryResponse.setProductList(createListOfProducts(pdpUrlList));
            productListingPageSummaryResponse.setTotal(total);
            return productListingPageSummaryResponse;
        } else {
            throw new IllegalArgumentException("Passed in url is not valid: " + url);
        }
    }

    /**
     * Checks to see if the url to be called is valid. It is deemed valid if it contains the string sainsburys and passes the UrlValidator check.
     * @param url: url to scrape.
     * @return boolean: true if valid, false is not.
     */
    private boolean isUrlValid(final String url) {

        logger.debug("Validating url");
        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url) && url.contains("sainsburys");
    }

    /**
     * Checks to see if the list of plp urls passed in are valid. If one out of the list is invalid it will return false.
     * @param urlList: List of urls to scrape.
     * @return boolean: true if all valid, false if not.
     */
    private boolean isUrlListValid(final List<String> urlList) {

        logger.debug("Validating url list");
        for (String url : urlList) {
            if (!isUrlValid(url)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates list of Products with details read from their individual PDPs.
     * @param pdpUrlList: List of PDP urls to scrape.
     * @return productList: List of populated Product objects.
     * @throws IOException
     */
    private List<Product> createListOfProducts(final List<String> pdpUrlList) throws IOException {

        logger.debug("Creating list of products for each PDP");
        List<Product> productList = new ArrayList<>();
        for (String str : pdpUrlList) {
            logger.info("Creating list of products on PDP: " + str);
            Product product = productDetailsHtmlReader.readProductDetails(parseHtmlFromUrl(str));
            productList.add(product);
            total = total.add(product.getPricePerUnit());
        }
        return productList;
    }

    /**
     * Reads in the html from a given url.
     * @param pdpUrl: url of the product details webpage to be read.
     * @return Document: Html document containing all the html from the given url.
     * @throws IOException: Something has gone wrong connecting to the url - letting this bubble up to the caller as it is a fatal error.
     */
    private Document parseHtmlFromUrl(final String pdpUrl) throws IOException {

        logger.info("Parsing PDP url: " + pdpUrl);
        return Jsoup.connect(pdpUrl).get();
    }
}
