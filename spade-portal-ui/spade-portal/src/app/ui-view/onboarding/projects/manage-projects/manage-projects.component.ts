import { Component, OnInit } from '@angular/core';
import {ServicesComponent} from "../../../../services/services.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-manage-projects',
  templateUrl: './manage-projects.component.html',
  styleUrls: ['./manage-projects.component.css','../../onboarding.component.css']
})
export class ManageProjectsComponent implements OnInit {

  /*Variable Declaration*/
  projectList : any;
  projectDetail : any;
  onboardId : any;
  customerId : any;
  deleted : any = true;

  constructor(
    private service : ServicesComponent,
    private router: Router
  ) {

    this.deleted = true;
    this.customerId = this.service.getDummyCustomerId();
    console.log("Inside Manage Project : Customer Name = " + this.customerId);

    this.service.getProjectsList(this.customerId).then(projectsList => {
      this.projectList = projectsList.json();
      console.log(this.projectList);
      this.deleted = true;
    })
      .catch(err => {
        console.log("this is error"+err);
      })
  }

  ngOnInit() {
  }

  getProject() : any {
    return this.projectDetail;
  }


  editProject(projectName : any, onboardId : any) : void {
    console.log("ProjectName = " + projectName);
    console.log("OnboardId = " + onboardId);
    this.onboardId = onboardId;

    console.log("Inside Edit Project " + this.onboardId);
    //Calling Service
    this.service.getCustomerId(this.onboardId).then(custId => {
      this.customerId = custId.json().customerId;
      console.log(this.customerId);

      this.service.getProjectDetails(this.onboardId, this.customerId).then(project => {
        this.projectDetail = project.json();
        console.log("Project Detail = ");
        console.log(this.projectDetail);
        this.router.navigate(['edit-project']);
        console.log("After Navigation");
      })

    })
      .catch(err => {
        console.log("this is error"+err);
      });


  }



 deleteProject(onBoardId:any):any{

  this.service.deleteProject(onBoardId, this.customerId).then(res => {
    console.log("Rally project disabled");
    this.deleted = false;
    console.log(this.deleted);
  })
  .catch(err => {
        console.log("this is error"+err);
      });

 }

  getOnboardId(onboardId : any) {
    return this.onboardId = onboardId;
  }

  gotoManageProjects() : void {
    window.location.reload();
  }

}

