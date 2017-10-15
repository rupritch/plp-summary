package com.sainsburys.services.plpsummary.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * Summary of a PLP page. This is only used if a request for multiple plp pages has been passed in.
 */
public class ProductListingPageSummary {

    /**
     * Title of the PLP page.
     */
    private String productListingPageTitle;

    /**
     * List of products contained within a PLP page.
     */
    private List<Product> productList;

    /**
     * Total cost of items listed on the PLP page.
     */
    private BigDecimal total;

    public String getProductListingPageTitle() {
        return productListingPageTitle;
    }

    public void setProductListingPageTitle(final String productListingPageTitle) {
        this.productListingPageTitle = productListingPageTitle;
    }

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
}
