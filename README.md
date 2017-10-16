# plp-summary
Service to provide a summary of products listed on a Sainsbury's product listing page.

Accepts none or multiple arguments.
If no argument is supplied it will use a fallback url to generate the response.
If an argument is supplied and it is a valid url it will be used to generate the response.
If multiple arguments are supplied and all are valid urls they will be used to generate the response.

Currently the service only supports looking up product listing information from a PLP url. However this could easily be expanded in future to accept other locations to read the data from.

On successful completion a plp-summary.json file is created containing the PLP details in the root dir of the project.

# Building
This project is using gradle for building.

In order to build follow the steps below:
1. Check out the code to your machine from the following location ``https://github.com/rupritch/plp-summary.git``.
2. From the root of the checked out project run ``./gradlew clean build``.

# Running The Tests

The test will run as part of the build, however you can run the unit tests separately by running the following from the project root:
``./gradlew test``.

# Running the application

The application can be run from the root directly with a number of different arguments:

- No arguments: ``java -jar ./build/libs/plp-summary-1.0-RELEASE.jar``.
- Single Argument: ``java -jar ./build/libs/plp-summary-1.0-RELEASE.jar https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html``.
- Multiple Arguments: ``java -jar ./build/libs/plp-summary-1.0-RELEASE.jar https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html``.

# Dependencies

The project has the following dependencies:

- Gson 2.8.2
- Jsoup 1.10.3
- commons-validator 1.6
- Guigle Guice 4.1.0
- log4j-api 2.9.1
- log4j-core 2.9.1
- slf4j-api 1.7.25
- slf4j-log4j12 1.7.25
- junit 4.12
- mockito-core 2.10.0
