import {Component, OnInit, Injectable} from "@angular/core";
import {Http, Headers, RequestOptions} from "@angular/http";
import "rxjs/add/operator/toPromise";
import "rxjs/add/operator/map";

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})

@Injectable()
export class ServicesComponent implements OnInit {

  /*Variable Declaration*/
  customerId : any;
  onboardId : any;
  projectList : any;
  name:any;
  mail:any;
  level:any;
  disable:any;
  projectDetailService : any;

  //http://wwwin-spadekmportal-dev.cisco.com
  //http://wwwin-spadekmportal-dev.cisco.com

  private projectRolesUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/projectRoles";
  private addCustomerUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/";

  constructor(private http : Http) { }

  ngOnInit() {
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

  //Dummy CustomerId for Manage Projects
  getDummyCustomerId() : any {
    return "909edaad-0238-11e7-aca3-d8cb8ab34019";
  }

  //Dynamic Customer ID
  getCustomerIdDynamically(customerName:any) :Promise<any> {
    let customerIdUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerName + "/customerId";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    return this.http.get(customerIdUrl , options).toPromise()
      .then(res => {
        //console.log(res.json());
        return res.json().customerId;
      })
  }

  //Getting CustomerId for Manage Projects
  getCustomerId(onboardId : any) : Promise<any> {
    let customerIdUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/"+onboardId;
    let headers = new Headers({'Authorization' : 'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    return this.http.get(customerIdUrl , options)
      .toPromise()
      .then(response => {
        console.log(response);
        return response;
      })
      .catch(this.handleError);
  }

  //Manage Projects
  getProjectsList(customerId):Promise<any> {
    let projectListUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/projects";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    let options = new RequestOptions({headers : headers});

    return this.http.get(projectListUrl, options)
      .toPromise()
      .then(response => {
        return response;
      })
      .catch(this.handleError);
  }

  //Add a Project
  createProject(body, customerId): Promise<any>{
    let createProjectUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/projects";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    body = JSON.stringify(body);
    console.log(body);

    return this.http.post(createProjectUrl,body,options).toPromise()
      .then(res=> {
        console.log("project created");
        return true;
      })
      .catch(err=>{
        console.log("its error\n");
      });
  }

  //Manage Users
  getManageUsersList(customerId)  : Promise<any> {
    let manageUsersUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/user";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    let options = new RequestOptions({headers : headers});

    return this.http.get(manageUsersUrl,options)
      .toPromise()
      .then(response => {
        return response;
      })
      .catch(this.handleError);
  }

  //Add a User
  createUser(body, customerId) : Promise<any> {
    let createUserUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/user";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    body = JSON.stringify(body);
    console.log(body);

    return this.http.post(createUserUrl,body,options).toPromise()
      .then(res=>{
        console.log("user created");
        return true;
      })
      .catch(err=>{
        console.log("this is error");
      });
  }

  //Manage Customers
  getCustomerName(workType : any) : Promise<any> {
    let customerNameUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/workTypes/" + workType + "/customerNames";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    return this.http.get(customerNameUrl,options)
      .toPromise()
      .then(response => {

        return response;
      })
      .catch(this.handleError);
  }

  //Manage Customer List
  getAllCustomers() : Promise<any> {
    let customerNameUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    return this.http.get(customerNameUrl,options)
      .toPromise()
      .then(response => {

        return response;
      })
      .catch(this.handleError);
  }

  //Add a Customer
  addCustomer(body : any) : Promise<any> {
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    console.log(body);
    body = JSON.stringify(body);
    console.log(body);

    return this.http.post(this.addCustomerUrl,body,options).toPromise()
      .then(res=>{
        console.log("customer created");
        return true;
      })
      .catch(err=>{
        console.log("this is error");
      });
  }

  //Add a Technology
  addTechnology(body : any, customerId : any) : Promise<any> {
    let addTechnologyUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/technologies";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    body  = JSON.stringify(body);
    console.log(addTechnologyUrl);

    return this.http.post(addTechnologyUrl , body , options).toPromise()
      .then(res => {
        console.log(res.json());
        return res;
      });
  }

  //Add a Category
  addCategory(technologyName : any, body : any, customerId : any) : Promise<any> {
    let addCategoryUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/" + technologyName + "/categories";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    body  = JSON.stringify(body);
    console.log(addCategoryUrl);

    return this.http.post(addCategoryUrl , body , options).toPromise()
      .then(res => {
        console.log(res.json());
        return res;
      });
  }

  //Disable User
  disableUser(body : any, customerId : any) : Promise<any>{
    let disableUserUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/user/disable";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    body = JSON.stringify(body);
    console.log(body);

    return this.http.post(disableUserUrl ,body ,options).toPromise()
    .then(res => {

    })
    .catch(this.handleError);
  }

  //Close Project
  deleteProject(onBoardId:any, customerId : any):Promise<any> {
    let deleteProjectUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/projects/"+onBoardId;
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    return this.http.post(deleteProjectUrl,options).toPromise()
    .then(response => {
      return response;
    })
    .catch(this.handleError);
  }

  //Technology List
  getTechnology(customerId :any) : Promise<any> {
    let technologyUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/technologies";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    return this.http.get(technologyUrl,options)
      .toPromise()
      .then(response => {
        console.log(response.json());
        return response;
      })
      .catch(this.handleError);
  }

  //Category List
  getCategory(technologyName : any, customerId : any):Promise<any> {
    let categoryUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/"+technologyName+"/categories";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    return this.http.get(categoryUrl ,options ).toPromise()
      .then(response => {
        console.log(response.json());
          return response;
      })
      .catch(this.handleError);
}

  //WorkType List
  getWorkType() : Promise<any> {
    let workTypeUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/workTypes";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});
     return this.http.get(workTypeUrl,options)
       .toPromise()
       .then(response => {
         return response;
       })
       .catch(this.handleError);
  }

  //Project Request Type List
  getProjectRequestType() : Promise<any> {
    let projectRequestTypeUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/projectRequestTypes";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    return this.http.get(projectRequestTypeUrl,options)
      .toPromise()
      .then(response => {
        return response;
      })
      .catch(this.handleError);
  }

  //Project Roles List
  getProjectRolesList() : Promise<any>{
    let headers=  new Headers({'Authorization' : 'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    let options = new RequestOptions({headers : headers});

    return this.http.get(this.projectRolesUrl, options)
      .toPromise()
      .then(response => {
        return response;
      })
      .catch(this.handleError);

  }

  //Project Manager List
  getProjectManagerList(technologyName : any, customerId : any) : Promise<any> {
    let projectManagerUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/projects/" + technologyName +"/managers";
    let headers=  new Headers({'Authorization' : 'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    let options = new RequestOptions({headers : headers});

    return this.http.get(projectManagerUrl, options)
      .toPromise()
      .then(response => {
        console.log(response.json());
        return response;
      })
      .catch(this.handleError);
  }

  //Parent Project List
  getParentProjectList(technologyName : any, customerId : any) : Promise<any> {
    let parentProjectUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/projects/"+ technologyName +"/parentProjects";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    let options = new RequestOptions({headers : headers});

    return this.http.get(parentProjectUrl,options)
      .toPromise()
      .then(response => {
        return response;
      })
      .catch(this.handleError);
  }

  //Earlier Project Reference List
  getEarlierProjectReferenceList(customerId : any) : Promise<any> {
    let earlierProjectReferenceUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/projects";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    let options = new RequestOptions({headers : headers});

    return this.http.get(earlierProjectReferenceUrl,options)
      .toPromise()
      .then(response => {
        return response;
      })
      .catch(this.handleError);
  }

  //Project Type List
  getProjectTypeList() : Promise<any> {
    let projectTypeUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/projectTypes";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    let options = new RequestOptions({headers : headers});

    return this.http.get(projectTypeUrl,options)
      .toPromise()
      .then(response => {
        return response;
      })
      .catch(this.handleError);
  }

  //Verifying Github User
  verifyGithubUser(email : any) : Promise<any> {
    let userName = email.split("@")[0];
    let githubUserUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/"+userName;
    let headers = new Headers({'Authorization' : 'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    let options = new RequestOptions({headers : headers});

    return this.http.get(githubUserUrl , options)
      .toPromise()
      .then(response => {
        return response;
      })
      .catch(this.handleError);
  }

  //Edit Project : Fetching the Project Details
  getProjectDetails(onboardId : any, customerId : any) : Promise<any> {
    let projectDetailUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/projects/" + onboardId;
    let headers = new Headers({'Authorization' : 'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    this.onboardId  = onboardId;
    console.log("Inside getProjectDetails function" + projectDetailUrl);

    return this.http.get(projectDetailUrl , options)
      .toPromise()
      .then(response => {
        //console.log(response);
        this.projectDetailService = response.json();
        console.log("Project Detail Service");
        console.log(this.projectDetailService);
        return response;
      })
      .catch(this.handleError);
  }

  //Fetching the OnboardId
  getOnboardId():any{
    console.log("Inside getOnboardId = " + this.onboardId);
    return this.onboardId;
  }

  //Edit Project : Fetching the Pre-filled Data
  getVariable() : any {
    return this.projectDetailService;
  }

  //Edit Project Fucntion : To Update the Changes.
  editProject(body : any, customerId : any) : Promise<any> {
    let editProjectUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/" + customerId + "/projects/" + this.onboardId;
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    console.log(body);
    body = JSON.stringify(body);
    console.log(body);

    return this.http.put(editProjectUrl, body, options).toPromise()
      .then(res=>{
        console.log("customer created");
        return true;
      })
      .catch(err=>{
        console.log("this is error");
      });
  }

  setEditProjectlist(name:any,mail:any,level:any,list:any) : any{
    console.log("setting project list for editing");
    console.log(list);
    this.projectList = list;
    this.name = name;
    this.mail = mail;
    this.level = level;
    //this.disable = disable;
  }

  getEditProjectlist():any{
    console.log("Inside getting projects to be editted");
    return this.projectList;
  }
  getUserName() :any {
    return this.name;
  }
  getMail()  : any{
    return this.mail;
  }
  /*getDisable() :any{
    return this.disable;
  }*/
  getAccesLevel() :any{
    return this.level;
  }

  getOnboardIdForUser(projectName : any):Promise<any>{
    let customerIdUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/user/"+projectName+"/onBoardId";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    console.log(projectName);

    return this.http.get(customerIdUrl , options).toPromise()
    .then(res => {
      console.log("customer id is"+ res);
      return res;
    })
    .catch(err=>{
        console.log("this is error");
      });

  }

  //Edit User
  editUser(body : any,onBoardId : any,userName : any):Promise<any>{
    let editUserUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/user/"+userName+"/project/"+onBoardId;
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});
    body = JSON.stringify(body);

    console.log(body);
    console.log("customer id");
    console.log(onBoardId);

    return this.http.post(editUserUrl , body,options).toPromise()
    .then(res => {
        console.log("user edited");
        return res;

    }).catch(err => {
      console.log("This is error" + err);
    })
  }

  editCustomer(body  :any) : Promise<any> {
    let editCustomerUrl = "http://wwwin-spadekmportal-dev.cisco.com/spade-portal-webservice/rest/customers/edit";
    let headers = new Headers({'Authorization':'Basic eXVnc2luZ2g6T012aXNobnVqaWppci85'});
    headers.append('Content-Type','application/json');
    let options = new RequestOptions({headers : headers});

    body = JSON.stringify(body);
    console.log(body);

    return this.http.post(editCustomerUrl , body, options).toPromise()
      .then(response => {
        console.log("Customer edited");
        return response;
      })
        .catch(err => {
        console.log("This is error" + err);
      })
  }

  customerName : any;
  emailAddress : any;
  region :any;
  contact : any;
  setEditCustomerDetails(customerName : any, emailAddress : any, region :any, contact : any) : any {
    this.customerName = customerName;
    this.emailAddress = emailAddress;
    this.region = region;
    this.contact = contact;
  }

  getEditCustomerName() : any {
    return this.customerName;
  }
  getEditEmailAddress() : any {
    return this.emailAddress;
  }
  getEditRegion() : any {
    return this.region;
  }

  getEditContact() : any {
    return this.contact;
  }
}
