import { Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { League } from './../shared/league.model';
import { LeagueService } from './../shared/league.service';
import { Component, OnInit, Input } from '@angular/core';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-league-search',
  templateUrl: './league-search.component.html',
  styleUrls: ['./league-search.component.css']
})
export class LeagueSearchComponent implements OnInit {

  url: string[];
  
  leagueToSearch: League;
  leagueName: string;

  leagues: League[];

  constructor(private leagueService: LeagueService, private router: Router) { }

  ngOnInit() {
    this.leagueToSearch = new League();
    this.leagueService.getAllLeagues().then(response => this.leagues = response);
  }

  goToLeague() {
    let league = this.leagues.find(x => x.name == this.leagueToSearch.name);
    this.setLeagueUrl(league);
    this.router.navigate(this.url);
  }

  searchLeague = (text$: Observable<string>) => 
    text$
      .debounceTime(200)
      .map(term => term === '' ? []
        : this.leagues.filter(league => league.name.includes(term)));

  leagueFormatter(league: League) : string {
    return league.name ? league.name : '';
  }

  setLeagueUrl(league: League) : void {
    if (league != null) {
      this.url = ["/leagues", league.id];
    } else {
      this.url = ["/leagues"];
    }
  }

  leagueFound() {
    return this.leagueToSearch.name != null ? true : false;
  }
}