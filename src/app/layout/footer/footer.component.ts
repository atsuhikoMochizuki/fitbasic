import { Component } from '@angular/core';
import { environment } from '../../../environments/environment.development';
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
  imports: [FontAwesomeModule,RouterModule,CommonModule],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {
 title=environment.visu.APP_TITLE;
 currentYear:number = new Date().getFullYear();
  faTwitter = faTwitter;
  faFacebook = faFacebook;
  faLinkedin = faLinkedin;
  faInstagram=faInstagram;

  backGroundColor = environment.visu.COLORS_FOOTER_BACKGROUND;
  
  styleProperties = {
    'height':'10vh',  
    'background-color':environment.visu.COLORS_BACKGROUND,
    'color':environment.visu.COLORS_TEXT,
    'text-decoration':'none',
  }
}
