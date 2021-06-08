import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { CertDTO } from 'src/app/models/certDTO';
import { CertificateDTO } from 'src/app/models/certificateDTO';
import { KeyUsage } from 'src/app/models/key-usage';
import { CertificateService } from 'src/app/services/certificate.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-issue-root-certificate',
  templateUrl: './issue-root-certificate.component.html',
  styleUrls: ['./issue-root-certificate.component.css']
})
export class IssueRootCertificateComponent implements OnInit {

  certificateForm: FormGroup;

  certificate: CertDTO;

  constructor(private toastr : ToastrService, 
              private formBuilder : FormBuilder,
              private certificateService: CertificateService,
              private router: Router ) { }

  ngOnInit(): void {

    this.certificateForm = this.formBuilder.group({
      commonName: new FormControl(null,[Validators.required]),
      firstName: new FormControl(null,[Validators.required, Validators.pattern('[a-zA-Z ]*')]),
      lastName: new FormControl(null,[Validators.required, Validators.pattern('[a-zA-Z ]*')]),
      email: new FormControl(null,[Validators.required, Validators.email]),
      organization: new FormControl(null,[Validators.required]),
      organizationUnit: new FormControl(null,[Validators.required]),
      duration: new FormControl(null, [Validators.required]),
      state: new FormControl(null,[Validators.required, Validators.pattern('[a-zA-Z ]*')]),
      country: new FormControl(null,[Validators.required, Validators.pattern('[a-zA-Z ]*')]),
      keyUsage: new FormControl(null,[Validators.required]),
      
    })

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
                                   "1",
                                   "SELF_SIGNED",
                                   this.certificateForm.value.keyUsage,
                                   false )     

    this.certificateService.creatRootCertificate(this.certificate).subscribe(
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

}

