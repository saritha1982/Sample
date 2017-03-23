/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { KmPortalComponent } from './km-portal.component';

describe('KmPortalComponent', () => {
  let component: KmPortalComponent;
  let fixture: ComponentFixture<KmPortalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KmPortalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KmPortalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
