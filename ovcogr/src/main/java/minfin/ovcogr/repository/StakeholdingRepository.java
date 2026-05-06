package minfin.ovcogr.repository;

import minfin.ovcogr.model.Stakeholding;
import minfin.ovcogr.dto.StakeholdingDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface StakeholdingRepository extends JpaRepository<Stakeholding, Integer> {

    @Query(value= """
            WITH RECURSIVE LINKS (LEVEL,PARENTBCE,CHILDBCE) AS (
                SELECT 
                    0 AS LEVEL,
                    PARENTBCE,
                    CHILDBCE
                FROM PUBLIC.PARTICIPATION 
                    WHERE CHILDBCE = :bce
                UNION ALL
                SELECT
                    LINKS.LEVEL+1 AS LEVEL,
                    P.PARENTBCE,
                    P.CHILDBCE
                FROM PUBLIC.PARTICIPATION AS P JOIN LINKS ON LINKS.PARENTBCE=P.CHILDBCE
                WHERE LINKS.LEVEL+1 <  LEVEL + 4 --AND P.CHILDBCE IS NOT NULL
            )
            SELECT DISTINCT PARENTBCE FROM LINKS            
            """,
            nativeQuery = true)
    List<String> getParentsForBce(@Param("bce") String bce);

    @Query(value= """
            WITH RECURSIVE LINKS (LEVEL,PARENTBCE,CHILDBCE) AS (
                SELECT 
                    0 AS LEVEL,
                    PARENTBCE,
                    CHILDBCE
                FROM PUBLIC.PARTICIPATION 
                    WHERE CHILDBCE IN :listOfBce
                UNION ALL
                SELECT
                    LINKS.LEVEL+1 AS LEVEL,
                    P.PARENTBCE,
                    P.CHILDBCE
                FROM PUBLIC.PARTICIPATION AS P JOIN LINKS ON LINKS.PARENTBCE=P.CHILDBCE
                WHERE LINKS.LEVEL+1 <  LEVEL + 4 --AND P.CHILDBCE IS NOT NULL
            )
            SELECT DISTINCT PARENTBCE FROM LINKS            
            """,
            nativeQuery = true)
    List<String> getParentsForListOfBce(@Param("listOfBce") List<String> listOfBce);



    @Query(value = """
            WITH RECURSIVE LINKS (LEVEL,PARENTBCE,CHILDBCE) AS (
                SELECT 
                    -1 AS LEVEL,
                    PARENTBCE,
                    CHILDBCE
                FROM PUBLIC.PARTICIPATION 
                WHERE PARENTBCE = :bce 
                UNION ALL
                SELECT
                    LINKS.LEVEL-1 AS LEVEL,  
                    C.PARENTBCE,
                    C.CHILDBCE
                FROM PUBLIC.PARTICIPATION AS C JOIN LINKS ON LINKS.CHILDBCE=C.PARENTBCE
                WHERE LINKS.LEVEL-1 > -4 --AND C.PARENTBCE IS NOT NULL
            )
            SELECT DISTINCT CHILDBCE FROM LINKS 
            """,
            nativeQuery = true)
    List<String> getChildrenForBce(@Param("bce") String bce);



    @Query(value = """
        WITH RECURSIVE LINKS (LEVEL,PARENTBCE,CHILDBCE) AS (
            SELECT 
                -1 AS LEVEL,
                PARENTBCE,
                CHILDBCE
            FROM PUBLIC.PARTICIPATION 
            WHERE PARENTBCE IN :listOfBce 
            UNION ALL
            SELECT
                LINKS.LEVEL-1 AS LEVEL,  
                C.PARENTBCE,
                C.CHILDBCE
            FROM PUBLIC.PARTICIPATION AS C JOIN LINKS ON LINKS.CHILDBCE=C.PARENTBCE
            WHERE LINKS.LEVEL-1 > -4 --AND C.PARENTBCE IS NOT NULL
        )
        SELECT DISTINCT CHILDBCE FROM LINKS 
        """,
        nativeQuery = true)
    List<String> getChildrenForListOfBce(@Param("listOfBce") List<String> listOfBce);



    @Query(value = """
            WITH RECURSIVE LINKS (LEVEL,PARENTBCE,PARENTNAME,COUNTRY,CHILDBCE,CHILDNAME,ENDSTAKEHOLDING,DIRECT,INDIRECT) AS (
            SELECT
            	-1 AS LEVEL, 
            	PARENTBCE,
            	PARENTNAME,
            	COUNTRY,
            	CHILDBCE,
            	CHILDNAME,
            	ENDSTAKEHOLDING,
            	DIRECT,
            	INDIRECT
            FROM PUBLIC.PARTICIPATION
            WHERE PARENTBCE IN  :listOfBce    
            UNION ALL
            SELECT
            	LINKS.LEVEL-1 AS LEVEL,
            	C.PARENTBCE,
            	C.PARENTNAME,
            	C.COUNTRY,
            	C.CHILDBCE,
            	C.CHILDNAME,
            	C.ENDSTAKEHOLDING,
            	C.DIRECT,
            	C.INDIRECT
            FROM PUBLIC.PARTICIPATION AS C JOIN LINKS ON LINKS.CHILDBCE=C.PARENTBCE
            WHERE LINKS.LEVEL-1 > -4 
            )
            SELECT DISTINCT PARENTBCE,PARENTNAME,COUNTRY,CHILDBCE,CHILDNAME,ENDSTAKEHOLDING,DIRECT,INDIRECT FROM LINKS
    """,
            nativeQuery = true)
    List<StakeholdingDto> getChildrenStakeholdingDtoForlistOfBce(@Param("listOfBce") List<String> listOfBce);

    @Query(value = """
    SELECT DISTINCT  
        PARENTBCE,
        PARENTNAME,
        COUNTRY,
        CHILDBCE,
        CHILDNAME,
        ENDSTAKEHOLDING,
        DIRECT,
        INDIRECT
    FROM PUBLIC.PARTICIPATION
    WHERE PARENTBCE IN  :listOfBce    
""", nativeQuery = true)
    List<StakeholdingDto> getStakeholdingDtosForListOfBce(@Param("listOfBce") List<String> listOfBce);
}
