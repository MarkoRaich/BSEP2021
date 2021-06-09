import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IssueCertificateComponent } from './components/issue-certificate/issue-certificate.component';
import { IssueRootCertificateComponent } from './components/issue-root-certificate/issue-root-certificate.component';
import { ListCertificatesComponent } from './components/list-certificates/list-certificates.component';
import { LoginComponent } from './components/login/login.component';
import { NonAuthenticatedComponent } from './components/non-authenticated/non-authenticated.component';
import { NonAuthorizedComponent } from './components/non-authorized/non-authorized.component';
import { RegisterComponent } from './components/register/register.component';
import { AdminGuard } from './guards/admin.guard';

const routes: Routes = [

  
  {
    path: '',
    component: LoginComponent,
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full'
  },
  {
    path: 'register',
    component: RegisterComponent,
    pathMatch: 'full'
  },
  {
    path: 'certificates/all-certificates',
    component: ListCertificatesComponent,
    pathMatch: 'full',
    canActivate: [AdminGuard]
  },
  {
    path: 'certificates/issue-cert',
    component: IssueCertificateComponent,
    pathMatch: 'full',
    canActivate: [AdminGuard]
  },
  {
    path: 'certificates/issue-root-cert',
    component: IssueRootCertificateComponent,
    pathMatch: 'full',
    canActivate: [AdminGuard]
  },

  //***************** GRESKE i PRAVA PRISTUPA******************************

  {
    path: 'error/non-authenticated',
    component: NonAuthenticatedComponent,
  },
  {
    path: 'error/non-authorized',
    component: NonAuthorizedComponent
  },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
