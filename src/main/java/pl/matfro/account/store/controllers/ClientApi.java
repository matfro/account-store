package pl.matfro.account.store.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.matfro.account.model.result.AmountResult;
import pl.matfro.account.model.result.ClientAccountsAmountResult;

@Tag(name = "Client")
@RequestMapping("clients/{clientIdentifier}")
public interface ClientApi {

  @Operation(
          operationId = "getClientAccountsAmounts",
          description = "Fetch clients accounts amounts in EUR",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "OK",
                          content = @Content(schema = @Schema(implementation = ClientAccountsAmountResult.class))),
                  @ApiResponse(responseCode = "404", description = "The resource was not found")
          })
  @GetMapping(
          value = "/accounts/amount",
          produces = {MediaType.APPLICATION_JSON_VALUE})
  ResponseEntity<ClientAccountsAmountResult> getClientAccountsAmounts(@PathVariable("clientIdentifier") String clientIdentifier);

  @Operation(
          operationId = "getClientAccountsAmounts",
          description = "Fetch clients accounts amounts in specified currency",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "OK",
                          content = @Content(schema = @Schema(implementation = ClientAccountsAmountResult.class))),
                  @ApiResponse(responseCode = "404", description = "The resource was not found")
          })
  @GetMapping(
          value = "/accounts/amounts",
          produces = {MediaType.APPLICATION_JSON_VALUE},
          params = "currency")
  ResponseEntity<ClientAccountsAmountResult> getAmount(
          @PathVariable("clientIdentifier") String clientIdentifier,
          @RequestParam("currency") String currency);
}
