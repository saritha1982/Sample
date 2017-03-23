import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-ssc',
  templateUrl: './ssc.component.html',
  styleUrls: ['./ssc.component.css','../../tools.component.css']
})
export class SscComponent implements OnInit {

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }

  gotoBranching(): void {
    let link =['/km/github3/branching-and-structure'];
    this.router.navigate(link);
  }

}
