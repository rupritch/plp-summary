package com.sainsburys.services.plpsummary.reader;

import com.google.inject.Inject;
import com.sainsburys.services.plpsummary.response.ProductListingPageSummaryResponse;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductListingPageSummaryHtmlReader implements ProductListingPageSummaryReader {

    private ProductListingPageSummaryResponse productListingPageSummaryResponse;

    @Inject
    public ProductListingPageSummaryHtmlReader(final ProductListingPageSummaryResponse productListingPageSummaryResponse) {
        this.productListingPageSummaryResponse = productListingPageSummaryResponse;
    }

    @Override
    public ProductListingPageSummaryResponse readProductListingPage(String url) throws IOException {

        if (isUrlValid(url)) {
            productListingPageSummaryResponse.setProductList(new ArrayList<>());
            productListingPageSummaryResponse.setTotal(new BigDecimal(10.00));
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
}
