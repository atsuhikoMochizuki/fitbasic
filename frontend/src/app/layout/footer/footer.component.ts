import { Component } from '@angular/core';
import { environment as env } from '../../../environments/environment.development';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faTwitter, faFacebook, faLinkedin, faInstagram } from '@fortawesome/free-brands-svg-icons';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
/**
 * ${1:Description placeholder}
 *
 * @export
 * @class FooterComponent
 * @typedef {FooterComponent}
 */
@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [FontAwesomeModule, RouterModule, CommonModule],
  templateUrl: './footer.component.html'
})
export class FooterComponent {
  title = env.APP_TITLE;
  currentYear: number = new Date().getFullYear();
  faTwitter = faTwitter;
  faFacebook = faFacebook;
  faLinkedin = faLinkedin;
  faInstagram = faInstagram;

  stylePropertiesInjectionObject = {
    'background-color': env.COLORS_BACKGROUND,
    'color': env.COLORS_TEXT,
    'text-decoration': 'none',
  }
}
