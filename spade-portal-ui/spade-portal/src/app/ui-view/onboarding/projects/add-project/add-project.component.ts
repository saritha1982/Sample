import {Component, OnInit} from "@angular/core";
import {FormGroup, FormControl, Validators, NgForm} from "@angular/forms";
import {ServicesComponent} from "../../../../services/services.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css','../../onboarding.component.css']
})

export class AddProjectComponent implements OnInit {

  /* List Variables Declaration */
  technologyList : any;
  workTypeList : any;
  customerNameList : any;
  projectRequestTypeList : any;
  projectRoleList : any;
  projectManagerList: any;
  parentProjectList : any;
  earlierProjectReferenceList : any;
  projectTypeList : any;

  /* Variables Declaration */
  submitted = false;
  successfulSubmission = false;
  active = true;
  customerId : any;

  checkAll = null;
  check1 = null;
  check2 = null;
  check3 = null;
  body:any ;
  projectCreated : any = false;
  message:any ;
  earlierFlag = true;
  projectIdName:any;
  contractIdName:any;
  clarityIdName:any;

  /* Constructor invoking the Services */
  constructor( private service : ServicesComponent, private router : Router ) {

    //Getting WorkType
    this.service.getWorkType()
      .then(workType => {
        this.workTypeList = workType.json();
        console.log(this.workTypeList);
      })
      .catch(err => {
        console.log("this is error"+err);
      });

    //Getting Project Roles List
    this.service.getProjectRolesList()
      .then(projectRole =>{
        this.projectRoleList = projectRole.json();
        console.log(this.projectRoleList);
      })
      .catch(err=> {
        console.log("this is error" + err);
      });

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

  }

  ngOnInit() {
  }

  /* Project Onboarding Form */
  projectOnboardingForm = new FormGroup({

    /*CheckBox Controls*/
    checkAll : new FormControl(false),
    check1 : new FormControl(false),
    check2 : new FormControl(false),
    check3 : new FormControl(false),

    /*Onboard*/
    projectId : new FormControl(null, Validators.required),
    contractId : new FormControl(null),
    clarityId : new FormControl(null),
    onboardId : new FormControl(null),

    /*Project Details*/
    technology: new FormControl(null, Validators.required),
    category: new FormControl(null, Validators.required),
    workType: new FormControl(null, Validators.required),
    customerName: new FormControl(null, Validators.required),
    projectStartDate : new FormControl(null),
    projectEndDate : new FormControl(null),
    projectName : new FormControl(null, Validators.required),
    projectRequestType  : new FormControl("New Request"),
    earlierProjectReference : new FormControl(null),
    businessUnit : new FormControl(null),
    projectManager : new FormControl(null),
    projectType : new FormControl(null),
    parentProject : new FormControl(''),
    notes : new FormControl(null),
    //userName : new FormControl(null, Validators.required)
  });

  customerStatus = false;
  technologyStatus = false;
  //Checking Customer
  updateCustomerList() {
    if(this.projectOnboardingForm.controls["workType"].value == "Internal"
      || this.projectOnboardingForm.controls["workType"].value == "Customer" || this.projectOnboardingForm.controls["workType"].value === null)
        this.projectOnboardingForm.controls["customerName"].setValue(null);
  }

  checkCustomer() {
    console.log(this.projectOnboardingForm.controls["customerName"].value);
    if(this.projectOnboardingForm.controls["customerName"].value === null ||this.projectOnboardingForm.controls["customerName"].value === "--Select a Customer--") {
      alert("Please select a Customer before adding a Technology.");
      this.customerStatus = false;
    }
    else
      this.customerStatus = true;
  }

  checkTechnology() {
    console.log(this.projectOnboardingForm.controls["technology"].value);
    if(this.projectOnboardingForm.controls["technology"].value === null ||this.projectOnboardingForm.controls["technology"].value === "--Select a Technology--") {
      alert("Please select a Technology before adding a Category.");
      this.technologyStatus = false;
    }
    else
      this.technologyStatus = true;
  }

