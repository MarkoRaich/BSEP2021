import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { CertificateService } from 'src/app/services/certificate.service';

@Component({
  selector: 'app-issue-root-certificate',
  templateUrl: './issue-root-certificate.component.html',
  styleUrls: ['./issue-root-certificate.component.css']
})
export class IssueRootCertificateComponent implements OnInit {

  dateForm: FormGroup;
  minDate = new Date();

  

  constructor(private toastr : ToastrService, 
    private formBuilder : FormBuilder,
    private certificateService: CertificateService) { }

  ngOnInit(): void {

    this.minDate.setDate(this.minDate.getDate() + 1);

    this.dateForm = this.formBuilder.group({
      dateFrom: new FormControl(null, [Validators.required]),
      dateTo: new FormControl(null, [Validators.required]),
      
    }, {
      validator: TimeValidator('dateFrom', 'dateTo')
    });

  }

}





function TimeValidator(controlName: string, matchingControlName: string) {
  return (formGroup: FormGroup) => {

      if (formGroup.controls[matchingControlName].errors && !formGroup.controls[matchingControlName].errors.mustMatch) {
          return;
      }

      if (formGroup.controls[controlName].value >= formGroup.controls[matchingControlName].value) {
          formGroup.controls[matchingControlName].setErrors({ timeError: true });
      } else {
          formGroup.controls[matchingControlName].setErrors(null);
      }
  };
}

