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

@Tag(name = "Accounts")
@RequestMapping("accounts/{accountNumber}")
public interface AccountApi {

  @Operation(
          operationId = "getAmount",
          description = "Fetch account amount in EUR",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "OK",
                          content = @Content(schema = @Schema(implementation = AmountResult.class))),
                  @ApiResponse(responseCode = "404", description = "The resource was not found")
          })
  @GetMapping(
          value = "/amount",
          produces = {MediaType.APPLICATION_JSON_VALUE})
  ResponseEntity<AmountResult> getAmount(@PathVariable("accountNumber") String accountNumber);

  @Operation(
          operationId = "getAmount",
          description = "Fetch account amount in specified currency",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "OK",
                          content = @Content(schema = @Schema(implementation = AmountResult.class))),
                  @ApiResponse(responseCode = "404", description = "The resource was not found")
          })
  @GetMapping(
          value = "/amount",
          produces = {MediaType.APPLICATION_JSON_VALUE},
          params = "currency")
  ResponseEntity<AmountResult> getAmount(
          @PathVariable("accountNumber") String accountNumber,
          @RequestParam("currency") String currency);
}