  technologyForm = new FormGroup({
    technologyName : new FormControl('', Validators.required),
    //gitRepoName : new FormControl('',Validators.required)
  });


  addTechnology() {
    console.log(this.technologyForm.controls["technologyName"].value);
    console.log(this.technologyForm.controls["gitRepoName"].value);
    let body = {
      "technologyName":this.technologyForm.controls["technologyName"].value,
      "githubRepoName":this.technologyForm.controls["technologyName"].value
    };

    this.service.addTechnology(body, this.customerId).then(res => {
      console.log("Technology added");

      this.service.getTechnology(this.customerId).then(response => {
        //console.log("Adding the Technology");
        this.technologyList = response.json();
      });

      //window.location.reload();
    })
      .catch(err => {
        console.log("this is error" + err);
      });

  }

  categoryForm = new FormGroup({
    categoryName : new FormControl('', Validators.required)
  });


  addCategory() {
    console.log(this.projectOnboardingForm.controls["technology"].value);
    console.log(this.categoryForm.controls["categoryName"].value);
    let body = {
      "categoryName": this.categoryForm.controls["categoryName"].value
    };

    this.service.addCategory(this.projectOnboardingForm.controls["technology"].value, body, this.customerId)
      .then(res => {
        console.log("Category added");
        this.service.getCategory(this.projectOnboardingForm.controls["technology"].value, this.customerId)
          .then(response => {
            console.log("Adding the Category");
            this.categoryList = response.json();
          })
          .catch(err => {
            console.log("this is error" + err);
          });
        })
        .catch(err => {
          console.log("this is error" + err);
        });

  }

  // CheckBox Option - Select All
  /*selectAll() {
    if(this.projectOnboardingForm.controls["checkAll"].value === true)
      this.projectOnboardingForm.controls["checkAll"].setValue(false);
    else if(this.projectOnboardingForm.controls["checkAll"].value === false)
      this.projectOnboardingForm.controls["checkAll"].setValue(true);
    console.log(this.projectOnboardingForm.controls["checkAll"].value);

    if(this.projectOnboardingForm.controls["checkAll"].value === true) {
      this.projectOnboardingForm.controls["check1"].setValue(true);
      this.projectOnboardingForm.controls["check2"].setValue(true);
      this.projectOnboardingForm.controls["check3"].setValue(true);
      this.check1 =  this.projectOnboardingForm.controls["check1"].value;
      this.check2 =  this.projectOnboardingForm.controls["check2"].value;
      this.check3 =  this.projectOnboardingForm.controls["check3"].value;
    }
    else {
      this.projectOnboardingForm.controls["check1"].setValue(false);
      this.projectOnboardingForm.controls["check2"].setValue(false);
      this.projectOnboardingForm.controls["check3"].setValue(false);
      this.check1 =  this.projectOnboardingForm.controls["check1"].value;
      this.check2 =  this.projectOnboardingForm.controls["check2"].value;
      this.check3 =  this.projectOnboardingForm.controls["check3"].value;
    }

  }

  //CheckBox Option - Individual Selection
  changeOption(choice : number) {
    if (this.projectOnboardingForm.controls["check" + choice].value === true)
      this.projectOnboardingForm.controls["check" + choice].setValue(false);
    else if (this.projectOnboardingForm.controls["check" + choice].value === false)
      this.projectOnboardingForm.controls["check" + choice].setValue(true);

    console.log("Check" + choice + " = " + this.projectOnboardingForm.controls["check" + choice].value);
    if(choice == 1)
      this.check1 =  this.projectOnboardingForm.controls["check" + choice].value;
    if(choice == 2)
      this.check2 =  this.projectOnboardingForm.controls["check" + choice].value;
    if(choice == 3)
      this.check3 =  this.projectOnboardingForm.controls["check" + choice].value;

    if(this.check1 == true && this.check2 == true && this.check3 == true)
      this.projectOnboardingForm.controls["checkAll"].setValue(true);
    else
      this.projectOnboardingForm.controls["checkAll"].setValue(false);
    console.log("CheckAll = " + this.projectOnboardingForm.controls["checkAll"].value);

  }*/

