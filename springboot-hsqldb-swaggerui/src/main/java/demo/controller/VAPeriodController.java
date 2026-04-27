package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import demo.model.VARequest;
import demo.model.VAResponse;
import demo.service.VAPeriodService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;

@RestController
public class VAPeriodController {


    @Autowired
    private VAPeriodService service;

    @ApiOperation(value = "Redirect to Swagger-UI", notes = "notes...")
    @GetMapping("/")
    public ResponseEntity<Void> redirectToSwagger() {
        return ResponseEntity.status(HttpStatus.FOUND)
                             .header("Location", "/swagger-ui/index.html")
                             .build();
    }
   
    @ApiOperation(value = "Get the relevent VA period for a given balanceDate and advancedPaymentDate", notes = "notes...")
    @PostMapping("/va/periods/")
    public VAResponse getVAPeriodForPayment(@Valid @RequestBody VARequest request){

        // Pre-validations : If not valid -> CustomBadRequestException : HTTP 400
        
        // taxYearStartDate should be < balanceDate
        if(request.getBalanceDate().isBefore(request.getTaxYearStartDate())){
            throw new CustomBadRequestException("The taxYearStartDate should occur before the balanceDate");
        }

        // isFirstActivity should be provided as valid boolean
        if(request.getIsFirstActivity() == null) {
            throw new CustomBadRequestException("isFirstActivity is missing or incorrect");
        }

        // payments is missing
        if(request.getPayments().isEmpty()){
            throw new CustomBadRequestException("list of payment is empty");
        }

        return service.getVAPeriodsForPayments(request);
    }

}
