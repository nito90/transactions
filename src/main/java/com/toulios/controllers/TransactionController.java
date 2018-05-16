package com.toulios.controllers;

import com.sun.org.apache.regexp.internal.RE;
import com.toulios.model.Account;
import com.toulios.model.Transaction;
import com.toulios.repository.AccountRepositoryImpl;
import com.toulios.repository.TransactionRepositoryImpl;
import com.toulios.services.AccountServiceImpl;
import com.toulios.services.TransactionServiceImpl;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * @author nikolaos.toulios
 *
 * A controller to call CRUD operations for Transaction model
 */
@Path("/transaction")
@Api("/transaction")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    public TransactionController(TransactionRepositoryImpl transactionRepository, AccountRepositoryImpl accountRepository){
        this.transactionService = new TransactionServiceImpl(transactionRepository, accountRepository);
    }
    @GET
    @Path("/all")
    @ApiOperation("Get all the transactions from the In Memory storage")
    public Response getAll(){
        return Response.ok().entity(transactionService.findAll()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(
            value = "Create a new Transaction",
            response =Transaction.class
    )
    public Response createTransaction(@FormParam("fromAccountName") String fromAccountName,
                                      @FormParam("toAccountName") String toAccountName,
                                      @FormParam("fromAccountId") String fromAccountId,
                                      @FormParam("toAccountId") String toAccountId,
                                      @FormParam("transferringAmmount") Integer transferringAmmount){


        if(StringUtils.isNotBlank(fromAccountName) && StringUtils.isNotBlank(toAccountName) && StringUtils.isNotBlank(fromAccountId) && StringUtils.isNotBlank(toAccountId)){
            Account fromAccount = new Account();
            fromAccount.setAccountId(fromAccountId);
            fromAccount.setAccountHoldersName(fromAccountName);

            Account toAccount = new Account();
            toAccount.setAccountId(toAccountId);
            toAccount.setAccountHoldersName(toAccountName);

            Transaction transaction = new Transaction(null, fromAccount, toAccount, transferringAmmount);
            try{
                Transaction transactionValue = transactionService.addTransaction(transaction);
                return Response.ok().entity(transactionValue).build();
            }catch (Exception e){
                return Response.serverError().entity(e.getMessage()).build();
            }
        }
        return Response.noContent().build();
    }

    @GET
    @ApiOperation("Get the transaction for the specified pair (fromAccountId, toAccountId)")
    public Response getTransaction(@QueryParam("fromAccountId") String fromAccountId,
                                      @QueryParam("toAccountId") String toAccountId){

        if(StringUtils.isNotBlank(fromAccountId) && StringUtils.isNotBlank(toAccountId)){
            Pair<String, String> key = new Pair<>(fromAccountId, toAccountId);
            try{
                Transaction transaction = transactionService.findTransaction(key);
                return Response.ok().entity(transaction).build();
            }catch (Exception e){
                return Response.serverError().entity(e.getMessage()).build();
            }
        }
        return Response.noContent().build();
    }

}
