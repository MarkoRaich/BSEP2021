import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CertDTO } from 'src/app/models/certDTO';
import { CertificateDTO } from 'src/app/models/certificateDTO';
import { CertificateService } from 'src/app/services/certificate.service';

@Component({
  selector: 'app-issue-certificate',
  templateUrl: './issue-certificate.component.html',
  styleUrls: ['./issue-certificate.component.css']
})
export class IssueCertificateComponent implements OnInit {

  certificateForm: FormGroup;

  certificate: CertDTO;

  issuerCertificates: CertificateDTO[] = [];

  constructor(private toastr : ToastrService, 
              private formBuilder : FormBuilder,
              private certificateService: CertificateService,
              private router: Router ) { }

  ngOnInit(): void {

    this.certificateForm = this.formBuilder.group({
      issuerSerialNumber: new FormControl(null,[Validators.required]),
      certificateType: new FormControl(null,[Validators.required]),
      commonName: new FormControl(null,[Validators.required]),
      firstName: new FormControl(null,[Validators.required]),
      lastName: new FormControl(null,[Validators.required]),
      email: new FormControl(null,[Validators.required, Validators.email]),
      organization: new FormControl(null,[Validators.required]),
      organizationUnit: new FormControl(null,[Validators.required]),
      duration: new FormControl(null, [Validators.required]),
      state: new FormControl(null,[Validators.required]),
      country: new FormControl(null,[Validators.required]),
      keyUsage: new FormControl(null,[Validators.required]),
      
    });

    this.getIssuerCerts();

  }

  create(){
    this.certificate = new CertDTO(this.certificateForm.value.commonName,
                                   this.certificateForm.value.firstName,
                                   this.certificateForm.value.lastName,
                                   this.certificateForm.value.email,
                                   this.certificateForm.value.organization,
                                   this.certificateForm.value.organizationUnit,
                                   this.certificateForm.value.state,
                                   this.certificateForm.value.country,
                                   this.certificateForm.value.duration,
                                   this.certificateForm.value.issuerSerialNumber,
                                   this.certificateForm.value.certificateType,
                                   this.certificateForm.value.keyUsage,
                                   false )     

    this.certificateService.creatCAorEndCertificate(this.certificate).subscribe(
      {
        next: () => {
          this.toastr.success("Kreiran sertifikat");
          this.router.navigate(['/certificates/all-certificates']);
        },
        error: data => {
          this.toastr.error("Doslo je do greske...")
        }
      }
    )                              
  }

  getIssuerCerts(): void {
    this.certificateService.getValidCACertificates().subscribe(
      (certs: CertificateDTO[]) => {
        this.issuerCertificates = certs;
      })
  }

}

