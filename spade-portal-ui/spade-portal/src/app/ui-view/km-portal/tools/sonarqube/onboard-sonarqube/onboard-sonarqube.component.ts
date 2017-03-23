import { Component, OnInit } from '@angular/core';
import {Validators, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-onboard-sonarqube',
  templateUrl: './onboard-sonarqube.component.html',
  styleUrls: ['./onboard-sonarqube.component.css','../../tools.component.css']
})
export class OnboardSonarqubeComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  locations = ['SJC','GPK','BLR'];
  submitted = false;
  active = true;

  sonarqubeForm = new FormGroup({
    domainName : new FormControl(null, Validators.required),
    locationAccess : new FormControl(null, Validators.required),
    projectName : new FormControl(null, Validators.required),
    teamCecId : new FormControl(null, Validators.required)
  });

  form = "";

  onSubmit() {

    this.form = "Hi AS-CICD Admin, %0D %0D" + "Please find below the details required for access to SonarQube. %0D %0D"
      + "Domain Name : " + this.sonarqubeForm.controls['domainName'].value
      + "%0D Location for Access : " + this.sonarqubeForm.controls['locationAccess'].value
      + "%0D Project Name : " + this.sonarqubeForm.controls['projectName'].value
      + "%0D Team CEC IDs : " + this.sonarqubeForm.controls['teamCecId'].value
      + "%0D %0D" + "Thanks";

    window.location.href = "mailto:as-cicdtools-admin@cisco.com" + "&subject=" + "Request to Provide Access to SonarQube" + "&body=" + this.form;

    this.submitted = true;
  }
}
