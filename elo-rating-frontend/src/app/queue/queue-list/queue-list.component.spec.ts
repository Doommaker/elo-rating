import { GoogleAuthServiceStub } from './../../testing/google-stubs';
import { GoogleAuthService } from './../../auth/shared/google-auth.service';
import { RouterTestingModule } from '@angular/router/testing';
import { Match } from './../../matches/shared/match.model';
import { MATCHES, SCHEDULED_MATCHES } from './../../testing/data/matches';
import { Player } from './../../players/shared/player.model';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { ActivatedRouteStub, RouterStub, RouterLinkStub } from './../../testing/routing-stubs';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayerServiceStub } from './../../testing/player-stubs';
import { PlayerService } from './../../players/shared/player.service';
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
        { provide: GoogleAuthService, useClass: GoogleAuthServiceStub }
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    activatedRoute = new ActivatedRouteStub();
    fixture = TestBed.createComponent(QueueListComponent);
    component = fixture.componentInstance;
    component.scheduledMatches = SCHEDULED_MATCHES;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
