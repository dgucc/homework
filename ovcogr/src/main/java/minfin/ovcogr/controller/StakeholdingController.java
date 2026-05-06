package minfin.ovcogr.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import minfin.ovcogr.model.Stakeholding;
import minfin.ovcogr.dto.StakeholdingDto;
import minfin.ovcogr.service.StakeholdingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stk")
@RequiredArgsConstructor
public class StakeholdingController {

    private final StakeholdingService stakeholdingService;

    @GetMapping("/test/up/{bce}")
    public ResponseEntity<List<String>> getParentsForBce(@PathVariable("bce") String bce){
        return new ResponseEntity<>(stakeholdingService.getParentsForBce(bce), HttpStatus.OK);
    }

    @GetMapping("/test/down/{bce}")
    public ResponseEntity<List<String>> getChildrenForBce(@PathVariable("bce") String bce){
        return new ResponseEntity<>(stakeholdingService.getchildrenForBce(bce), HttpStatus.OK);
    }

    @PostMapping("/test/down/all")
    public ResponseEntity<List<StakeholdingDto>> getAllChildrenForlistOfBce(@RequestBody List<String> listOfBce){
        return new ResponseEntity<>(stakeholdingService.getAllChildrenForlistOfBce(listOfBce), HttpStatus.OK);
    }

    @GetMapping("/{bce}")
    public ResponseEntity<List<StakeholdingDto>> getAllRelatedCompaniesForBce(@PathVariable("bce") String bce){
        return new ResponseEntity<List<StakeholdingDto>>(stakeholdingService.getAllRelatedCompaniesForBce(bce), HttpStatus.OK);
    }


}
