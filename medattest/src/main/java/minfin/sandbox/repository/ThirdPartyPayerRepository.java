package minfin.sandbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import minfin.sandbox.model.ThirdPartyPayer;

@Repository
public interface ThirdPartyPayerRepository extends JpaRepository<ThirdPartyPayer, Long> {

   ThirdPartyPayer findOneByNihdiNumberAndEnterpriseNumber(String nihdiNumber, String enterpriseNumber);


}
