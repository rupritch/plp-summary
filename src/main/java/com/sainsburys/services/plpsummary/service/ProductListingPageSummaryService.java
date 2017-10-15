package com.sainsburys.services.plpsummary.service;

import com.google.inject.Inject;
import com.sainsburys.services.plpsummary.reader.ProductListingPageSummaryReader;
import com.sainsburys.services.plpsummary.request.ProductListingPageSummaryRequest;
import com.sainsburys.services.plpsummary.response.ProductListingPageSummaryResponse;

import java.io.IOException;

/**
 * Service that returns a product listing page summary for a given ProductListingPageSummaryRequest containing a url.
 */
public class ProductListingPageSummaryService {

    private ProductListingPageSummaryReader productListingPageSummaryReader;

    @Inject
    public ProductListingPageSummaryService(ProductListingPageSummaryReader productListingPageSummaryReader) {
        this.productListingPageSummaryReader = productListingPageSummaryReader;
    }

    /**
     * Pulls out the url sent into the service in the request and calls out to the ProductListingPageSummaryReader to read the PLP data.
     * @param productListingPageSummaryRequest: Contains the url to the product listing page to be read.
     * @return ProductListingPageSummaryResponse: A list of products and the sum total of the products contained with a PLP.
     * @throws IOException
     */
    public ProductListingPageSummaryResponse createProductListingPageSummary(ProductListingPageSummaryRequest productListingPageSummaryRequest)
            throws IOException {

        if (productListingPageSummaryRequest.getSourcesList() != null && !productListingPageSummaryRequest.getSourcesList().isEmpty()) {

            if (productListingPageSummaryRequest.getSourcesList().size() == 1) {
                return productListingPageSummaryReader.readSingleProductListingPage(productListingPageSummaryRequest.getSourcesList().get(0));
            } else {
                return productListingPageSummaryReader.readMultipleProductListingPages(productListingPageSummaryRequest.getSourcesList());
            }
        } else {
            throw new IllegalArgumentException("Request object does not contain non empty source location list");
        }
    }
}
