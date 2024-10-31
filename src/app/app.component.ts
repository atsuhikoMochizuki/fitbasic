import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from "./layout/header/header.component";
import { FooterComponent } from "./layout/footer/footer.component";
import { CommonModule} from '@angular/common';
import { environment as env} from '../environments/environment.development';

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
  templateUrl: './app.component.html'
  })
export class AppComponent {

  stylePropertiesInjectionObject={
  'background-color': env.COLORS_BACKGROUND,
  'color':env.COLORS_TEXT,
  'height':'100vh'
  }
}
