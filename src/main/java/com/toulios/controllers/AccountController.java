package com.toulios.controllers;

import com.sun.org.apache.regexp.internal.RE;
import com.toulios.exceptions.account.AccountNotFoundException;
import com.toulios.exceptions.account.AccountValidationException;
import com.toulios.model.Account;
import com.toulios.repository.AccountRepositoryImpl;
import com.toulios.services.AccountServiceImpl;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nikolaos.toulios
 *
 * A controller to call CRUD operations for Account model
 */
@Path("/accounts")
@Api("/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;

    public AccountController(AccountRepositoryImpl accountRepository){
        this.accountService = new AccountServiceImpl(accountRepository);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("Return all the Accounts from the In Memory storage.")
    public Response getAllAccounts(){
        return Response.ok().entity(accountService.getAccounts()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("Return all the Accounts from the In Memory storage for the specified accountHolderName.")
    @Path("/{name}")
    public Response getAllAccountsByName(@PathParam("name") String accountHolderName){
        return Response.ok().entity(accountService.getAllAccounts(accountHolderName)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create a new account",
            response = Account.class
    )
    public Response addAccount(@ApiParam(value = "The information for the new Account",
            required = true) Account account){
        try{
            Account accountValue = accountService.addAccount(account);
            return Response.ok().entity(accountValue).build();
        }catch (AccountValidationException e){
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update a new account",
            response = Account.class
    )
    public Response updateAccount(@ApiParam(value = "The updated information for the specified Account",
            required = true) Account account){
        try{
            Account accountValue = accountService.updateAccount(account);
            return Response.ok().entity(accountValue).build();
        }catch (AccountNotFoundException | AccountValidationException e){
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation("Delete an account ")
    public Response deleteAccount(@FormParam("accountHolderName") String accountHolderName,
                              @FormParam("accountId") String accountId){
        if(StringUtils.isNotBlank(accountHolderName) && StringUtils.isNotBlank(accountId)){
            accountService.deleteAccount(accountHolderName, accountId);
        }
        return Response.noContent().build();
    }

}
