/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { BranchingComponent } from './branching.component';

describe('BranchingComponent', () => {
  let component: BranchingComponent;
  let fixture: ComponentFixture<BranchingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BranchingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BranchingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
