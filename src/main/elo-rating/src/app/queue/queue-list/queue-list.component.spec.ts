import { RouterTestingModule } from '@angular/router/testing';
import { Match } from './../../matches/shared/match.model';
import { QueueService } from './../shared/queue.service';
import { MATCHES } from './../../testing/match-stubs';
import { Player } from './../../players/shared/player.model';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { ActivatedRouteStub, RouterStub, RouterLinkStub } from './../../testing/routing-stubs';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayerServiceStub, PLAYERS } from './../../testing/player-stubs';
import { PlayerService } from './../../players/shared/player.service';
import { QueueServiceStub, QUEUE, EMPTY_QUEUE } from './../../testing/queue-stubs';
import { HttpModule } from '@angular/http';
import { AppModule } from './../../app.module';
/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed, fakeAsync, tick, inject } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { QueueListComponent } from './queue-list.component';

describe('QueueComponent', () => {
  let component: QueueListComponent;
  let fixture: ComponentFixture<QueueListComponent>;

  let playerService: PlayerService;
  let queueService: QueueService;
  let router: RouterStub;
  let activatedRoute: ActivatedRouteStub;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        QueueListComponent,
        RouterLinkStub        
      ],
      imports: [
        HttpModule,
        FormsModule,
        NgbModule.forRoot(),
      ],
      providers: [
        { provide: Router, useClass: RouterStub },
        { provide: ActivatedRoute, useValue: activatedRoute },
        { provide: PlayerService, useClass: PlayerServiceStub },
        { provide: QueueService, useClass: QueueServiceStub }
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    activatedRoute = new ActivatedRouteStub();
    fixture = TestBed.createComponent(QueueListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have queue', () => {
    component.queue = QUEUE;
    fixture.detectChanges();
    expect(component).toBeTruthy();
    let debugElement = fixture.debugElement.query(By.css('div#daily-queue h2'));
    expect(debugElement.nativeElement.textContent).toEqual('Daily queue');
  });

  // Ignore test because functionality is moved to match add component
  // it('should add match to queue', fakeAsync(() => {
  //   component.scheduledMatches = MATCHES;
  //   expect(component.scheduledMatches.length).toEqual(MATCHES.length);

  //   let formButton = fixture.debugElement.query(By.css('div form div button#queue-match-submit '));
  //   expect(formButton.nativeElement.textContent).toEqual('Add match to queue');

  //   let playerOne = PLAYERS[0];
  //   let playerTwo = PLAYERS[1];
  //   let match = MATCHES[0];
  //   match.playerOne = playerOne;
  //   match.playerTwo = playerTwo;

  //   component.match = match;

  //   console.log('test temporary ignored because of XHR issue');
  //   // formButton.triggerEventHandler('click', null);

  //   // expect(component.queue.matches.length).toEqual(2);
  //   // expect(component.queue.matches[1].playerOne.username).toEqual(playerOne.username);
  //   // expect(component.queue.matches[1].playerTwo.username).toEqual(playerTwo.username);
  // }));

  /*
  it('should remove match from queue', fakeAsync(() => {
    let playerOne = PLAYERS[0];
    let playerTwo = PLAYERS[1];
    let match = MATCHES[0];
    let date = new Date();
    date.setHours(16);
    date.setMinutes(30);
    match.date = date;
    match.playerOne = playerOne;
    match.playerTwo = playerTwo;

    component.scheduledMatches = MATCHES;
    component.scheduledMatches[0] = match;

    let queueSize = component.scheduledMatches.length;

    expect(queueSize).toEqual(MATCHES.length);
    expect(component.scheduledMatches[0].id).toEqual('111');

    fixture.detectChanges();
    let matchElement = fixture.debugElement.query(By.css('div#daily-queue table tbody tr'));
    let matchColumns = fixture.debugElement.queryAll(By.css('div#daily-queue table tbody tr td'));
    expect(matchColumns[0].nativeElement.textContent).toEqual(playerOne.username);
    expect(matchColumns[1].nativeElement.textContent).toEqual(date.getHours() + ":" + date.getMinutes());
    expect(matchColumns[2].nativeElement.textContent).toEqual(playerTwo.username);

    matchElement.triggerEventHandler('click', null);

    component.scheduledMatches = new Array<Match>();

    fixture.detectChanges();
    tick();

    matchColumns = fixture.debugElement.queryAll(By.css('div#daily-queue table tbody tr td'));
    expect(matchColumns.length).toEqual(0);

    let newQueueSize = component.scheduledMatches.length;
    expect(newQueueSize).toEqual(queueSize - 1);
  }));
  */
});
