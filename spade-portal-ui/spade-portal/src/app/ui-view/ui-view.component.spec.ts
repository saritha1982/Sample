/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { UiViewComponent } from './ui-view.component';

describe('UiViewComponent', () => {
  let component: UiViewComponent;
  let fixture: ComponentFixture<UiViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UiViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UiViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
