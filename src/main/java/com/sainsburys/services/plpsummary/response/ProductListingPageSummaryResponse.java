package com.sainsburys.services.plpsummary.response;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response object returned from the service.
 *
 */
public class ProductListingPageSummaryResponse {

    /**
     * List of products on a PLP.
     * Only used when building response for single PLP.
     */
    @SerializedName("results")
    private List<Product> productList;

    /**
     * Total cost of the products on a PLP.
     * Only used when building response for single PLP.
     */
    private BigDecimal total;

    /**
     * Holds a list of PLP summaries.
     * Only used when building result for multiple PLPs.
     */
    private List<ProductListingPageSummary> productListingPageSummaries;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(final List<Product> productList) {
        this.productList = productList;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(final BigDecimal total) {
        this.total = total;
    }

    public List<ProductListingPageSummary> getProductListingPageSummaries() {
        return productListingPageSummaries;
    }

    public void setProductListingPageSummaries(final List<ProductListingPageSummary> productListingPageSummaries) {
        this.productListingPageSummaries = productListingPageSummaries;
    }
}
