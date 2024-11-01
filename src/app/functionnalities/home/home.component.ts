import { Component} from '@angular/core';
import { environment as env } from '../../../environments/environment.development';
import { CommonModule } from '@angular/common';

/**
 * ${1:Description placeholder}
 *
 * @export
 * @class HomeComponent
 * @typedef {HomeComponent}
 */
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls:[
    '../../../styles.css'
  ]
})
export class HomeComponent{
  slogan:string=env.APP_SLOGAN;
  subtitle:string=env.APP_SUBTITLE;
  description:string= env.APP_DESCRIPTION;

  stylePropertiesInjectionObject = {
    'background-color': env.COLORS_BACKGROUND,
    'color': env.COLORS_TEXT
  }
}
