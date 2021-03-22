import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IssueCertificateComponent } from './components/issue-certificate/issue-certificate.component';
import { IssueRootCertificateComponent } from './components/issue-root-certificate/issue-root-certificate.component';
import { ListCertificatesComponent } from './components/list-certificates/list-certificates.component';

const routes: Routes = [
  {
    path: '',
    component: ListCertificatesComponent,
    pathMatch: 'full'
  },
  {
    path: 'certificates/all-certificates',
    component: ListCertificatesComponent,
    pathMatch: 'full'
  },
  {
    path: 'certificates/issue-cert',
    component: IssueCertificateComponent,
    pathMatch: 'full'
  },
  {
    path: 'certificates/issue-root-cert',
    component: IssueRootCertificateComponent,
    pathMatch: 'full'
  }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
