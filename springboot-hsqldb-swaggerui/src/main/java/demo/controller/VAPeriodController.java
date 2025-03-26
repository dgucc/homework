package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import demo.model.VARequest;
import demo.model.VAResponse;
import demo.service.VAPeriodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags="CALCISOC - VA PERIODS", description = "Get the relevent VA period for given PaymentDate and balanceDate")
public class VAPeriodController {


    @Autowired
    private VAPeriodService service;

    // @GetMapping("/")
    // @ApiOperation(value = "Redirect to Swagger-UI", notes = "endpoint details...")
    // public RedirectView redirectToSwagger() {
    //     return new RedirectView("/swagger-ui/index.html"); 
    // }  
   
    @PostMapping("/va/periods/")
    @ApiOperation(value = "Get the relevent VA period for a given balanceDate and paymentDate", notes = "endpoint details...", response = VAResponse.class)
    public VAResponse getVAPeriodForPayment(
        @ApiParam(value = "RequestBody in JSON", required = true)
        @RequestBody VARequest request){
        return service.getVAPeriodsForPayments(request);
    }

}