  /* SYNC Variables*/
  projectRequestType = this.projectOnboardingForm.controls["projectRequestType"].valueChanges;

  getCustomerList() {
    let workType = this.projectOnboardingForm.controls["workType"].value;
    console.log(workType);

    //Getting CustomerName
    this.service.getCustomerName(workType)
      .then(customerName => {
        console.log('hi'+customerName);
        this.customerNameList = customerName.json();
        console.log("customer List" +this.customerNameList);
      })
      .catch(err => {
        console.log("this is error"+err);
      });

  }

  getCustomerId() {
    let customerName = this.projectOnboardingForm.controls["customerName"].value;
    console.log(customerName);

    this.service.getCustomerIdDynamically(customerName)
      .then(response => {
      this.customerId = response;
      console.log("Customer ID for " + customerName + " is " + this.customerId);

      //Getting Technology
      this.service.getTechnology(this.customerId)
        .then(technology => {
          this.technologyList = technology.json();
          console.log(this.technologyList);
        })
        .catch(err => {
          console.log("this is error"+err);
        });

      })
      .catch(err => {
      console.log("this is error"+err);
    });

  }

  //Navigate to Add Customer Page
  gotoAddCustomer() {
    this.router.navigate(['onboarding/add-customer']);
  }

  c1:any =null;
  c2:any =null;
  c3:any =null;
  id: any = '';
  pid:any[]=['','',''];

  p1:any;

  /**********************************************************Associate User**********************************************************/

  accessLevels = ['Admin','User','Viewer'];
  userName:any ='';
  emailAddress:any='';
  accessLevel:any='Admin';
  projectRole:any='';

  widget:any=[];
  wflag : any = '';

  editFlag : any = false;
  edits1 : any=[];
  edits2 : any=[];
  edits3 : any=[];

  eUname:any='';
  edmail:any='';
  echecks:any= false;
  eaccess1:any='';
  eprr1:any='';

  del:any=true;

  subFlag: any= true;

  xname:any ='';

  verifyUser : any =true;
  notverified : any ;
  count = 0;
  add : any = false;
  admin:any = false;
  firstRow : any ;

  submit(form:NgForm,bool:any){


    if(this.emailAddress != '' && this.emailAddress != null) {
      this.count++;
    }

    this.accessLevels = ['Admin','User','Viewer'];
    this.notverified = false;
    console.log("count = " + this.count);
    //var verifyUser : any = false;
    //alert("inside submit");
    console.log(bool);

    console.log(form.value.userName);
    //console.log(form.value);

    console.log("checking for onboard id"+this.projectIdName+this.contractIdName+this.clarityIdName);

    //console.log(form.controls["userName"][0]);

   this.service.verifyGithubUser(form.value.emailAddress).then(response => {
      this.verifyUser = response.json();
      this.notverified = !this.verifyUser;
   })
   .catch(err=> {
        console.log("this is error" + err);
   });

    if(form.controls["emailAddress"].value!=''){
      this.wflag = true;
      this.widget.push(form.value);
      console.log(this.widget);
      this.userName='';
      this.emailAddress='';
      this.accessLevel='';
      this.projectRole='';
    }
    console.log("inside submit"+this.wflag);
    console.log("Inside submit"+this.editFlag);

    if(this.editFlag == true){
      this.wflag = false;
      this.edits3.push(form.value);
    }

    if(bool === false){
      this.subFlag = false;
    }

    console.log("****"+this.widget.length);
    /*if(this.widget.length >0){
      this.add = true;
    }*/


     if(form.controls["emailAddress"].value!=''){
        this.subFlag = false;
        this.accessLevel = '';

     }

     console.log(this.widget[0]);

     this.firstRow = this.widget[0];



    //  if(this.widget[0].accessLevel === 'Admin'){
    //    this.admin = true;
    //  }else{
    //    this.admin = false;
    //  }

  }

