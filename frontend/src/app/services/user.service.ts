import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { Admin } from "../models/admin";
import { Entity } from "../models/entity";
import { LoggedInUser } from "../models/loggedInUser";
import { NewPass } from "../models/newPass";
import { UserLoginRequest } from "../models/userLoginRequest";
import { UserTokenState } from "../models/userTokenState";

@Injectable({ providedIn: 'root' })
export class UserService {
   
   
    access_token = null;
    req: UserTokenState;
    loggedInUserSubject: BehaviorSubject<LoggedInUser>;
    loggedInUser: Observable<LoggedInUser>;
    loggedInSuccess: BehaviorSubject<LoggedInUser> = new BehaviorSubject<LoggedInUser>(null);

    question: BehaviorSubject<String> = new BehaviorSubject<String>("");

    constructor(private http: HttpClient, private router: Router) {
        this.loggedInUserSubject = new BehaviorSubject<LoggedInUser>(JSON.parse(localStorage.getItem('LoggedInUser')));
        this.loggedInUser = this.loggedInUserSubject.asObservable();
    }

  
    getLoggedInUser(): LoggedInUser {
      return this.loggedInUserSubject.value;
    }

    login(user: UserLoginRequest) {
        return this.http.post(environment.baseUrl + environment.login, user).pipe(map((res: LoggedInUser) => {
          this.access_token = res.userTokenState.jwtAccessToken;
          localStorage.setItem('LoggedInUser', JSON.stringify(res));
          this.loggedInUserSubject.next(res);
        }));
    }

    resetPassword(email: string) {
       return this.http.get(environment.baseUrl + environment.resetPassword + "?emailAddress=" + email )   
    }
   

    registerUser(entity: Entity) {
      return this.http.post<Entity>(environment.baseUrl + environment.registerUser, entity)
    }

    registerAdmin(admin: Admin) {
      return this.http.post<Admin>(environment.baseUrl + environment.registerAdmin, admin)
    }

    confirmAccount(token: string) {
      return this.http.get(environment.baseUrl + environment.confirmAccount + "?token=" + token);
    }

    newPassword(newPass: NewPass) {
      return this.http.post<NewPass>(environment.baseUrl + environment.newPass, newPass);
    }


    logout() {
        this.access_token = null;
        localStorage.removeItem('LoggedInUser');
        this.router.navigate(['']);
    }

  

    getToken(){
      return this.access_token;
    }
  
    tokenIsPresent() {
      return this.access_token != undefined && this.access_token != null;
    }


    isLoggedIn() {
        return localStorage.getItem('LoggedInUser') !== null;
    }


    isAdmin() {
        if (this.isLoggedIn()) {
          return this.loggedInUserSubject.value.role === "ADMIN";
        }
    }

    isUser(){
        if (this.isLoggedIn()) {
          return this.loggedInUserSubject.value.role === "USER";
        }
    }

}