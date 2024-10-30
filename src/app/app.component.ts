import { height } from './../../node_modules/@fortawesome/free-brands-svg-icons/faAccessibleIcon.d';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from "./layout/header/header.component";
import { FooterComponent } from "./layout/footer/footer.component";
import { environment } from '../environments/environment.development';
import { CommonModule} from '@angular/common';

/**
 * ${1:Description placeholder}
 *
 * @export
 * @class AppComponent
 * @typedef {AppComponent}
 */
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, FooterComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  })
export class AppComponent {
 
  /**
 * ${1:Description placeholder}
 *
 * @type {{ 'background-color': any; color: any; height: string; }\}
 */
styleObject = {
  'background-color': environment.visu.COLORS_BACKGROUND,
  'color':environment.visu.COLORS_TEXT,
  'height':'85vh'
  };
}
