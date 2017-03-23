import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-software-developer',
  templateUrl: './software-developer.component.html',
  styleUrls: ['./software-developer.component.css','../../km-home.component.css']
})
export class SoftwareDeveloperComponent implements OnInit {

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }

  gotoAgile(): void {
    let link =['/km/agile-central'];
    this.router.navigate(link);
  }
  gotoEclipse(): void {
    let link =['/km/eclipse'];
    this.router.navigate(link);
  }
  gotoGithub(): void {
    let link =['/km/github3'];
    this.router.navigate(link);
  }
  gotoJenkins(): void {
    let link =['/km/jenkins'];
    this.router.navigate(link);
  }
  gotoSonar(): void {
    let link =['/km/sonarqube'];
    this.router.navigate(link);
  }
}
