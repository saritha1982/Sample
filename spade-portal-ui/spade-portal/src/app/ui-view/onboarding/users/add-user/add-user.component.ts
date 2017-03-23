import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, Validators, Validator} from "@angular/forms";
import { ServicesComponent } from '../../../../services/services.component';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css','../../onboarding.component.css']
})
export class AddUserComponent implements OnInit {

  projectList : any;
  projectRoleList:any;
  body : any;
  userCreated : any = false;
  verifyUser:any ;
  notverified:any;
  user:any;
  successfulSubmission = false;
  accessLevels : any = ['User','Admin','Viewer'];

  customerName : any;
  customerId : any;
  constructor( private service : ServicesComponent) {

    this.customerId = this.service.getDummyCustomerId();

    /*this.service.getCustomerIdDynamically(this.customerName)
      .then(response => {
        this.customerId = response;
        console.log("Customer ID for " + this.customerName + " is " + this.customerId);
      })
      .catch(err => {
        console.log("this is error"+err);
      });*/

    this.service.getProjectRolesList()
      .then(projectRole =>{
        this.projectRoleList = projectRole.json();
        console.log(this.projectRoleList);
      })
      .catch(err=> {
        console.log("this is error" + err);
      });

    this.service.getProjectsList(this.customerId).then(projectsList => {


      this.projectList = projectsList.json();
      console.log(this.projectList);


    })
      .catch(err => {
        console.log("this is error"+err);
      })
  }

  ngOnInit() {
  }

  submitted = false;

  userOnboardingForm = new FormGroup({
    userName : new FormControl('', Validators.required),
    emailAddress : new FormControl(),
    workspace : new FormControl('AS Software Development', Validators.required),
    accessLevel : new FormControl('', Validators.required),
    onBoardId : new FormControl('')
  });

  userName = this.userOnboardingForm.controls["userName"].valueChanges;

  projectsList = ['Project 1', 'Project 2', 'Project 3'];
  //projectRoleList = ['Role 1', 'Role 2', 'Role 3'];

  projectDetailsForm = new FormGroup({
    onBoardId : new FormControl('', Validators.minLength(3)),
    projectRole : new FormControl('', Validators.minLength(3))
  });

  projectName = this.projectDetailsForm.controls["onBoardId"].valueChanges;
  projectRole = this.projectDetailsForm.controls["projectRole"].valueChanges;

  test = [];

  checkUser() : any{
    console.log(this.userOnboardingForm.controls["userName"].value);
    console.log(this.projectDetailsForm.controls["onBoardId"].value);


  }

  onSubmit() {
    console.log(this.userOnboardingForm.controls["userName"].value.split("@")[0]);
    this.user = this.userOnboardingForm.controls["userName"].value.split("@")[0];
    //console.log(form._value.userName.split("@")[0]);
    this.test = this.projectDetailsList;
    console.log(this.test);
    console.log(this.test[0].onBoardId);
    let i:any;
    let name:any;
    let names:any;

    this.service.verifyGithubUser(this.user).then(response => {
      this.verifyUser = response.json();
      this.notverified = !this.verifyUser;
   })
   .catch(err=> {
        console.log("this is error" + err);
   });

    for(i=0;i<this.test.length;){
      name=this.test[i].onBoardId;
      names=name.split("/");
      this.test[i].onBoardId=names[1];
      i=i+1;
    }

    this.submitted = true;
    this.userOnboardingForm.controls["emailAddress"].setValue(this.userOnboardingForm.controls["userName"].value);
    this.body ={"userName":this.userOnboardingForm.controls["userName"].value.split("@")[0],
    		        "emailAddress":this.userOnboardingForm.controls["emailAddress"].value,
                "accessLevel":this.userOnboardingForm.controls["accessLevel"].value,
                "workspace":this.userOnboardingForm.controls["workspace"].value,
                "projects":this.projectDetailsList
              };
    //console.log(this.body);

    this.service.createUser(this.body, this.customerId)
    .then(value => {
      this.submitted = false;
      this.successfulSubmission = true;
    })
  }

  projectAdded = false;
  projectDetailsList = [];

  submitProjectDetails () {
    let name:any;
    let names:any[] = [];
    if(this.projectDetailsForm.controls["onBoardId"].value != '' && this.projectDetailsForm.controls["projectRole"].value != '') {
      this.projectDetailsList.push(this.projectDetailsForm.value);
      //this.passedProjectDetailsForm = this.projectDetailsForm.value;
      this.projectAdded = true;
    }
    else
      console.log("Error... Try Again");
    this.projectDetailsForm.controls["onBoardId"].setValue('');
    this.projectDetailsForm.controls["projectRole"].setValue('');
  }

  deleteProjectDetail (onBoardId : any, projectRole : any) {
    let index = 0;
    for (let pro of this.projectDetailsList) {
      if(pro.onBoardId === onBoardId && pro.projectRole === projectRole) {
        this.projectDetailsList.splice(index, 1);
        break;
      }
      index++;
    }
  }

  goBack() : void {
    window.location.reload();
  }

}
