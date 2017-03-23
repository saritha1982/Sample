import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-about-github3',
  templateUrl: './about-github3.component.html',
  styleUrls: ['./about-github3.component.css','../../tools.component.css']
})
export class AboutGithub3Component implements OnInit {

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }

  gotoBranching(): void {
    let link =['/github3/branching-and-structure'];
    this.router.navigate(link);
  }

}
