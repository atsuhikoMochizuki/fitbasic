import { Routes } from '@angular/router';

/**
 * ${1:Description placeholder}
 *
 * @type {Routes}
 */
export const routes: Routes = [
    {
        path: 'home',
        loadComponent: () => import('./functionnalities/home/home.component').then(module => module.HomeComponent),
        pathMatch: 'full'
    },
    {
        path: 'legal-mentions',
        loadComponent:()=> import('./functionnalities/legal-mentions/legal-mentions.component').then(module=>module.LegalMentionsComponent),
        pathMatch:'full'
    },
    {
        path:'page-not-found',
        loadComponent:()=> import('./functionnalities/page-not-found/page-not-found.component').then(module=>module.PageNotFoundComponent),
        pathMatch:'full'
    },
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
      },
    {
        path: '**',
        redirectTo: 'page-not-found',
        pathMatch: 'full'
      }
];

