import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-project-manager',
  templateUrl: './project-manager.component.html',
  styleUrls: ['./project-manager.component.css','../../km-home.component.css']
})
export class ProjectManagerComponent implements OnInit {

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }

  gotoAgile(): void {
    let link =['/km/agile-central'];
    this.router.navigate(link);
  }

}
