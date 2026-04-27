package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import demo.dto.DateOfLateInterestRequest;
import demo.dto.DateOfLateInterestResponse;
import demo.service.DateOfLateInterestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@Validated
public class DateOfLateInterestController {


    @Autowired
    private DateOfLateInterestService service;

    @ApiOperation(value = "Redirect to Swagger-UI", notes = "notes...")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "301", description = "Redirect to swagger-UI page")
    })
        
    @GetMapping("/")
    public ResponseEntity<Void> redirectToSwagger() {
        return ResponseEntity.status(HttpStatus.FOUND)
                             .header("Location", "/swagger-ui/index.html")
                             .build();
    }
   
    @ApiOperation(value = "Calculate the Date of Late Interest", notes = "notes...")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success : Date of Late Interest calculated",content = @Content(mediaType = "application/json", schema = @Schema(implementation = DateOfLateInterestResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid Request : Please check input data", content = @Content)
    })
    @PostMapping("/article415/")
    public DateOfLateInterestResponse calculateDateOfLateInterest(@Valid @RequestBody DateOfLateInterestRequest request){

        /*
         * Pre-validations : If not valid -> CustomBadRequestException : HTTP 400
         */
        
        // withNonStandardInterestRate should be False
        if(request.getWithNonStandardInterest() == true){
            throw new CustomBadRequestException("Calculation not possible with Article 416 (Non Standard Interest Rate)");
        }


        return service.getDateOfLateInterest(request);
    }

}
