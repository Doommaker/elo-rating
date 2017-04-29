import { League } from './../../leagues/shared/league.model';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { By } from '@angular/platform-browser';
import { MatchServiceStub } from './../../testing/match-stubs';
import { MatchService } from './../../matches/shared/match.service';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRouteStub } from './../../testing/routing-stubs';
import { ActivatedRoute } from '@angular/router';
import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { PlayerMatchesComponent } from './player-matches.component';

describe('PlayerMatchesComponent', () => {
  let component: PlayerMatchesComponent;
  let fixture: ComponentFixture<PlayerMatchesComponent>;
  let activatedRoute: ActivatedRouteStub;

  beforeEach(async(() => {
    activatedRoute = new ActivatedRouteStub();
    TestBed.configureTestingModule({
      declarations: [ PlayerMatchesComponent ],
      imports: [ RouterTestingModule, NgbModule.forRoot() ],
      providers: [
        {provide: MatchService, useClass: MatchServiceStub},
        {provide: ActivatedRoute, useValue: activatedRoute}
      ]
    })
    .compileComponents();
  }));

  function createComponent() {
    fixture = TestBed.createComponent(PlayerMatchesComponent);
    component = fixture.componentInstance;
    activatedRoute.testParams = {player_id: '111', league_id: '123'}
    fixture.detectChanges();
    tick();
  };

  it('should create', fakeAsync(() => {
    createComponent();
    expect(component).toBeTruthy();
  }));

  it('should have player id', fakeAsync(() => {
    createComponent();
    expect(component.playerId).toEqual('111');
  }));

  it('should have league id', fakeAsync(() => {
    createComponent();
    expect(component.leagueId).toEqual('123');
  }));

  it('should have played matches list', fakeAsync(() => {
    createComponent();    
    expect(component.playedMatches.length).toBeGreaterThan(0);
    expect(component.hasPlayedMatches()).toBeTruthy();
  }));

  it('should have scheduled matches list', fakeAsync(() => {
    createComponent();
    expect(component.scheduledMatches.length).toBeGreaterThan(0);
    expect(component.hasScheduledMatches()).toBeTruthy();
  }));

  it('should display played matches in table', fakeAsync(() => {
    createComponent();
    fixture.detectChanges();
    let debugElement = fixture.debugElement.queryAll(By.css('table#playedMatches tbody tr'));
    expect(debugElement.length).toBeGreaterThan(0);
  }));

  it('should display scheduled matches in table', fakeAsync(() => {
    createComponent();
    fixture.detectChanges();
    let debugElement = fixture.debugElement.queryAll(By.css('table#scheduledMatches tbody tr'));
    expect(debugElement.length).toBeGreaterThan(0);
  }));

  it('should display alert if matches list is empty', fakeAsync(() => {
    createComponent();
    component.playedMatches = [];
    fixture.detectChanges();
    expect(component.hasMatches()).toBeFalsy();
    let debugElement = fixture.debugElement.query(By.css('div.alert.alert-info'));
    expect(debugElement.nativeElement).toBeTruthy();
  }));

  it('should present winner in green and looser in red background', fakeAsync(() => {
    createComponent();
    fixture.detectChanges();
    let debugElement = fixture.debugElement.queryAll(By.css('table tbody tr'));
    expect(debugElement[0].query(By.css('td.table-success')).nativeElement.textContent).toEqual('Player 1');
    expect(debugElement[1].query(By.css('td.table-danger')).nativeElement.textContent).toEqual('Player 1');    
  }));

  it('should present current player in blue background', fakeAsync(() => {
    createComponent();
    fixture.detectChanges();
    let debugElement = fixture.debugElement.queryAll(By.css('table#scheduledMatches tbody tr'));
    expect(debugElement[0].query(By.css('td.table-info')).nativeElement.textContent).toEqual('Player 1');
    expect(debugElement[1].query(By.css('td.table-info')).nativeElement.textContent).toEqual('Player 1');    
  }));
});