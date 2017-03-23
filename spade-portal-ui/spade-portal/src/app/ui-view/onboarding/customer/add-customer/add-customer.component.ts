import { Component, OnInit } from '@angular/core';
import {ServicesComponent} from "../../../../services/services.component";
import {FormGroup, FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-customer',
  templateUrl: './add-customer.component.html',
  styleUrls: ['./add-customer.component.css','../../onboarding.component.css']
})
export class AddCustomerComponent implements OnInit {

  constructor(private service:ServicesComponent) { }

  ngOnInit() {
  }

  submitted = false;
  body = null;
  internal : any = false;
  customerList : any;
  customerExists : any = false;
  names : any;
  regionList:any = ['APJC','EMEA','US'];

  customerForm = new FormGroup({
    customerName : new FormControl(null, Validators.required),
    emailAddress : new FormControl(null),
    region : new FormControl(null),
    contact : new FormControl(null),
  });

  onSubmit() {

    this.body ={
      "name":this.customerForm.controls["customerName"].value,
      "emailAddress":this.customerForm.controls["emailAddress"].value,
      "region":this.customerForm.controls["region"].value,
      "contact":this.customerForm.controls["contact"].value,
      "internal" : this.internal
    };

    this.service.addCustomer(this.body)
      .then(response => {
        console.log("customer added");
        this.customerExists = false;
        this.submitted = true;
      })
      .catch(err => {
        console.log("this is error"+err);
      });

  }

  gotoAddProject() : any {
    window.history.back();
  }

  goBack() : void {
    window.location.reload();
  }

}
