package com.sainsburys.services.plpsummary.service;

import com.google.inject.Inject;
import com.sainsburys.services.plpsummary.reader.ProductListingPageSummaryReader;
import com.sainsburys.services.plpsummary.request.ProductListingPageSummaryRequest;
import com.sainsburys.services.plpsummary.response.ProductListingPageSummaryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Service that returns a product listing page summary for a given ProductListingPageSummaryRequest containing a url.
 */
public class ProductListingPageSummaryService {

    private static final Logger logger = LoggerFactory.getLogger(ProductListingPageSummaryService.class);

    private ProductListingPageSummaryReader productListingPageSummaryReader;

    @Inject
    public ProductListingPageSummaryService(ProductListingPageSummaryReader productListingPageSummaryReader) {
        this.productListingPageSummaryReader = productListingPageSummaryReader;
    }

    /**
     * Pulls out the url sent into the service in the request and calls out to the ProductListingPageSummaryReader to read the PLP data.
     * @param productListingPageSummaryRequest: Contains the url to the product listing page to be read.
     * @return ProductListingPageSummaryResponse: A list of products and the sum total of the products contained with a PLP.
     * @throws IOException: Something went wrong connecting to one of the urls. Calling class needs to know.
     */
    public ProductListingPageSummaryResponse createProductListingPageSummary(ProductListingPageSummaryRequest productListingPageSummaryRequest)
            throws IOException {

        if (productListingPageSummaryRequest.getSourcesList() != null && !productListingPageSummaryRequest.getSourcesList().isEmpty()) {

            if (productListingPageSummaryRequest.getSourcesList().size() == 1) {
                logger.info("Creating PLP summary for single source");
                return productListingPageSummaryReader.readSingleProductListingPage(productListingPageSummaryRequest.getSourcesList().get(0));
            } else {
                logger.info("Creating PLP summaries for multiple sources");
                return productListingPageSummaryReader.readMultipleProductListingPages(productListingPageSummaryRequest.getSourcesList());
            }
        } else {
            throw new IllegalArgumentException("Request object does not contain non empty source location list");
        }
    }
}
