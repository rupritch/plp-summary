package com.sainsburys.services.plpsummary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sainsburys.services.plpsummary.config.ProductListingPageSummaryServiceModule;
import com.sainsburys.services.plpsummary.request.ProductListingPageSummaryRequest;
import com.sainsburys.services.plpsummary.response.ProductListingPageSummaryResponse;
import com.sainsburys.services.plpsummary.service.ProductListingPageSummaryService;

import java.io.IOException;

public class ProductListingPageSummaryApplication {

    private static final String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    public static void main(String[] args) {

        ProductListingPageSummaryRequest productListingPageSummaryRequest  = new ProductListingPageSummaryRequest(URL);

        Injector injector = Guice.createInjector(new ProductListingPageSummaryServiceModule());
        ProductListingPageSummaryService productListingPageSummaryService = injector.getInstance(ProductListingPageSummaryService.class);
        try {
            ProductListingPageSummaryResponse productListingPageSummaryResponse = productListingPageSummaryService.createProductListingPageSummary(productListingPageSummaryRequest);
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            System.out.println(gson.toJson(productListingPageSummaryResponse));
        } catch (IOException e) {
            //TODO RDP: Improve messaging here to the caller!!!
            System.out.println("Something went wrong...fix it.");
        }
    }
}
