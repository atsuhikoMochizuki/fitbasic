import { Component} from '@angular/core';
import { environment as env } from '../../../environments/environment.development';

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
  imports: [],
  templateUrl: './home.component.html'
})
export class HomeComponent{
  slogan:string=env.APP_SLOGAN;
  subtitle:string=env.APP_SUBTITLE;
  description:string= env.APP_DESCRIPTION;
}
