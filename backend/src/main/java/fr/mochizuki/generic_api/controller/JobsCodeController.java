package fr.mochizuki.generic_api.controller;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.mochizuki.generic_api.cross_cutting.constants.Endpoint;
import fr.mochizuki.generic_api.cross_cutting.constants.FranceTravail;
import fr.mochizuki.generic_api.cross_cutting.exceptions.FranceTravailAccessTokenGenerationException;
import fr.mochizuki.generic_api.cross_cutting.exceptions.ObtainClosestJobTitleFromFreeTextException;
import fr.mochizuki.generic_api.cross_cutting.notEntityClasses.Appelation;
import fr.mochizuki.generic_api.cross_cutting.notEntityClasses.JobsCodeOptions;
import fr.mochizuki.generic_api.cross_cutting.notEntityClasses.JobsCodeRequestObject;
import fr.mochizuki.generic_api.cross_cutting.security.impl.JwtServiceImpl;
import fr.mochizuki.generic_api.service.impl.JobsCodeServiceImpl;
import fr.mochizuki.generic_api.service.impl.UserServiceImpl;

/**
 * Controller for account user routes
 *
 * @author A.Mochizuki
 * @date 10/04/2024
 */

/*
 * Nota:
 * The @RestController annotation is a specialized version of the @Controller
 * annotation in Spring MVC.
 * It combines the functionality of the @Controller and @ResponseBody
 * annotations, which simplifies
 * the implementation of RESTful web services.
 * When a class is annotated with @RestController, the following points apply:
 * -> It acts as a controller, handling client requests.
 * -> The @ResponseBody annotation is automatically included, allowing the
 * automatic serialization
 * of the return object into the HttpResponse.
 */
@RestController
public class JobsCodeController {
    private final JobsCodeServiceImpl jobsCodeService;

     // Needed for security context
     @SuppressWarnings("unused")
     private final AuthenticationManager authenticationManager;
 
     @SuppressWarnings("unused")
     private final UserServiceImpl userService;
 
     @SuppressWarnings("unused")
     private final JwtServiceImpl jwtService;
 
     public JobsCodeController(JobsCodeServiceImpl jobsCodeService, AuthenticationManager authenticationManager,
             UserServiceImpl userService, JwtServiceImpl jwtService) {
         this.jobsCodeService = jobsCodeService;
         this.authenticationManager = authenticationManager;
         this.userService = userService;
         this.jwtService = jwtService;
     }


    @PostMapping(Endpoint.JOBCODE)
    public ResponseEntity<String> getAIresponse(@RequestBody String freeInputText) throws JsonMappingException, RestClientException, JsonProcessingException, FranceTravailAccessTokenGenerationException, ObtainClosestJobTitleFromFreeTextException{
        
        String token = this.jobsCodeService.generateFranceTravailAccessToken();
        
        Appelation appelation = Appelation.builder()
                            .intitule(freeInputText)
                            .identifiant(FranceTravail.ROMEO_FUNCTIONAL_ID)
                            .contexte("")
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
        return ResponseEntity
            .status(200)
            .body(stringlifiedResponse);
    }
}
