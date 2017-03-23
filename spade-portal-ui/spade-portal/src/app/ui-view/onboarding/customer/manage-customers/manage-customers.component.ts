import { Component, OnInit } from '@angular/core';
import {ServicesComponent} from "../../../../services/services.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-manage-customers',
  templateUrl: './manage-customers.component.html',
  styleUrls: ['./manage-customers.component.css','../../onboarding.component.css']
})
export class ManageCustomersComponent implements OnInit {

  customerNameList : any;

  constructor(
    private service : ServicesComponent,
    private router: Router

  ) {

    this.service.getAllCustomers()
      .then(customerName => {
        console.log('Customer Name = ' + customerName);
        this.customerNameList = customerName.json();
        console.log(this.customerNameList);
      })
      .catch(err => {
        console.log("this is error"+err);
      });
  }

  ngOnInit() {
  }

  editCustomer(customerName : any, emailAddress : any, region :any, contact : any) : void {
    console.log("Inside Edit Customer ... Customer Name = " + customerName);

    this.service.setEditCustomerDetails(customerName, emailAddress, region, contact);

    this.router.navigate(['edit-customer']);

  }

}
