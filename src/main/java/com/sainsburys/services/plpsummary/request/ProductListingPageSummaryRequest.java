package com.sainsburys.services.plpsummary.request;

import java.util.List;

/**
 * Request object for the ProductListingPageSummary Service
 */
public class ProductListingPageSummaryRequest {

    /**
     * List of sources to follow in order to obtain PLP information.
     */
    private List<String> sourcesList;

    public ProductListingPageSummaryRequest(List<String> sourcesList) {
        this.sourcesList = sourcesList;
    }

    public List<String> getSourcesList() {
        return sourcesList;
    }
}
