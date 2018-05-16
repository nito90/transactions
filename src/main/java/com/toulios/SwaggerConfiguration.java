package com.toulios;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

/**
 * @author nikolaos.toulios
 *
 * This class is to configur the Swagger library, in order to have a view of your
 * valid requests.
 *
 */
public class SwaggerConfiguration extends Configuration{

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
}