  addRow(){
    this.subFlag = true;

  }

  delete(name:any,bool:any){


    this.count = this.count-1;

    console.log("hi " + name);
    console.log(this.widget);
    console.log(this.widget.length);
    //var x=[{"n":"y","c":"I"},{"n":"r","accessLevel":"a"}];
    var i=0;
    for(let w of this.widget){
      if(w.userName === name){
        this.widget.splice(i,1);
      }
      i=i+1;
    }

    if(bool === false){
      this.eUname='';
      this.edmail='';
      this.echecks=false;
      this.eaccess1='';
      this.eprr1='';
      this.editFlag = false;
      this.wflag = true;
    }

    if(this.widget.length<1){
      this.add = false;
    }

    if(this.widget.length ==0){
      this.subFlag = true;
      this.accessLevel = 'Admin';
      this.accessLevels = ['Admin','User','Viewer'];;
    }

    // if(this.widget[0].accessLevel === 'Admin'){
    //    this.admin = true;
    //  }else{
    //    this.admin = false;
    //  }

    this.firstRow = this.widget[0];

  }

  edit(name:any){
    console.log("hi inside edit");
    console.log(this.widget.length);
    //this.edits1 = this.widget.slice(0,2);
    //console.log(this.edits1);
    var i = 0;
    var len = this.widget.length;

    for(let w of this.widget){
      if(w.userName === name){
        break;
      }
      i=i+1;
    }

    this.edits1 = this.widget.slice(0,i);
    this.edits2= this.widget[i];
    this.edits3=this.widget.slice(i+1,len);
    //this.edits1 = this.widget.slice(0,1);
    console.log("Inside edits1"+this.edits1);
    console.log("Inside edits2"+this.edits2);
    console.log("Inside edits3"+this.edits3);

    this.eUname = this.edits2.userName;
    this.edmail = this.edits2.emailAddress;
    this.eaccess1 = this.edits2.accessLevel;
    this.eprr1 = this.edits2.projectRole;

    this.editFlag = true;
    this.wflag = false;

    this.xname = this.edits2.userName;
    //alert(this.xname);

    if(this.widget.length ==1){
      this.add = true;
    }else{
      this.add = false;
    }
    if(this.edits2.emailAddress === this.widget[0].emailAddress){
      this.accessLevels = ['Admin','User','Viewer'];;
    }
    this.firstRow = this.widget[0];

  }

  esub(){

    console.log(this.eUname);

    var i=0;
    for(let w of this.widget){
      if(w.userName === this.edits2.userName){
        break;
      }
      i=i+1;
    }
    this.widget[i].userName = this.eUname;
    this.widget[i].emailAddress = this.edmail;
    this.widget[i].accessLevel = this.eaccess1;
    this.widget[i].projectRole = this.eprr1;

    this.editFlag = false;
    this.wflag = true;
    this.accessLevels = ['Admin','User','Viewer'];
    this.firstRow = this.widget[0];
  }

  x=10;

