package fr.mochizuki.generic_api.cross_cutting.notEntityClasses;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobsCodeRequestObject {
    private ArrayList<Appelation> appellations;
    private JobsCodeOptions options;
}
