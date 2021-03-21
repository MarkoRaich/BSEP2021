import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
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
  }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
