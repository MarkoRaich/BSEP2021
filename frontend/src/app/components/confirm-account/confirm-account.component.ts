import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-confirm-account',
  templateUrl: './confirm-account.component.html',
  styleUrls: ['./confirm-account.component.css']
})
export class ConfirmAccountComponent implements OnInit {


  token : string;

  success: boolean;

  constructor(private activatedRoute: ActivatedRoute,
              private userService: UserService,
              private toastr: ToastrService          ) {  
              
                  this.activatedRoute.queryParams.subscribe(params => {
                  
                  let data = params['token'];
                  this.token = data;
            });
  }

  ngOnInit(): void {

    this.confirmAccount();
    
  }


  confirmAccount(): void {
    this.userService.confirmAccount(this.token).subscribe(
        (data: boolean) => {
          if(data == true){
            this.success = true;
          } else {
            this.success = false;
          }
          
        },
        () => {
          this.toastr.error("Došlo je do greške...");
        }
      )  
  }

}
