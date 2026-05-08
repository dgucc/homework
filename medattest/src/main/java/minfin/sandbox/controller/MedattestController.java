package minfin.sandbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import minfin.sandbox.model.ThirdPartyPayer;
import minfin.sandbox.service.MedattestService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/api/v1")
// @AllArgsConstructor
public class MedattestController {
   
   @Autowired
   private MedattestService service;
   @Autowired
   private ObjectMapper objectMapper;

   @GetMapping(value="/sample/{nihdiNumber}/{enterpriseNumber}")
   public ResponseEntity<StreamingResponseBody> getSample(
                              @PathVariable("nihdiNumber") String nihdiNumber,  
                              @PathVariable("enterpriseNumber") String enterpriseNumber){

      ThirdPartyPayer result = service.getSample(nihdiNumber, enterpriseNumber);

      StreamingResponseBody body = outputStream -> {
               objectMapper.writeValue(outputStream, result);  
               outputStream.flush(); 
        };

        return (ResponseEntity<StreamingResponseBody>) ResponseEntity.ok()
               .contentType(MediaType.parseMediaType("application/json"))
               .header(HttpHeaders.TRANSFER_ENCODING, "chunked")
               .body(body);
      };

}


