/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { AgileCentralComponent } from './agile-central.component';

describe('AgileCentralComponent', () => {
  let component: AgileCentralComponent;
  let fixture: ComponentFixture<AgileCentralComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgileCentralComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgileCentralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
