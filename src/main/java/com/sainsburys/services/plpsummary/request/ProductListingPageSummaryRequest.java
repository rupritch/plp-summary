package com.sainsburys.services.plpsummary.request;

public class ProductListingPageSummaryRequest {

    private String url;

    public ProductListingPageSummaryRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
