import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-solution-architect',
  templateUrl: './solution-architect.component.html',
  styleUrls: ['./solution-architect.component.css','../../km-home.component.css']
})
export class SolutionArchitectComponent implements OnInit {

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
