/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { SscComponent } from './ssc.component';

describe('SscComponent', () => {
  let component: SscComponent;
  let fixture: ComponentFixture<SscComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SscComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SscComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
