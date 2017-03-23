import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-features',
  templateUrl: './features.component.html',
  styleUrls: ['./features.component.css']
})
export class FeaturesComponent implements OnInit {

  constructor(
    private routerOnboarding: Router,
    private routerDashboards: Router,
    private routerKmPortal: Router
  ) { }

  ngOnInit() {
  }

  gotoOnboarding(): void {
    let link = ['/onboarding/manage-projects'];
    this.routerOnboarding.navigate(link);
  }
  gotoDashboards(): void {
    let link = ['/dashboards/custom-dashboards'];
    this.routerDashboards.navigate(link);
  }
  gotoKmPortal(): void {
    let link = ['/km'];
    this.routerKmPortal.navigate(link);
  }

}
