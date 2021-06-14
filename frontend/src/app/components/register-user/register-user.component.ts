import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Entity } from 'src/app/models/entity';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {

  registerForm: FormGroup;

  entity : Entity;

  submitted = false;

  constructor(   private toastr : ToastrService, 
                 private formBuilder : FormBuilder,
                 private userService: UserService,
                 private router: Router) { }

  ngOnInit(): void {

    this.registerForm = this.formBuilder.group({
      email : new FormControl(null, [Validators.required, Validators.email]),
      password : new FormControl(null, [Validators.required, Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^\w\s]).{10,}$') ] ),
      repeatedPassword :  new FormControl(null, [Validators.required, Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^\w\s]).{10,}$') ] ),
      commonName : new FormControl(null, [Validators.required]),
      firstName : new FormControl(null, [Validators.required, Validators.pattern('[ a-zA-Z]*')]),
      lastName : new FormControl(null, [Validators.required, Validators.pattern('[ a-zA-Z]*')]),
      organization : new FormControl(null, [Validators.required, Validators.pattern('[ a-zA-Z0-9_]*')]),
      organizationUnit : new FormControl(null, [Validators.required, Validators.pattern('[ a-zA-Z0-9_]*')]),
      state : new FormControl(null, [Validators.required, Validators.pattern('[ a-zA-Z]*')]),
      country : new FormControl(null, [Validators.required, Validators.pattern('[ a-zA-Z]*')]),
      question : new FormControl(null, [Validators.required]),
      answer : new FormControl(null, [Validators.required])
    })


  }

   // convenience getter for easy access to form fields
   get f() { return this.registerForm.controls; }

   onSubmit() {

      this.submitted = true;

      if(this.registerForm.invalid){
        return;
      }

      if(this.registerForm.value.password != this.registerForm.value.repeatedPassword ){
        this.toastr.error("Lozinka i ponovljena lozinka se ne poklapaju!");
        return;
      }

      this.entity = new Entity(this.registerForm.value.email,
                              this.registerForm.value.password,
                              this.registerForm.value.commonName,
                              this.registerForm.value.firstName,
                              this.registerForm.value.lastName,
                              this.registerForm.value.organization,
                              this.registerForm.value.organizationUnit,
                              this.registerForm.value.state,
                              this.registerForm.value.country,
                              this.registerForm.value.question,
                              this.registerForm.value.answer
                        )

      this.userService.registerUser(this.entity).subscribe(
        () => {
          this.toastr.success("Uspešno Ste se registrovali. Na Vašu email adresu je poslata potvrda. Aktivirajte nalog klikom na link u emailu");
          this.router.navigate(['login']);
        },
        (error) => {
          this.toastr.error("Došlo je do greške...");
        }
      )


   }

    onReset() {
        this.submitted = false;
        this.registerForm.reset();
    }

}
