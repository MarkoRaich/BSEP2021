import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CertDetails } from 'src/app/models/certDetails';
import { CertificateDTO } from 'src/app/models/certificateDTO';
import { CertificateService } from 'src/app/services/certificate.service';

@Component({
  selector: 'app-certificate-details',
  templateUrl: './certificate-details.component.html',
  styleUrls: ['./certificate-details.component.css']
})
export class CertificateDetailsComponent implements OnInit {

  cert: CertificateDTO;

  certDetails: CertDetails;

  constructor(private router: Router,
              private toastr: ToastrService,
              private certificateService: CertificateService,
              public dialogRef: MatDialogRef<CertificateDetailsComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {

    this.getDetails(this.data.certificate.serialNumberSubject);
  }

  getDetails(serialNumber){
    this.certificateService.getDetails(serialNumber).subscribe(
      (data: CertDetails) => {
        this.certDetails = data;
      }
    )
  }

}
