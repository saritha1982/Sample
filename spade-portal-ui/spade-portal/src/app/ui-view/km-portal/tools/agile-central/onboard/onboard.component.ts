import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Observable } from "rxjs";

@Component({
  selector: 'app-onboard',
  templateUrl: './onboard.component.html',
  styleUrls: ['./onboard.component.css','../../tools.component.css']
})
export class OnboardComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  submitted = false;
  active = true;

  agileForm = new FormGroup({
    workspaceName : new FormControl('AS Software Development', Validators.required),
    domainName : new FormControl(null, Validators.required),
    projectType : new FormControl(null, Validators.required),
    customerName : new FormControl(' ', Validators.required),
    projectName : new FormControl(null, Validators.required),
    projectOwnerName : new FormControl(null, Validators.required),
    teamCecId : new FormControl(null, Validators.required),
    customerExternalAccess : new FormControl(null, Validators.required),
    contractId : new FormControl(null, Validators.required),
  });

  projectType = this.agileForm.controls["projectType"].valueChanges;

  form = "";

  onSubmit() {
    /*console.log(this.agileForm.value);
     console.log(this.agileForm.controls['projectName'].value);*/

    this.form = "Hi AS-CICD Admin, %0D %0D" + "Please find below the details required for access to Agile Central. %0D %0D"
      + "Workspace Name : " + this.agileForm.controls['workspaceName'].value
      + "%0D Domain Name : " + this.agileForm.controls['domainName'].value
      + "%0D Project Type : " + this.agileForm.controls['projectType'].value
      + "%0D Customer Name : " + this.agileForm.controls['customerName'].value
      + "%0D Project Name : " + this.agileForm.controls['projectName'].value
      + "%0D Project Owner Name : " + this.agileForm.controls['projectOwnerName'].value
      + "%0D Team CEC IDs : " + this.agileForm.controls['teamCecId'].value
      + "%0D Customer External Access : " + this.agileForm.controls['customerExternalAccess'].value
      + "%0D Contract ID : " + this.agileForm.controls['contractId'].value
      + "%0D %0D" + "Thanks";

    window.location.href = "mailto:as-cicdtools-admin@cisco.com" + "&subject=" + "Request to Provide Access to Agile Central" + "&body=" + this.form;

    this.submitted = true;
  }
}
