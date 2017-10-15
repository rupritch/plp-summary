package com.sainsburys.services.plpsummary.reader;

import com.sainsburys.services.plpsummary.response.ProductListingPageSummaryResponse;

import java.io.IOException;

/**
 * Reads the information from a given source location for a ProductListingPage and returns a response object
 * containing a summary of the products located within the page.
 */
public interface ProductListingPageSummaryReader {

    /**
     * Method to read the information from a product listing page for a given source.
     * @param source: Location to find the product listing page data. (eg. webpage url, database location, webservice endpoint)
     * @return ProductListingPageSummaryResponse: response object containing list of products and price total for the PLP.
     * @throws IOException: Something went wrong in connecting to the source.
     */
    ProductListingPageSummaryResponse readProductListingPage(String source) throws IOException;
}
