import { UserService } from './../../users/shared/user.service';
import { User } from './../../users/shared/user.model';
import { GoogleAuthService } from './../../core/directives/shared/google-auth.service';
import { League } from './../shared/league.model';
import { Component, OnChanges, Input } from '@angular/core';

@Component({
  selector: 'app-league-assign',
  templateUrl: './league-assign.component.html',
  styleUrls: ['./league-assign.component.css']
})
export class LeagueAssignComponent implements OnChanges {
  @Input() league: League;
  showSuccessAlert: boolean = false;

  constructor(
    private googleAuthService: GoogleAuthService,
    private userService: UserService
  ) { }

  ngOnChanges() {
    
  }

  isAuthenticated() {
    return this.googleAuthService.isAuthenticated();
  }

  getUser() {
    return this.googleAuthService.getUser();
  }

  isAssigned() {
    if (this.league != undefined)
      return this.league.users && this.league.users.length > 0;
    return true;
  }

  assignLeague() {
    let userId = this.googleAuthService.getUser().id;
    let leagueId = this.league.id;
    this.userService.assignLeague(userId, leagueId)
      .then(user => {
        sessionStorage.setItem(this.googleAuthService.USER, JSON.stringify(user));
        this.showSuccessAlert = true;
      });
  }
}
