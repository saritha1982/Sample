/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { EclipseComponent } from './eclipse.component';

describe('EclipseComponent', () => {
  let component: EclipseComponent;
  let fixture: ComponentFixture<EclipseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EclipseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EclipseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