  onboardId : any;
  userAssociated = false;
  onSubmit() {
    if(this.widget.length >0 && this.firstRow.accessLevel === "Admin"){
      this.admin = true;
    }else{
      this.admin = false;
    }
    //let admin : any = this.widget[0].accessLevel;
    //console.log(this.widget[0].accessLevel);
    if(this.count >= 1 && this.admin == true){

      this.submitted = true;
      console.log(this.userAssociated);
      this.userAssociated=true;
      console.log(this.userAssociated);

      /* Logic applied for checkboxes
    if(this.check1 === true && this.check2 === true && this.check3 === true) {
      this.onboardId = this.projectOnboardingForm.controls["projectId"].value
                      + this.projectOnboardingForm.controls["contractId"].value
                      + this.projectOnboardingForm.controls["clarityId"].value;
    }
    else if(this.check1 === true && this.check2 === true)
      this.onboardId = this.projectOnboardingForm.controls["projectId"].value + this.projectOnboardingForm.controls["contractId"].value;
    else if(this.check2 === true && this.check3 === true)
      this.onboardId = this.projectOnboardingForm.controls["contractId"].value + this.projectOnboardingForm.controls["clarityId"].value;
    else if(this.check3 === true && this.check1 === true)
      this.onboardId = this.projectOnboardingForm.controls["projectId"].value + this.projectOnboardingForm.controls["clarityId"].value;
    else if(this.check1 === true)
      this.onboardId = this.projectOnboardingForm.controls["projectId"].value;
    else if(this.check2 === true)
      this.onboardId = this.projectOnboardingForm.controls["contractId"].value;
    else if(this.check3 === true)
      this.onboardId = this.projectOnboardingForm.controls["clarityId"].value;*/

      if(this.projectOnboardingForm.controls["contractId"].value == null && this.projectOnboardingForm.controls["clarityId"].value == null)
        this.onboardId = this.projectOnboardingForm.controls["projectId"].value;
      else if(this.projectOnboardingForm.controls["contractId"].value != null && this.projectOnboardingForm.controls["clarityId"].value != null)
        this.onboardId = this.projectOnboardingForm.controls["projectId"].value + this.projectOnboardingForm.controls["contractId"].value + this.projectOnboardingForm.controls["clarityId"].value;
      else if(this.projectOnboardingForm.controls["contractId"].value != null && this.projectOnboardingForm.controls["clarityId "].value == null)
        this.onboardId = this.projectOnboardingForm.controls["projectId"].value + this.projectOnboardingForm.controls["contractId"].value;
      else if(this.projectOnboardingForm.controls["contractId"].value == null && this.projectOnboardingForm.controls["clarityId"].value != null)
        this.onboardId = this.projectOnboardingForm.controls["projectId"].value + this.projectOnboardingForm.controls["clarityId"].value;

      console.log(this.projectOnboardingForm.controls["onboardId"].value);
      console.log(this.projectOnboardingForm.controls["projectId"].value);
      console.log(this.projectOnboardingForm.controls["contractId"].value);
      console.log(this.projectOnboardingForm.controls["clarityId"].value);
      console.log(this.onboardId);


      this.body = {
        "onBoardId" : this.onboardId,
        "projectId" : this.projectOnboardingForm.controls["projectId"].value,
        "clarityId" :  this.projectOnboardingForm.controls["clarityId"].value,
        "contractId" :  this.projectOnboardingForm.controls["contractId"].value,
        "customerName" : this.projectOnboardingForm.controls["customerName"].value,
        "workType" : this.projectOnboardingForm.controls["workType"].value,
        "technologyName" : this.projectOnboardingForm.controls["technology"].value,
        "categoryName" : this.projectOnboardingForm.controls["category"].value,
        "projectName" : this.projectOnboardingForm.controls["projectName"].value,
        "projectStartDate" : this.projectOnboardingForm.controls["projectStartDate"].value,
        "projectEndDate" : this.projectOnboardingForm.controls["projectEndDate"].value,
        "projectRequestType" : this.projectOnboardingForm.controls["projectRequestType"].value,
        "earlierProjectReference" : this.projectOnboardingForm.controls["earlierProjectReference"].value,
        "businessUnit" : this.projectOnboardingForm.controls["businessUnit"].value,
        "projectManager" :  "as-ci-user.gen@cisco.com",
        "projectType" : this.projectOnboardingForm.controls["projectType"].value,
        "parentProject" : this.projectOnboardingForm.controls["parentProject"].value,
        "notes" : this.projectOnboardingForm.controls["notes"].value,
        "owner" : "as-ci-user.gen",
        "users":this.widget,
      };

        console.log(this.body);
        console.log("Customer ID for " + this.projectOnboardingForm.controls["customerName"].value + " is " + this.customerId);
        this.service.createProject(this.body, this.customerId)
        .then( value => {
          console.log("project creation ends");
          //this.submitted=true;
          if(this.notverified){
            //alert("The user doesn't exist in the github");
        }
          this.submitted = false;
          this.successfulSubmission = true;

      })
      .catch(err=> {
          console.log("this is error" + err);
        });

    }       //If Ends
    else
      alert("Atleast One Admin user must be added to the Project being On-Boarded!!");


  }

