import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import { UserService } from './services/user.service';
import { LoggedInUser } from './models/loggedInUser';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private router: Router,
              private breakpointObserver: BreakpointObserver,
              private userService: UserService) { }

  title = 'Infrastruktura Javnih Ključeva';
  user: LoggedInUser;

 //radi otvaranje i zatvaranje side navigation u zavisnosti od velicine prozora itd...
 isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
 .pipe(
   map(result => result.matches),
   shareReplay()
 );


 ngOnInit() {
  this.router.events.subscribe((event) => {
    if (event instanceof NavigationEnd) {
      if (this.isLoggedIn()) {
        this.user = this.userService.getLoggedInUser();
      }
    }
  });
}

isLoggedIn() {
  return this.userService.isLoggedIn();
}

onLogout() {
  this.userService.logout();
  this.router.navigate(['/login']);
}


isAdmin(){
  return this.userService.isAdmin();
}

isUser(){
  return this.userService.isUser();
}


}
