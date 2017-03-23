/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { SdlpPhasedApproachComponent } from './sdlp-phased-approach.component';

describe('SdlpPhasedApproachComponent', () => {
  let component: SdlpPhasedApproachComponent;
  let fixture: ComponentFixture<SdlpPhasedApproachComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SdlpPhasedApproachComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdlpPhasedApproachComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
