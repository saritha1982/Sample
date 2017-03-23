/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { OnboardGithub3Component } from './onboard-github3.component';

describe('OnboardGithub3Component', () => {
  let component: OnboardGithub3Component;
  let fixture: ComponentFixture<OnboardGithub3Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OnboardGithub3Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OnboardGithub3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
