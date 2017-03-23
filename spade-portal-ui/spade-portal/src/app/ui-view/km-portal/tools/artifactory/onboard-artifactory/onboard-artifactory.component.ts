import { Component, OnInit } from '@angular/core';
import {Validators, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-onboard-artifactory',
  templateUrl: './onboard-artifactory.component.html',
  styleUrls: ['./onboard-artifactory.component.css','../../tools.component.css']
})
export class OnboardArtifactoryComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  locations = ['SJC','GPK','BLR'];
  repositories = ['AS-release','AS-snapshot','AS-thirdparty','AS-group (virtual group)'];
  submitted = false;
  active = true;

  artifactoryForm = new FormGroup({
    domainName : new FormControl(null, Validators.required),
    locationAccess : new FormControl(null, Validators.required),
    repositoryAccess : new FormControl(null, Validators.required),
    projectName : new FormControl(null, Validators.required),
    teamCecId : new FormControl(null, Validators.required)
  });

  form = "";

  onSubmit() {
    /*console.log(this.artifactoryForm.value);
     console.log(this.artifactoryForm.controls['projectName'].value);*/

    this.form = "Hi AS-CICD Admin, %0D %0D" + "Please find below the details required for access to Artifactory. %0D %0D"
      + "Domain Name : " + this.artifactoryForm.controls['domainName'].value
      + "%0D Location for Access : " + this.artifactoryForm.controls['locationAccess'].value
      + "%0D Access with Artifactory Repository : " + this.artifactoryForm.controls['repositoryAccess'].value
      + "%0D Project Name : " + this.artifactoryForm.controls['projectName'].value
      + "%0D Team CEC IDs : " + this.artifactoryForm.controls['teamCecId'].value
      + "%0D %0D" + "Thanks";

    window.location.href = "mailto:as-cicdtools-admin@cisco.com" + "&subject=" + "Request to Provide Access to Artifactory" + "&body=" + this.form;

    this.submitted = true;
  }
}
