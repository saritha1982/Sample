import { Component, OnInit } from '@angular/core';
import {Validators, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-onboard-jenkins',
  templateUrl: './onboard-jenkins.component.html',
  styleUrls: ['./onboard-jenkins.component.css','../../tools.component.css']
})
export class OnboardJenkinsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  areas = ['Sandbox','Production'];
  locations = ['SJC','GPK','BLR'];
  types = ['Editor','Builder'];
  submitted = false;
  active = true;

  jenkinsForm = new FormGroup({
    domainName : new FormControl(null, Validators.required),
    accessArea : new FormControl(null, Validators.required),
    locationAccess : new FormControl(null, Validators.required),
    accessType : new FormControl(null, Validators.required),
    projectName : new FormControl(null, Validators.required),
    teamCecId : new FormControl(null, Validators.required)
  });

  form = "";

  onSubmit() {
    /*console.log(this.jenkinsForm.value);
     console.log(this.jenkinsForm.controls['projectName'].value);*/

    this.form = "Hi AS-CICD Admin, %0D %0D" + "Please find below the details required for access to Jenkins. %0D %0D"
      + "Domain Name : " + this.jenkinsForm.controls['domainName'].value
      + "%0D Access Area: " + this.jenkinsForm.controls['accessArea'].value
      + "%0D Location for Access : " + this.jenkinsForm.controls['locationAccess'].value
      + "%0D Type of Access : " + this.jenkinsForm.controls['accessType'].value
      + "%0D Project Name : " + this.jenkinsForm.controls['projectName'].value
      + "%0D Team CEC IDs : " + this.jenkinsForm.controls['teamCecId'].value
      + "%0D %0D" + "Thanks";

    window.location.href = "mailto:as-cicdtools-admin@cisco.com" + "&subject=" + "Request to Provide Access to Jenkins" + "&body=" + this.form;

    this.submitted = true;
  }
}
