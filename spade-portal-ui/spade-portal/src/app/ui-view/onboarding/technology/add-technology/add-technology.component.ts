import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, Validator } from "@angular/forms";
import { ServicesComponent } from '../../../../services/services.component';

@Component({
  selector: 'app-add-technology',
  templateUrl: './add-technology.component.html',
  styleUrls: ['./add-technology.component.css']
})
export class AddTechnologyComponent implements OnInit {

  constructor(private service : ServicesComponent) { }

  ngOnInit() {
  }






}
