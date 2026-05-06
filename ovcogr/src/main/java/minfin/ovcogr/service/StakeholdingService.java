package minfin.ovcogr.service;

import lombok.RequiredArgsConstructor;
import minfin.ovcogr.dto.StakeholdingDto;
import minfin.ovcogr.repository.StakeholdingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StakeholdingService {

    private final StakeholdingRepository stakeholdingRepository;

    public List<String> getParentsForBce(String bce) {
        return stakeholdingRepository.getParentsForBce(bce);
    }
    public List<String> getParentsForListOfBce(List<String> listOfBce) {
        return stakeholdingRepository.getParentsForListOfBce(listOfBce);
    }

    public List<String> getchildrenForBce(String bce) {
        return stakeholdingRepository.getChildrenForBce(bce);
    }

    public List<String> getchildrenForListOfBce(List<String> listOfBce) {
        return stakeholdingRepository.getChildrenForListOfBce(listOfBce);
    }

    public List<StakeholdingDto> getAllChildrenForlistOfBce(List<String> listOfBce) {
        return stakeholdingRepository.getChildrenStakeholdingDtoForlistOfBce(listOfBce);
    }


    public List<StakeholdingDto> getAllRelatedCompaniesForBce(String bce){
        List<String> parentsInitialList = new ArrayList<>();
        List<String> childrenInitialList = new ArrayList<>();
        List<String> parentFinalList = new ArrayList<String>();
        List<String> childrenFinalList = new ArrayList<String>();
        List<String> relatedCompanies = new ArrayList<String>();
        List<StakeholdingDto> relatedStakeholdings = new ArrayList<>();

        // Ensure a viable starting point (to avoid dead end)
        parentsInitialList = getParentsForBce(bce);
        relatedCompanies.addAll(parentsInitialList);

        childrenInitialList = getchildrenForBce(bce);
        relatedCompanies.addAll(childrenInitialList);

        // Start drills up and down
        childrenFinalList = getchildrenForListOfBce(parentsInitialList);
        relatedCompanies.addAll(childrenFinalList);

        parentFinalList = getParentsForListOfBce(childrenInitialList);
        relatedCompanies.addAll(parentFinalList);

        // Remove duplicates
        Set<String> relatedCompaniesSet = new HashSet<String>(relatedCompanies);

        // Get all stakeholdings for the related companies
        relatedStakeholdings = stakeholdingRepository.getStakeholdingDtosForListOfBce(relatedCompaniesSet.stream().toList());
        Set<StakeholdingDto> stakeholdingDtoSet = new HashSet<StakeholdingDto>(relatedStakeholdings);
        
        return stakeholdingDtoSet.stream().toList();
    }
}
