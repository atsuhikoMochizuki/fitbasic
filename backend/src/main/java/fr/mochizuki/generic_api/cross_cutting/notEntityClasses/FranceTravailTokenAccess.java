package fr.mochizuki.generic_api.cross_cutting.notEntityClasses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FranceTravailTokenAccess {

    private String access_token;
    private String scope;
    private String token_type;
    private Integer expires_in;
}