  check(x:any){
    if(x==1){
      this.c1= true;
    }
    else if(x==2){
      this.c2=true;
    }else{
      this.c3=true;
    }
  }

  ids(x:any){


    console.log(this.pid);

    if(x == 1 && this.c1 == true){
      this.pid[0]=this.projectOnboardingForm.controls['ProjectId'].value;
      this.id += this.pid[0]+'/';
      //this.projectOnboardingForm.controls['PId'].setValue = this.id;
      console.log(this.projectOnboardingForm.controls['PId']);
      console.log(this.id);
    }
    else if(x==2 && this.c2== true){

      if(this.pid[0] == ''){
        //this.pid[0] ='/';
        this.id += '/';
      }
      this.pid[1]=this.projectOnboardingForm.controls['contractId'].value;
      this.id += this.pid[1]+'/';

      //this.id += this.projectOnboardingForm.controls['contractId'].value+'/';
      //this.projectOnboardingForm.controls['PId'].setValue = this.id;
      console.log(this.id);
    }else if(x==3 && this.c3 == true){
      if(this.pid[1] == ''){
        //this.pid[1] = '/';
        this.id += '/';

      }
      if(this.pid[0] == '' && this.pid[1] == ''){
        //this.pid[0]='/';
        //this.pid[1]='/';
        this.id += ' / /';
      }
      this.pid[2]=this.projectOnboardingForm.controls['ClarityId'].value;
      this.id += this.pid[2];

      //this.id += this.projectOnboardingForm.controls['ClarityId'].value;
      //this.projectOnboardingForm.controls['PId'].setValue = this.id;
      console.log(this.id);
    }
    this.submitted = false;
  }

  categoryList : any=[];
  getProjectManager() {
    console.log(this.projectOnboardingForm.controls["technology"].value);
    /*
    this.service.getProjectManagerList(this.projectOnboardingForm.controls["technology"].value, this.customerId)
      .then(projectMangerList => {
        this.projectManagerList = projectMangerList.json();
        console.log(this.technologyList);
      })
      .catch(err => {
        console.log("this is error"+err);
      });
    */

    //Getting Category List
    this.service.getCategory(this.projectOnboardingForm.controls["technology"].value, this.customerId)
      .then(response => {
        console.log("Adding the Category");
        this.categoryList = response.json();
      })
      .catch(err => {
        console.log("this is error" + err);
      });

    //Getting Parent Project List
    this.service.getParentProjectList(this.projectOnboardingForm.controls["technology"].value, this.customerId)
      .then(parentProject =>{
        this.parentProjectList = parentProject.json();
        console.log(this.parentProjectList);
      })
      .catch(err=> {
        console.log("this is error" + err);
      });

    //Getting Earlier Project Reference List
    this.service.getEarlierProjectReferenceList(this.customerId)
      .then(earlierProjectReference =>{
        this.earlierProjectReferenceList = earlierProjectReference.json();
        console.log(this.earlierProjectReferenceList);
      })
      .catch(err=> {
        console.log("this is error" + err);
      });


  }

  goBack() : void {
    window.location.reload();
  }

}
