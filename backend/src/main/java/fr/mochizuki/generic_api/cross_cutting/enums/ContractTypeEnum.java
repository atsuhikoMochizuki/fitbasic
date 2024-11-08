package fr.mochizuki.generic_api.cross_cutting.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContractTypeEnum {
    CDI,
    CDD,
    INTERIM,
    STAGE,
    FREELANCE,
    APPRENTICESHIP;
    private  String displayName;
}
