import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConfirmAccountComponent } from './components/confirm-account/confirm-account.component';
import { DownloadCertificateComponent } from './components/download-certificate/download-certificate.component';
import { IssueCertificateComponent } from './components/issue-certificate/issue-certificate.component';
import { IssueRootCertificateComponent } from './components/issue-root-certificate/issue-root-certificate.component';
import { ListCertificatesComponent } from './components/list-certificates/list-certificates.component';
import { LoginComponent } from './components/login/login.component';
import { NewPasswordComponent } from './components/new-password/new-password.component';
import { NonAuthenticatedComponent } from './components/non-authenticated/non-authenticated.component';
import { NonAuthorizedComponent } from './components/non-authorized/non-authorized.component';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import { RegisterComponent } from './components/register/register.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { AdminGuard } from './guards/admin.guard';
import { UserGuard } from './guards/user.guard';

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
    component: RegisterUserComponent,
    pathMatch: 'full'
  },
  {
    path: 'reset-password',
    component: ResetPasswordComponent,
    pathMatch: 'full'
  }, 
  {
    path: 'new-password',
    component: NewPasswordComponent,
    pathMatch: 'full'
  },
  {
    path: 'confirm-account',
    component: ConfirmAccountComponent,
    pathMatch: 'full'
  },
  {
    path: 'register/admin',
    component: RegisterComponent,
    pathMatch: 'full',
    canActivate: [AdminGuard]
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
    {
    path: 'certificates/download-certificate',
    component: DownloadCertificateComponent,
    pathMatch: 'full',
    canActivate: [UserGuard]
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
