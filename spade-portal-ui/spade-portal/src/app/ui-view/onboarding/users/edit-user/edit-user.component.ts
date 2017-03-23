import { Component, OnInit } from '@angular/core';
import { ServicesComponent } from '../../../../services/services.component';
import {FormGroup, FormControl, Validators, Validator} from "@angular/forms";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css','../../onboarding.component.css']
})
export class EditUserComponent implements OnInit {

  submitted : any = false;
  projectList:any;
  selectedProject : any;
  body :any;
  levels : any = ['User','Admin','Viewer'];
  level : any;
  disable:any;

  constructor(private service : ServicesComponent) {

    this.projectList = this.service.getEditProjectlist();
    this.userOnboardingForm.controls["userName"].setValue(this.service.getUserName());
    this.userOnboardingForm.controls["emailAddress"].setValue(this.service.getMail());
    //this.userOnboardingForm.controls["disabled"].setValue(this.service.getDisable());
    this.userOnboardingForm.controls["accessLevel"].setValue(this.service.getAccesLevel());

    this.level = this.userOnboardingForm.controls["accessLevel"].value;

    console.log(this.level);
  }

  ngOnInit() {
  }

  userOnboardingForm = new FormGroup({
    Projects : new FormControl(null, Validators.required),
    userName : new FormControl({value: '', disabled: true}, Validators.required),
    emailAddress : new FormControl({value: '', disabled: true}, Validators.required),
    //disabled : new FormControl({value : ''}, Validators.required),
    workspace : new FormControl({value :'AS Software Development' , disabled: true}, Validators.required),
    accessLevel : new FormControl(Validators.required),
  });

  edit() :any{



  }

  goBack() : void {
    window.history.back();
  }

  onSubmit() :any{
    this.submitted = true;
    this.body ={"userName":this.userOnboardingForm.controls["userName"].value.split("@")[0],
    		        "emailAddress":this.userOnboardingForm.controls["emailAddress"].value,
                //"disabled":this.userOnboardingForm.controls["disabled"].value,
                "accessLevel":this.userOnboardingForm.controls["accessLevel"].value,
                "workspace":this.userOnboardingForm.controls["workspace"].value,
                "projects":[]
              };
    console.log("inside submit function  of edit user");
    console.log("hi"+this.userOnboardingForm.controls["Projects"].value);
    this.service.getOnboardIdForUser(this.userOnboardingForm.controls["Projects"].value).then(res => {
      let onBoardId = res.json().onboardId;

      this.service.editUser(this.body , onBoardId , this.userOnboardingForm.controls["userName"].value)
        .then(res => {
        console.log("usert finally edited");
        }).catch(err => {
          console.log("this is error" + err);
        })



    })

  }


}
