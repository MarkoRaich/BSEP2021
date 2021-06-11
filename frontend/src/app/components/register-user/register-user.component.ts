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
      repeatedPassword :  new FormControl(null, [Validators.required]),
      commonName : new FormControl(null, [Validators.required]),
      firstName : new FormControl(null, [Validators.required]),
      lastName : new FormControl(null, [Validators.required]),
      organization : new FormControl(null, [Validators.required]),
      organizationUnit : new FormControl(null, [Validators.required]),
      state : new FormControl(null, [Validators.required]),
      country : new FormControl(null, [Validators.required]),
       
    })


  }

   // convenience getter for easy access to form fields
   get f() { return this.registerForm.controls; }

   onSubmit() {

      if(this.registerForm.value.password != this.registerForm.value.repeatedPassword){
        this.toastr.error("Lozinka i ponovljena lozinka se ne smeju razlikovati. Pokušajte ponovo.");
      }

      this.entity = new Entity(this.registerForm.value.email,
                              this.registerForm.value.password,
                              this.registerForm.value.commonName,
                              this.registerForm.value.firstName,
                              this.registerForm.value.lastName,
                              this.registerForm.value.organization,
                              this.registerForm.value.organizationUnit,
                              this.registerForm.value.state,
                              this.registerForm.value.country        
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

}
