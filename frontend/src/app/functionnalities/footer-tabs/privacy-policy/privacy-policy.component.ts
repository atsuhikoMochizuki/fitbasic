import { Component } from '@angular/core';
import { environment as env } from '../../../../environments/environment.development';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-privacy-policy',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './privacy-policy.component.html',
  styleUrl: '../../../../styles.css'
})
export class PrivacyPolicyComponent {
  stylePropertiesInjectionObject = {
    'background-color': env.COLORS_BACKGROUND,
    'color': env.COLORS_TEXT
  }

  informationInjectObject = {
    companyOrSiteManagerName: env.SITE_EDITOR_COMPANY_OR_SITE_MANAGER_NAME,
    siteManagerFirstName: env.SITE_EDITOR_SITE_MANAGER_FIRST_NAME,
    addressOfTheRegistereOfficeOrDomicileOfSiteResponsible: env.SITE_EDITOR_ADDRESS_OF_THE_REGISTERED_OFFICE_OR_DOMICILE_OF_SITE_RESPONSIBLE,
    immatriculationRneOrCs: env.SITE_EDITOR_IMMATRICULATION_RNE_OR_CS,
    intracommunityTvaNumber: env.SITE_EDITOR_INTRACOMMUNITY_TVA_NUMBER,
    companyNameOrSiteResponsible:env.COMPANY_NAME_OR_SITE_RESPONSIBLE,
    companyEmail:env.COMPANY_EMAIL,
    companyPhoneNumber:env.COMPANY_PHONE_NUMBER
  }
}
