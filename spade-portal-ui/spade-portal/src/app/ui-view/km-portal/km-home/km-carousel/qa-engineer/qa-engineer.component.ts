import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-qa-engineer',
  templateUrl: './qa-engineer.component.html',
  styleUrls: ['./qa-engineer.component.css','../../km-home.component.css']
})
export class QaEngineerComponent implements OnInit {

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }

  gotoJenkins(): void {
    let link =['/km/jenkins'];
    this.router.navigate(link);
  }
  gotoSonar(): void {
    let link =['/km/sonarqube'];
    this.router.navigate(link);
  }
  gotoArtifactory(): void {
    let link =['/km/artifactory'];
    this.router.navigate(link);
  }
  gotoCdets(): void {
    let link =['/km/cdets'];
    this.router.navigate(link);
  }

}
