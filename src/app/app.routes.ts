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
        path: "cgu",
        loadComponent:()=> import('./functionnalities/cgu/cgu.component').then(module=>module.CguComponent),
        pathMatch:'full'
    },
    {
        path:"privacy-policy",
        loadComponent: ()=> import('./functionnalities/privacy-policy/privacy-policy.component').then(module=>module.PrivacyPolicyComponent),
        pathMatch:'full'
    },
    {
        path:'privacy-cookies',
        loadComponent:()=>import('./functionnalities/cookies-policy/cookies-policy.component').then(module=>module.CookiesPolicyComponent),
        pathMatch:'full'
    },
    {
        path:'contact',
        loadComponent:()=>import('./functionnalities/contact/contact.component').then(module=>module.ContactComponent),
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

