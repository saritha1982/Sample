import { Component, OnInit } from '@angular/core';

declare let google : any;

@Component({
  selector: 'app-google-charts',
  templateUrl: './google-charts.component.html',
  styleUrls: ['./google-charts.component.css']
})
export class GoogleChartsComponent implements OnInit {

  constructor(){
    this.options = {
      title : { text : 'simple chart' },
      series: [{
        data: [29.9, 71.5, 106.4, 129],
      }]
    };
    console.log("Here is GoogleChartComponent");
  }

  options: Object;

  ngOnInit() {
  }

}
