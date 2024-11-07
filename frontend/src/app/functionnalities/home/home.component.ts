import { ToastrService } from 'ngx-toastr';
import { Component, OnInit} from '@angular/core';
import { environment as env } from '../../../environments/environment.development';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

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
  imports: [CommonModule,RouterModule],
  templateUrl: './home.component.html',
  styleUrls:[
    '../../../styles.css'
  ]
})
export class HomeComponent implements OnInit{
  
  constructor(private toastr: ToastrService){}

  ngOnInit(): void {
    this.toastr.success("Soyez le bienvenu","Bienvenue");
  }
  
  slogan:string=env.APP_SLOGAN;
  subtitle:string=env.APP_SUBTITLE;
  description:string= env.APP_DESCRIPTION;

  stylePropertiesInjectionObject = {
    'background-color': env.COLORS_BACKGROUND,
    'color': env.COLORS_TEXT
  }
}
