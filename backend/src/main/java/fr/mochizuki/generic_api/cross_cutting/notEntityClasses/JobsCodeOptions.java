package fr.mochizuki.generic_api.cross_cutting.notEntityClasses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobsCodeOptions {
    private String nomAppelant;
    private Integer nbResultats;
    private double seuilScorePrediction;
}
