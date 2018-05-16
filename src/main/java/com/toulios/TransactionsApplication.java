package com.toulios;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.toulios.controllers.AccountController;
import com.toulios.controllers.TransactionController;
import com.toulios.repository.AccountRepositoryImpl;
import com.toulios.repository.TransactionRepositoryImpl;
import com.wordnik.swagger.jaxrs.config.BeanConfig;
import com.wordnik.swagger.models.Contact;
import com.wordnik.swagger.models.Info;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

/**
 * @author nikolaos.toulios
 */
public class TransactionsApplication extends Application<SwaggerConfiguration>
{
    public static void main( String[] args ) throws Exception {
        new TransactionsApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<SwaggerConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<SwaggerConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(SwaggerConfiguration sampleConfiguration) {
                return sampleConfiguration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(SwaggerConfiguration configuration, Environment environment) throws Exception {
        // add your resources as usual
        TransactionRepositoryImpl transactionRepository = new TransactionRepositoryImpl();
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();

        environment.jersey().register(new TransactionController(transactionRepository, accountRepository));
        environment.jersey().register(new AccountController(accountRepository));
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Info apiInfo = new Info();
        Contact contact = new Contact();
        contact.setEmail("toulios90@gmail.com");
        contact.setName("Nikolaos Toulios");
        apiInfo.setContact(contact);

        BeanConfig config = new BeanConfig();
        config.setTitle("Transaction Applications");
        config.setVersion("1.0.0");
        config.setResourcePackage("com.toulios.controllers");
        config.setDescription("This api is handling the basic functionalities of a simple transaction system.");
        config.setInfo(apiInfo);
        config.setScan(true);
    }
}
