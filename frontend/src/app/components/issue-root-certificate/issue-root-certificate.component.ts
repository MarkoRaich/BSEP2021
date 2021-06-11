import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { CertDTO } from 'src/app/models/certDTO';
import { CertificateDTO } from 'src/app/models/certificateDTO';
import { KeyUsage } from 'src/app/models/key-usage';
import { CertificateService } from 'src/app/services/certificate.service';
import {Router} from '@angular/router';
import { Entity } from 'src/app/models/entity';
import { EntityService } from 'src/app/services/entity.service';


@Component({
  selector: 'app-issue-root-certificate',
  templateUrl: './issue-root-certificate.component.html',
  styleUrls: ['./issue-root-certificate.component.css']
})
export class IssueRootCertificateComponent implements OnInit {

  certificateForm: FormGroup;

  certificate: CertDTO;

  entities: Entity[] = [];

  constructor(private toastr : ToastrService, 
              private formBuilder : FormBuilder,
              private certificateService : CertificateService,
              private entityService : EntityService,
              private router: Router ) { }

  ngOnInit(): void {

    this.certificateForm = this.formBuilder.group({
      duration: new FormControl(null, [Validators.required]),
      keyUsage: new FormControl(null,[Validators.required]),
      entityId: new FormControl(null,[Validators.required])
    });

    this.getEntites();

  }

  create(){
    this.certificate = new CertDTO(this.certificateForm.value.entityId,
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

  getEntites(): void {
    this.entityService.getEntitiesWithoutActiveCertificate().subscribe(
      (data: Entity[]) => {
        this.entities = data;
      })
  }

}

