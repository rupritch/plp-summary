package com.sainsburys.services.plpsummary.reader;

import com.google.inject.Inject;
import com.sainsburys.services.plpsummary.response.Product;
import com.sainsburys.services.plpsummary.response.ProductListingPageSummary;
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

        List<ProductListingPageSummary> productListingPageSummaryList = new ArrayList<>();
        for (String url : urlList) {
            if (isUrlValid(url)) {
                List<String> pdpUrlList = productListingPageHtmlReader.createListOfProductDetailPageUrlsForPage(url);
                productListingPageSummary.setProductListingPageTitle(productListingPageHtmlReader.readProductListingPageTitle(url));
                productListingPageSummary.setProductList(createListOfProducts(pdpUrlList));
                productListingPageSummary.setTotal(total);
                total = BigDecimal.valueOf(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);;
                productListingPageSummaryList.add(productListingPageSummary);
            } else {
                throw new IllegalArgumentException("Passed in url is not valid: " + url);
            }
        }
        productListingPageSummaryResponse.setProductListingPageSummaries(productListingPageSummaryList);
        return productListingPageSummaryResponse;
    }

    @Override
    public ProductListingPageSummaryResponse readSingleProductListingPage(final String url) throws IOException {

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
    private boolean isUrlValid(final String url) {

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
    private List<Product> createListOfProducts(final List<String> pdpUrlList) throws IOException {

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
    private Document parseHtmlFromUrl(final String url) throws IOException {

        if (!url.contains(".html")) {
            Document document = Jsoup.connect(url).get();
            String baseUri = document.baseUri();
            return Jsoup.connect(baseUri + ".html").get();
        }
        return Jsoup.connect(url).get();
    }
}
