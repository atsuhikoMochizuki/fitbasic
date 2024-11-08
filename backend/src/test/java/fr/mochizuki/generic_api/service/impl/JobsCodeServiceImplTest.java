package fr.mochizuki.generic_api.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.mochizuki.generic_api.cross_cutting.constants.FranceTravail;
import fr.mochizuki.generic_api.cross_cutting.exceptions.FranceTravailAccessTokenGenerationException;
import fr.mochizuki.generic_api.cross_cutting.exceptions.ObtainClosestJobTitleFromFreeTextException;
import fr.mochizuki.generic_api.cross_cutting.notEntityClasses.Appelation;
import fr.mochizuki.generic_api.cross_cutting.notEntityClasses.JobsCodeOptions;
import fr.mochizuki.generic_api.cross_cutting.notEntityClasses.JobsCodeRequestObject;

import java.util.ArrayList;
/**
 * Unit tests of JobsCodeService
 * 
 * @author A.Mochizuki
 * @date 2024-10-17 
 */
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class JobsCodeServiceImplTest {

    @InjectMocks
    private JobsCodeServiceImpl jobsCodeService;

    @Test
    void generateFranceTravailAccessToken_shouldReturnSuccess() throws FranceTravailAccessTokenGenerationException, JsonMappingException, RestClientException, JsonProcessingException{
        String franceTravailAccessToken = this.jobsCodeService.generateFranceTravailAccessToken();
        assertThat(franceTravailAccessToken).isNotNull();
    }

    @Test
    void obtainClosestJobTitleFromFreeText_shouldReturn200() throws JsonProcessingException, ObtainClosestJobTitleFromFreeTextException, RestClientException, FranceTravailAccessTokenGenerationException{

        String token = this.jobsCodeService.generateFranceTravailAccessToken();
        
        Appelation appelation = Appelation.builder()
                            .intitule("Boucher")
                            .identifiant(FranceTravail.ROMEO_FUNCTIONAL_ID)
                            .contexte("Commerce de détail de viandes et de produits à base de viande en magasin spécialisé")
                            .build();

        ArrayList<Appelation> appellations = new ArrayList<>();
        appellations.add(appelation);

        JobsCodeOptions options = JobsCodeOptions.builder()
        .nomAppelant("francetravail")
        .nbResultats(2)
        .seuilScorePrediction(0.7)
        .build();

        JobsCodeRequestObject requestObject = 
            JobsCodeRequestObject.builder()
            .appellations(appellations)
            .options(options)
            .build();

        String stringlifiedResponse = this.jobsCodeService.obtainClosestJobTitleFromFreeText(requestObject,token);

        assertThat(stringlifiedResponse).contains("metiersRome");
        
    }
}
