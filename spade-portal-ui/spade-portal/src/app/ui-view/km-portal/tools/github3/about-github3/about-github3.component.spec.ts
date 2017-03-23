/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { AboutGithub3Component } from './about-github3.component';

describe('AboutGithub3Component', () => {
  let component: AboutGithub3Component;
  let fixture: ComponentFixture<AboutGithub3Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AboutGithub3Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AboutGithub3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
