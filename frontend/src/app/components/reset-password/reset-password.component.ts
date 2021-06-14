import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

    resetPasswordForm: FormGroup;

    submitted = false; //pomocna promenljiva za ispaljivanje gresaka
    
    error = '';
    
    constructor(
        private formBuilder: FormBuilder,
        private toastr: ToastrService,
        private router: Router,
        private userService: UserService
    ) { }


    ngOnInit() {
        this.resetPasswordForm = this.formBuilder.group({
            email:    ['', [Validators.required, Validators.email]]
        });

        
    }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.resetPasswordForm.invalid) {
            this.toastr.error("Molimo unesite ispravno podatke.", 'Reset Lozinke');
            return;
        }
        
        this.userService.resetPassword(this.resetPasswordForm.value.email).subscribe(
            () => {
                this.toastr.success("Na Vašu email adresu je poslat link za promenu lozinke");
                this.router.navigate(['login']);
              },
              (error) => {
                this.toastr.error("Došlo je do greške...");
              }
        )
    }

    // convenience getter for easy access to form fields
    get f() { return this.resetPasswordForm.controls; }

   
}
