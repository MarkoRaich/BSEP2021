import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AngularMaterialModule } from './angular-material/angular-material.module';
import { ToastrModule } from 'ngx-toastr';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ListCertificatesComponent } from './components/list-certificates/list-certificates.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CertificateDetailsComponent } from './components/certificate-details/certificate-details.component';
import { IssueCertificateComponent } from './components/issue-certificate/issue-certificate.component';
import { IssueRootCertificateComponent } from './components/issue-root-certificate/issue-root-certificate.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { NonAuthenticatedComponent } from './components/non-authenticated/non-authenticated.component';
import { NonAuthorizedComponent } from './components/non-authorized/non-authorized.component';
import { AdminGuard } from './guards/admin.guard';
import { UserGuard } from './guards/user.guard';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { ErrorInterceptor } from './interceptors/error.interceptor';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import { DownloadCertificateComponent } from './components/download-certificate/download-certificate.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { ConfirmAccountComponent } from './components/confirm-account/confirm-account.component';
import { NewPasswordComponent } from './components/new-password/new-password.component';

@NgModule({
  declarations: [
    AppComponent,
    ListCertificatesComponent,
    CertificateDetailsComponent,
    IssueCertificateComponent,
    IssueRootCertificateComponent,
    LoginComponent,
    RegisterComponent,
    NonAuthenticatedComponent,
    NonAuthorizedComponent,
    RegisterUserComponent,
    DownloadCertificateComponent,
    ResetPasswordComponent,
    ConfirmAccountComponent,
    NewPasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatNativeDateModule,
    AngularMaterialModule,
    ToastrModule.forRoot({
      timeOut: 2000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
    BrowserAnimationsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    AdminGuard,
    UserGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
