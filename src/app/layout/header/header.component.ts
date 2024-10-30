import { Component } from '@angular/core';
import { environment } from '../../../environments/environment.development';
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
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

/**
 * ${1:Description placeholder}
 *
 * @type {${2:*}}
 */
appTitle=environment.visu.APP_TITLE;
properties={
  'backgroundColor':environment.visu.COLORS_BACKGROUND,
  'height':'15vh'
}
}
