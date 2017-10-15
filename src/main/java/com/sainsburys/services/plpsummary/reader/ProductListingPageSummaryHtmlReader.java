package com.sainsburys.services.plpsummary.reader;

import com.google.inject.Inject;
import com.sainsburys.services.plpsummary.response.Product;
import com.sainsburys.services.plpsummary.response.ProductListingPageSummaryResponse;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductListingPageSummaryHtmlReader implements ProductListingPageSummaryReader {

    private ProductListingPageSummaryResponse productListingPageSummaryResponse;

    private ProductDetailsHtmlReader productDetailsHtmlReader;

    private ProductListingPageHtmlReader productListingPageHtmlReader;

    private BigDecimal total = BigDecimal.valueOf(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);

    @Inject
    public ProductListingPageSummaryHtmlReader(final ProductListingPageSummaryResponse productListingPageSummaryResponse,
                                               final ProductDetailsHtmlReader productDetailsHtmlReader,
                                               final ProductListingPageHtmlReader productListingPageHtmlReader) {
        this.productListingPageSummaryResponse = productListingPageSummaryResponse;
        this.productDetailsHtmlReader = productDetailsHtmlReader;
        this.productListingPageHtmlReader = productListingPageHtmlReader;
    }

    @Override
    public ProductListingPageSummaryResponse readProductListingPage(String url) throws IOException {

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
     * Checks to see if the url to be called is valid.
     * @param url: url to scrape.
     * @return boolean: true if valid, false is not.
     */
    private boolean isUrlValid(String url) {

        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }

    /**
     * Creates list of Products with details read from their individual PDPs.
     * @param pdpUrlList: List of PDP urls to scrape.
     * @return productList: List of populated Product objects.
     * @throws IOException
     */
    private List<Product> createListOfProducts(List<String> pdpUrlList) throws IOException {

        List<Product> productList = new ArrayList<>();

        for (String str : pdpUrlList) {
            Product product = productDetailsHtmlReader.readProductDetails(parseHtmlFromUrl(str));
            productList.add(product);
            total = total.add(product.getPricePerUnit());
        }
        return productList;
    }

    /**
     * Reads in the html from a given url.
     * @param url: url of the webpage to be read.
     * @return Document: Html document containing all the html from the given url.
     * @throws IOException
     */
    private Document parseHtmlFromUrl(String url) throws IOException {

        return Jsoup.connect(url).get();
    }
}
