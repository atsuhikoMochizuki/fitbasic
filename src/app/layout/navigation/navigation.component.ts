import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { environment as env} from '../../../environments/environment.development';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navigation.component.html'
})
export class NavigationComponent {
  stylePropertiesInjectionObject=
    {
      'color':env.COLORS_TEXT,
      'background-color':env.COLORS_BACKGROUND,
      
      
    };
  title=env.APP_TITLE;
}
