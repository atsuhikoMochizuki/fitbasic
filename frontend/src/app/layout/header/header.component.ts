import { Component } from '@angular/core';
import { environment as env } from '../../../environments/environment.development';
import { NavigationComponent } from "../navigation/navigation.component";
import { CommonModule } from '@angular/common';

/**
 * ${1:Description placeholder}
 *
 * @export
 * @class HeaderComponent
 * @typedef {HeaderComponent}
 */
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule,NavigationComponent],
  templateUrl: './header.component.html'
})
export class HeaderComponent {

/**
 * ${1:Description placeholder}
 *
 * @type {${2:*}}
 */
appTitle=env.APP_TITLE;

stylePropertiesInjectionObject={
  'backgroundColor':env.COLORS_BACKGROUND
}
}
