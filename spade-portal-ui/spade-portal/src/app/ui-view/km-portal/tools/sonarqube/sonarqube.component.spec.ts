/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { SonarqubeComponent } from './sonarqube.component';

describe('SonarqubeComponent', () => {
  let component: SonarqubeComponent;
  let fixture: ComponentFixture<SonarqubeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SonarqubeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SonarqubeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
