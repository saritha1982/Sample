import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl} from "@angular/forms";

@Component({
  selector: 'app-custom-dashboards',
  templateUrl: './custom-dashboards.component.html',
  styleUrls: ['./custom-dashboards.component.css']
})
export class CustomDashboardsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  choiceForm = new FormGroup ({
    customerName : new FormControl(),
    technology : new FormControl(),
    projectState : new FormControl()
  });

  customerNameList = ['Singtel', 'AT & T', 'Verizon', 'Ernst & Young'];
  technologyList = ['NFV', 'NSO', 'ACI', 'UCSD', 'Cloud Automation'];
  projectStateList = ['In-Progress', 'Completed'];
  userNameList = ['Mohitmal', 'Sujani', 'Ramana', 'Jane Juo'];
}
