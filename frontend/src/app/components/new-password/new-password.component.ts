import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { NewPass } from 'src/app/models/newPass';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-new-password',
  templateUrl: './new-password.component.html',
  styleUrls: ['./new-password.component.css']
})
export class NewPasswordComponent implements OnInit {

  token : string;

  submitted = false;

  newPass: NewPass;

  emailForm: FormGroup;

  constructor(private activatedRoute: ActivatedRoute,
              private formBuilder : FormBuilder,
              private router: Router,
              private userService: UserService,
              private toastr: ToastrService ) {

                this.activatedRoute.queryParams.subscribe(params => {
                
                  let data = params['token'];
                  this.token = data;

               });
  }

  ngOnInit(): void {
    this.emailForm = this.formBuilder.group({
      password : new FormControl(null, [Validators.required, Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^\w\s]).{10,}$') ] ),
      repeatedPassword :  new FormControl(null, [Validators.required, Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^\w\s]).{10,}$') ] )
    })
  }

   // convenience getter for easy access to form fields
   get f() { return this.emailForm.controls; }

  onSubmit(){

    this.submitted = true;

    if(this.emailForm.invalid){
      return;
    }

    if(this.emailForm.value.password != this.emailForm.value.repeatedPassword ){
      this.toastr.error("Lozinka i ponovljena lozinka se ne poklapaju!");
      return;
    }

    this.newPass = new NewPass(this.token, this.emailForm.value.password);

    this.userService.newPassword(this.newPass).subscribe(
      () => {
          this.toastr.success("Uspešno Ste izmenili lozinku. ");
          this.router.navigate(['login']);
      },
      (error) => {
        this.toastr.error("Došlo je do greške...");
      }
    )
  }

  onReset() {
    this.submitted = false;
    this.emailForm.reset();
}

}
