/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { CdetsComponent } from './cdets.component';

describe('CdetsComponent', () => {
  let component: CdetsComponent;
  let fixture: ComponentFixture<CdetsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CdetsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CdetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
