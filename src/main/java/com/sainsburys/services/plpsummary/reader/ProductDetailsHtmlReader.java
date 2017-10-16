package com.sainsburys.services.plpsummary.reader;

import com.sainsburys.services.plpsummary.response.Product;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * Reads all the Product information required of the service from a html document of a product details page.
 */
public class ProductDetailsHtmlReader {

    private static final Logger logger = LoggerFactory.getLogger(ProductDetailsHtmlReader.class);

    /**
     * Reads and initialises a new Product with the following product details read from a product details page html:
     * - title
     * - kcalPer100g
     * - pricePerUnit
     * - description
     * @param document: Html document from a product details page.
     * @return Product: Populated Product object.
     */
    public Product readProductDetails(Document document) {

        logger.debug("Reading product details");
        String title = readProductTitle(document);
        Integer kcalPer100g = readKcalPer100g(document);
        BigDecimal pricePerUnit = readProductPricePerUnit(document);
        String description = readProductDescription(document);
        return new Product(title, kcalPer100g, pricePerUnit, description);
    }

    /**
     * Returns the product title from the product details page.
     * Note: Every PDP is expected to have a product title. If can't find one then will need to investigate why it doesn't.
     * Therefore am allowing any unexpected NPE exceptions to terminate the request.
     * @param document: Html document from a product details page.
     * @return String: product title.
     */
    private String readProductTitle(Document document) {
        logger.debug("Reading product title");
        return document.getElementsByClass("productTitleDescriptionContainer").first().text();
    }

    /**
     * Returns the first line of the product description from the product details page.
     * Note: Every PDP is expected to have a product description. If can't find one then will need to investigate why it doesn't.
     * Therefore am allowing any unexpected NPE exceptions to terminate the request.
     * @param document: Html document from a product details page.
     * @return String: 1st line of the product description.
     */
    private String readProductDescription(Document document) {

        logger.debug("Reading product description");
        Element element = document.getElementById("information").getElementsByClass("productText").first();
        if (isDescriptionMultipleLines(element)) {
            return element.getElementsByClass("memo").first().text().trim();
        }
        if (element.getElementsByTag("p").first().hasText()) {
            return element.getElementsByTag("p").first().text().trim();
        } else {
            return element.getElementsByTag("p").text().trim();
        }
    }

    /**
     * Determines if the product description covers multiple lines in the product details page.
     * @param element: Html element containing the product description.
     * @return boolean: true if multiple lines, false if not.
     */
    private boolean isDescriptionMultipleLines(Element element) {

        return !element.getElementsByClass("memo").isEmpty();
    }

    /**
     * Returns the price per unit of product from the product details page.
     * Every PDP is expected to have a product price per unit value. If can't find one then will need to investigate why it doesn't.
     * Therefore am allowing any unexpected NPE exceptions to terminate the request.
     * @param document: Html document from a product details page.
     * @return BigDecimal: pricePerUnit.
     */
    private BigDecimal readProductPricePerUnit(Document document) {

        logger.debug("Reading product price per unit");
        return new BigDecimal(document.getElementsByClass("pricePerUnit")
                .first()
                .text()
                .replaceAll("[^0-9.]+", "")).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Returns the kcal per 100g value from the nutrition information table on product details page.
     * Every PDP is not expected to have a kcal per 100g so am explicitly checking for potential npe.
     * If no kcalPer100g value is found then null is returned.
     * @param document: Html document from a product details page.
     * @return String or null: kcalPer100g value if found otherwise will return null.
     */
    private Integer readKcalPer100g(Document document) {

        logger.debug("Reading product kcalPer100g");
        Elements table = document.getElementsByClass("nutritionTable");

        if (table != null && !table.isEmpty()) {
            Elements tableHeader = table.select("thead");
            if (tableHeader != null && !tableHeader.isEmpty()) {
                Elements columnHeadings = tableHeader.get(0).select("th");
                if (columnHeadings != null && !columnHeadings.isEmpty()) {
                    OptionalInt column = IntStream.range(1, columnHeadings.size() -1)
                            .filter(columnIndex -> columnHeadings.get(columnIndex).text().toLowerCase().contains("per 100g"))
                            .findFirst();
                    if (column.isPresent() && column.getAsInt()-1 >= 0) {
                        Elements tableRows = table.get(0).select("tr");
                        String kcal = tableRows.get(2).select("td").get(column.getAsInt()-1).text().replace("kcal", "");
                        return Integer.valueOf(kcal);
                    }
                }
            }
        }
        logger.info("No kcalPer100g found.");
        return null;
    }
}
