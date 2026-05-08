package minfin.sandbox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import minfin.sandbox.model.ThirdPartyPayer;
import minfin.sandbox.repository.ThirdPartyPayerRepository;

@Service
public class MedattestService {

   @Autowired
   private ThirdPartyPayerRepository repository;

   public ThirdPartyPayer getSample(String nihdiNumber, String enterpriseNumber) {
      return repository.findOneByNihdiNumberAndEnterpriseNumber(nihdiNumber, enterpriseNumber);

   }
   
}
