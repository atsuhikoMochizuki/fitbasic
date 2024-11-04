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

    // Authentication
    {
        path:'register',
        loadComponent:()=>import('./functionnalities/auth/register/register.component').then(module=>module.RegisterComponent),
        pathMatch:'full'
    },

    // Footer tabs
    {
        path: 'legal-mentions',
        loadComponent:()=> import('./functionnalities/footer-tabs/legal-mentions/legal-mentions.component').then(module=>module.LegalMentionsComponent),
        pathMatch:'full'
    },
    {
        path: "cgu",
        loadComponent:()=> import('./functionnalities/footer-tabs/cgu/cgu.component').then(module=>module.CguComponent),
        pathMatch:'full'
    },
    {
        path:"privacy-policy",
        loadComponent: ()=> import('./functionnalities/footer-tabs/privacy-policy/privacy-policy.component').then(module=>module.PrivacyPolicyComponent),
        pathMatch:'full'
    },
    {
        path:'privacy-cookies',
        loadComponent:()=>import('./functionnalities/footer-tabs/cookies-policy/cookies-policy.component').then(module=>module.CookiesPolicyComponent),
        pathMatch:'full'
    },
    {
        path:'contact',
        loadComponent:()=>import('./functionnalities/footer-tabs/contact/contact.component').then(module=>module.ContactComponent),
        pathMatch:'full'
    },

    {
        path:'page-not-found',
        loadComponent:()=> import('./functionnalities/page-not-found/page-not-found.component').then(module=>module.PageNotFoundComponent),
        pathMatch:'full'
    },

    // Redirections
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

