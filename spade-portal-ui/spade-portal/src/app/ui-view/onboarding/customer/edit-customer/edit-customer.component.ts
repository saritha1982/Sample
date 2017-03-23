import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, Validators} from "@angular/forms";
import {ServicesComponent} from "../../../../services/services.component";

@Component({
  selector: 'app-edit-customer',
  templateUrl: './edit-customer.component.html',
  styleUrls: ['./edit-customer.component.css']
})
export class EditCustomerComponent implements OnInit {

  customerId : any;
  customerName : any;
  region : any;
  constructor(private service:ServicesComponent) {
    this.customerForm.controls["customerName"].setValue(this.service.getEditCustomerName());
    this.customerForm.controls["emailAddress"].setValue(this.service.getEditEmailAddress());
    this.customerForm.controls["region"].setValue(this.service.getEditRegion());
    this.customerForm.controls["contact"].setValue(this.service.getEditContact());

    this.customerName = this.customerForm.controls["customerName"].value;
    this.region = this.customerForm.controls["region"].value;
    console.log( this.customerForm.controls["region"].value);
    console.log(this.region);
    console.log(this.customerName);
    this.service.getCustomerIdDynamically(this.customerName)
      .then(response => {
        this.customerId = response;
        console.log("Customer ID for " + this.customerName + " is " + this.customerId);
      })
      .catch(err => {
        console.log("this is error"+err);
      });
  }

  ngOnInit() {
  }

  submitted = false;
  body = null;
  internal : any = false;
  regionList:any = ['APJC','EMEA','US'];

  customerForm = new FormGroup({
    customerName : new FormControl({value: '', disabled: true}, Validators.required),
    emailAddress : new FormControl(''),
    region : new FormControl(),
    contact : new FormControl(''),
  });

  goBack() : void {
    window.history.back();
  }

  onSubmit() {

    this.body ={"customerName":this.customerForm.controls["customerName"].value,
      "customerId":this.customerId,
      "emailAddress":this.customerForm.controls["emailAddress"].value,
      "contact":this.customerForm.controls["contact"].value,
      "region":this.customerForm.controls["region"].value,
      "internal" : this.internal
    };

    console.log(this.body);
    this.service.editCustomer(this.body)
      .then(response => {
        console.log("Customer Updated");
        //this.customerExists = false;
        this.submitted = true;
      })
      .catch(err => {
        console.log("this is error"+err);
      });

  }

}
