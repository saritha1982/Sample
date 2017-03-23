import {Component, OnInit} from "@angular/core";
import {FormGroup, FormControl, Validators} from "@angular/forms";
import {ServicesComponent} from "../../../../services/services.component";

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css','../../onboarding.component.css']
})
export class EditProjectComponent implements OnInit {

  /*Variable Declaration*/
  submitted = false;
  editSuccessful : any;
  body :any;
  projectList : any;
  projectRequestTypeList : any;
  projectManagerList: any;
  earlierProjectReferenceList : any;
  projectTypeList : any;


//variables to display the parent values
  customerName : any;
  customerId : any;
  workType:any ;
  projectRequestType:any;
  earlierProjectReference:any;
  projectManager:any;
  projectType:any;

  constructor(
    private service : ServicesComponent
  ) {

    console.log("Inside Edit constructor");
    console.log("************ Get Variable " +this.service.getVariable().onboardId);

    this.customerName = this.service.getVariable().customerName;
    this.workType = this.service.getVariable().worktype;
    this.projectRequestType = this.service.getVariable().projectRequestType;
    this.earlierProjectReference = this.service.getVariable().earlierProjectReference;
    this.projectManager = this.service.getVariable().projectManager;
    this.projectType = this.service.getVariable().projectType;
    console.log("Work Type = " + this.workType);
    console.log("Project Request Type = " + this.projectRequestType);
    console.log(this.earlierProjectReference);
    console.log(this.service.getVariable().businessUnit);
    console.log(this.service.getVariable().notes);

    this.service.getCustomerIdDynamically(this.customerName)
      .then(response => {
        this.customerId = response;
        console.log("Customer ID for " + this.customerName + " is " + this.customerId);

        //Getting ProjectRequestType
        this.service.getProjectRequestType()
          .then(projectRequestType => {
          this.projectRequestTypeList = projectRequestType.json();
          console.log(this.projectRequestTypeList);
          })
          .catch(err => {
          console.log("this is error" + err);
          });

        //Getting ProjectType
        this.service.getProjectTypeList()
          .then(projectType =>{
          this.projectTypeList = projectType.json();
          console.log(this.projectTypeList);
          })
          .catch(err=> {
          console.log("this is error" + err);
          });

        //Getting EarlierProjectReferenceList
        this.service.getEarlierProjectReferenceList(this.customerId)
          .then(earlierProjectReference =>{
            this.earlierProjectReferenceList = earlierProjectReference.json();
            console.log(this.earlierProjectReferenceList);
          })
          .catch(err=> {
            console.log("this is error" + err);
          });

      })
      .catch(err => {
        console.log("this is error"+err);
      });

  }

  ngOnInit() {
  }

  projectOnboardingForm = new FormGroup({

    /*Onboard*/
    projectId : new FormControl(''),
    contractId : new FormControl(''),
    clarityId : new FormControl(''),
    onboardId : new FormControl({value:this.service.getOnboardId(), disabled:true}, Validators.required),

    /*Project Details*/

    customerName: new FormControl({value: this.service.getVariable().customerName, disabled: true}, Validators.required),
    workType: new FormControl({value: this.service.getVariable().worktype, disabled: true}, Validators.required),
    technology: new FormControl({value: this.service.getVariable().technologyName, disabled: true}, Validators.required),
    projectName : new FormControl({value: this.service.getVariable().projectName, disabled: true}, Validators.required),
    projectStartDate : new FormControl(this.service.getVariable().projectStartDate),
    projectEndDate : new FormControl(this.service.getVariable().projectEndDate),
    projectRequestType  : new FormControl(this.service.getVariable().projectRequestType),
    earlierProjectReference : new FormControl(this.service.getVariable().earlierProjectReference),
    businessUnit : new FormControl(this.service.getVariable().businessUnit),
    projectManager : new FormControl({value: '', disabled: true}, Validators.required),
    projectType : new FormControl(this.service.getVariable().projectType),
    parentProject : new FormControl({value: this.service.getVariable().parentProject, disabled: true}, Validators.required),
    notes : new FormControl(this.service.getVariable().notes)
  });

  /* SYNC Variables*/
  projectRequestTypeSync = this.projectOnboardingForm.controls["projectRequestType"].valueChanges;
  //businessUnit = this.projectOnboardingForm.controls["businessUnit"].valueChanges;

  onSubmit() {
    this.submitted = true;
    console.log(this.projectOnboardingForm.controls["technology"].value);

    console.log(this.projectOnboardingForm.controls["projectEndDate"].value);

    console.log(this.projectOnboardingForm.controls["projectRequestType"].value);
    console.log(this.projectOnboardingForm.controls["businessUnit"].value);
    console.log(this.projectOnboardingForm.controls["notes"].value);

    this.body = {
      "onBoardId": this.projectOnboardingForm.controls["onboardId"].value,
      "projectId": this.projectOnboardingForm.controls["projectId"].value,
      "clarityId": this.projectOnboardingForm.controls["clarityId"].value,
      "contractId": this.projectOnboardingForm.controls["contractId"].value,
      "technologyName": this.projectOnboardingForm.controls["technology"].value,
      "customerName": this.projectOnboardingForm.controls["customerName"].value,
      "earlierProjectReference": this.projectOnboardingForm.controls["earlierProjectReference"].value,
      "businessUnit": this.projectOnboardingForm.controls["businessUnit"].value,
      "parentProject": this.projectOnboardingForm.controls["parentProject"].value,
      "projectEndDate": this.projectOnboardingForm.controls["projectEndDate"].value,
      "projectName": this.projectOnboardingForm.controls["projectName"].value,
      "projectRequestType": this.projectOnboardingForm.controls["projectRequestType"].value,
      "projectStartDate": this.projectOnboardingForm.controls["projectStartDate"].value,
      "projectType": this.projectOnboardingForm.controls["projectType"].value,
      "workType": this.projectOnboardingForm.controls["workType"].value,
      //"projectManager": "as-ci-user.gen@cisco.com",
      "projectManager" : this.projectOnboardingForm.controls["projectManager"].value,
      "notes": this.projectOnboardingForm.controls["notes"].value

    };

    console.log(this.body);

    this.service.editProject(this.body, this.customerId).then(response => {
      console.log("Project Edition Successful!!!");
      this.editSuccessful = true;
      this.submitted = true;
    })
      .catch(err => {
        console.log("this is error"+err);
      });

  }

  goBack() : void {
    window.history.back();
  }

}
