import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment.development';

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
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent{
  /**
 * ${1:Description placeholder}
 *
 * @type {string}
 */
slogan:string=environment.visu.APP_SLOGAN;
/**
 * ${1:Description placeholder}
 *
 * @type {string}
 */
subtitle:string=environment.visu.APP_SUBTITLE;
/**
 * ${1:Description placeholder}
 *
 * @type {string}
 */
description:string= environment.visu.APP_DESCRIPTION;
}
