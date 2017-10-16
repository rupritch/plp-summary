package com.sainsburys.services.plpsummary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sainsburys.services.plpsummary.config.ProductListingPageSummaryServiceModule;
import com.sainsburys.services.plpsummary.reader.ProductListingPageSummaryHtmlReader;
import com.sainsburys.services.plpsummary.request.ProductListingPageSummaryRequest;
import com.sainsburys.services.plpsummary.response.ProductListingPageSummaryResponse;
import com.sainsburys.services.plpsummary.service.ProductListingPageSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Application that calls the ProductListingPageSummaryService.
 */
public class ProductListingPageSummaryApplication {

    private static final Logger logger = LoggerFactory.getLogger(ProductListingPageSummaryHtmlReader.class);

    private static final String FALLBACK_URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    public static void main(String[] args) {

        ProductListingPageSummaryRequest productListingPageSummaryRequest;

        if (args.length > 0) {
            productListingPageSummaryRequest = new ProductListingPageSummaryRequest(Arrays.asList(args));
        } else {
            productListingPageSummaryRequest = new ProductListingPageSummaryRequest(new ArrayList<>(Collections.singletonList(FALLBACK_URL)));
        }

        Injector injector = Guice.createInjector(new ProductListingPageSummaryServiceModule());
        ProductListingPageSummaryService productListingPageSummaryService = injector.getInstance(ProductListingPageSummaryService.class);
        try (FileWriter fileWriter = new FileWriter("plp-summary.json")) {
            ProductListingPageSummaryResponse productListingPageSummaryResponse = productListingPageSummaryService.createProductListingPageSummary(productListingPageSummaryRequest);
            Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
            logger.info("Writing output to plp-summary.json file");
            fileWriter.write(gson.toJson(productListingPageSummaryResponse));
            logger.info("PLP summary generated successfully. Please check plp-summary.json for output");
        } catch (Exception e) {
            logger.error("Fatal error executing request for PLP summaries. Reason: ", e);
        }
    }
}
