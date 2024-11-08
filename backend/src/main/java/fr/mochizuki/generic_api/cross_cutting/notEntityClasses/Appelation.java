package fr.mochizuki.generic_api.cross_cutting.notEntityClasses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Appelation {
    private String intitule; // Free entered text
    private String identifiant;
    private String contexte;
}
