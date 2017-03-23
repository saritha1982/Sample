import { Component, OnInit } from '@angular/core';
import {ServicesComponent} from "../../../../services/services.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css','../../onboarding.component.css']
})
export class ManageUsersComponent implements OnInit {

  userDetails: any ;
  customerName : any;
  customerId : any;
  disable:any = false;

  constructor(
    private service : ServicesComponent,
    private router: Router
  ) {

    this.customerId = this.service.getDummyCustomerId();

    /*this.service.getCustomerIdDynamically(this.customerName)
      .then(response => {
        this.customerId = response;
        console.log("Customer ID for " + this.customerName + " is " + this.customerId);
      })
      .catch(err => {
        console.log("this is error"+err);
      });
    */

    this.service.getManageUsersList(this.customerId).then(response => {

    this.userDetails = response.json();
    console.log(this.userDetails);
    })
    .catch(err => {
        console.log("this is error"+err);
      })

   }

  ngOnInit() {
  }

  editUser(username :any,emailAddress :any,accessLevel: any,projectList : any) :any {
    //console.log(projectList);
    console.log(projectList);

    this.service.setEditProjectlist(username,emailAddress,accessLevel,projectList);

    console.log('Ready for navigation');
    this.router.navigate(['edit-user']);


  }

  deleteUser(username :any,emailAddress :any,accessLevel: any,projectList : any):any {

    let body = {
      "userName" : username,
      "emailAddress" : emailAddress,
      "accessLevel" : accessLevel,
      //"disabled" : disabled,
      "projects" : projectList
      };

    console.log(body);
    this.service.disableUser(body, this.customerId).then(res => {
      console.log("Got the response from service component");
      this.disable = true;
    })

  }

  goBack() : void {
    window.location.reload();
  }

}
