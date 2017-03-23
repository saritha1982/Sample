import { Component, OnInit } from '@angular/core';
import {Validators, FormControl, FormGroup} from "@angular/forms";
import { Observable } from "rxjs";

@Component({
  selector: 'app-onboard-github3',
  templateUrl: './onboard-github3.component.html',
  styleUrls: ['./onboard-github3.component.css','../../tools.component.css']
})
export class OnboardGithub3Component implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  organisations = ['AS-community','Customer'];
  submitted = false;
  active = true;

  githubForm = new FormGroup({
    githubAccount : new FormControl(null, Validators.required),
    organisationName : new FormControl(null, Validators.required),
    customerName : new FormControl(),
    repositoryName : new FormControl(null, Validators.required),
    repositoryOwnerName : new FormControl(null, Validators.required),
    teamCecId : new FormControl(null, Validators.required)
  });

  githubAccount = this.githubForm.controls["githubAccount"].valueChanges;
  organisationName = this.githubForm.controls["organisationName"].valueChanges;

  form = "";

  onSubmit() {
    /*console.log(this.githubForm.value);
     console.log(this.githubForm.controls['projectName'].value);*/

    this.form = "Hi AS-CICD Admin, %0D %0D" + "Please find below the details required for access to GitHub3. %0D %0D"
      + "Need Github Enterprise account : " + this.githubForm.controls['githubAccount'].value
      + "%0D Name of Organization : " + this.githubForm.controls['organisationName'].value
      + "%0D Customer Name : " + this.githubForm.controls['customerName'].value
      + "%0D Repository Name : " + this.githubForm.controls['repositoryName'].value
      + "%0D Repository Owner Name : " + this.githubForm.controls['repositoryOwnerName'].value
      + "%0D Team CEC IDs : " + this.githubForm.controls['teamCecId'].value
      + "%0D %0D" + "Thanks";

    window.location.href = "mailto:as-cicdtools-admin@cisco.com" + "&subject=" + "Request to Provide Access to GitHub3" + "&body=" + this.form;

    this.submitted = true;
  }
}
