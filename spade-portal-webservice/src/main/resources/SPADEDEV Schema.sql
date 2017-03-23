CREATE SCHEMA spadedev;

CREATE TABLE spadedev.portalconfig 
(
  ConfigId VARCHAR(36),
ConfigKey VARCHAR(100),
  
ConfigValue VARCHAR(100),
PRIMARY KEY(ConfigId));




CREATE TABLE spadedev.activitiworkflow 

(
  Id VARCHAR(36), 
 
ProcessName VARCHAR(125),

ActivitiPDId VARCHAR(125),
PRIMARY KEY(Id)

);



CREATE TABLE spadedev.customerinfo 

(
  CustomerId VARCHAR(36),
  

CustomerName VARCHAR(125),
  
CustomerType VARCHAR(125),
  
Region VARCHAR(125) ,
  

EmailAddress VARCHAR(125),
  
Contact VARCHAR(125),
 
 GitHubOrgId VARCHAR(36),
  
OrgId VARCHAR(36),

Internal TINYINT,  

PRIMARY KEY (CustomerId));





CREATE TABLE spadedev.worktype 

(
    
	WorkTypeId VARCHAR(36),
	WorkTypeName VARCHAR(125),
  CustomerId VARCHAR(36),
PRIMARY KEY (WorkTypeId));





CREATE TABLE spadedev.role 
(
  RoleId VARCHAR(36),
  
RoleName VARCHAR(125),

GroupId VARCHAR(36),
  
PRIMARY KEY (RoleId));



CREATE TABLE spadedev.uiview 
(
  ViewId VARCHAR(36),
  
TabId VARCHAR(36),
  
RoleId VARCHAR(36),

PRIMARY KEY (ViewId));



CREATE TABLE spadedev.technology 
(
  TechnologyId VARCHAR(36),
  
TechnologyName VARCHAR(125),
  
GroupId VARCHAR(36),
  
RallyProjectObjId BIGINT,
  
CustomerId VARCHAR(36),
  
GitHubRepoId VARCHAR(36),
  
PRIMARY KEY (TechnologyId));



CREATE TABLE spadedev.organisationunit
 
(
  
  OrgId VARCHAR(36),
  OrgUnitName VARCHAR(125),
 
 Description MEDIUMTEXT,
  
RallyWorkspaceObjId BIGINT,
  
PRIMARY KEY (OrgId));


CREATE TABLE spadedev.category
(
  TechnologyId VARCHAR(36),
RallyProjectObjId BIGINT,
GitHubFolder VARCHAR(125));


CREATE TABLE spadedev.tabs

(
  TabId VARCHAR(36),
  TabsName VARCHAR(125),
  
PRIMARY KEY (TabId));


CREATE TABLE spadedev.projecttype 
(
  ProjectTypeId VARCHAR(36),
  
ProjectTypeName VARCHAR(125),
  
CustomerId VARCHAR(36),
PRIMARY KEY (ProjectTypeId));



CREATE TABLE spadedev.projectrequesttype 
(
  ProjectRequestTypeId VARCHAR(36),
  ProjectRequestTypeName VARCHAR(125),
CustomerId VARCHAR(36),
PRIMARY KEY (ProjectRequestTypeId));


CREATE TABLE spadedev.projectonboard 
(
  OnBoardId VARCHAR(125),
  ProjectId VARCHAR(125),
  
TechnologyId VARCHAR(36),
  
WorkTypeId VARCHAR(36) ,
  
CustomerId VARCHAR(36),
  
ProjectStartDate DATE,
  
ProjectEndDate DATE,

  ProjectName VARCHAR(125),
  
ProjectRequestTypeId VARCHAR(36), 
  EarlierProjectReference VARCHAR(125),
 
ProjectManager VARCHAR(125),

  ParentProject VARCHAR(125),

  ProjectTypeId VARCHAR(36),
 
 ClarityId VARCHAR(125),
  
ContractId VARCHAR(125),
  
RallyId VARCHAR(36),
  
JenkinsId VARCHAR(36),
 
 GitHubId VARCHAR(36),
 
 ArtifactoryId VARCHAR(36),
 
 SonarId VARCHAR(36),

  ProjectStateId VARCHAR(36),
  
Notes MEDIUMTEXT,
ActivitiPIID VARCHAR(125),

GitHubBranch VARCHAR(125),
RallyProjectObjId BIGINT,
BusinessUnit VARCHAR(125),
 PRIMARY KEY (OnBoardId));



CREATE TABLE spadedev.rallyproject 
(
  RallyProjectObjId BIGINT,
  
Name VARCHAR(125),
 
 ProjectManager VARCHAR(125),
 
RallyWorkspaceObjId BIGINT,
 PRIMARY KEY (RallyProjectObjId));



CREATE TABLE spadedev.roletype 
(
  RoleId VARCHAR(36),
  RoleType VARCHAR(125)
 );


CREATE TABLE spadedev.useronboard 
(
  UserName VARCHAR(125),
  
EmailAddress VARCHAR(125),
  
Workspace VARCHAR(125),

 OnBoardId VARCHAR(125),

Disabled TINYINT ,
AccessLevel VARCHAR(45),
RallyUserObjId BIGINT,
CustomerId VARCHAR(36),
ProjectRoleId VARCHAR(36));


CREATE TABLE spadedev.jenkinsjob 
(
 JenkinsJobId VARCHAR(36),
 JenkinsId VARCHAR(36),
  
JenkinsJobURL VARCHAR(125),
 
 JobName VARCHAR(125),
  
PRIMARY KEY (JenkinsJobId));



CREATE TABLE spadedev.githubrepo 
(
  GitHubRepoId VARCHAR(36),
  GitHubRepoName VARCHAR(125),
 
 URL VARCHAR(125),
  
PRIMARY KEY (GitHubRepoId));



CREATE TABLE spadedev.user 
(
  UserId VARCHAR(36),
  Name VARCHAR(125),
 
 EmailAddress VARCHAR(125),
  
Contact VARCHAR(125),

CustomerId VARCHAR(36),
ProjectRoleId VARCHAR(36),
PRIMARY KEY (UserId));


CREATE TABLE spadedev.groupmember
 (
  GroupId VARCHAR(36),
  
  
UserId VARCHAR(36));


CREATE TABLE spadedev.rallyworkspace 
(
  RallyWorkspaceObjId BIGINT,
  
Name VARCHAR(125),
 
 Owner VARCHAR(125),
  
Description MEDIUMTEXT,
  
PRIMARY KEY (RallyWorkspaceObjId));



CREATE TABLE spadedev.jenkins 
(
   JenkinsId VARCHAR(36),
  UserName VARCHAR(125),

  Password VARCHAR(125),
URL VARCHAR(125),

  InstanceName varchar(125),
   
OrgId VARCHAR(36),

  PRIMARY KEY (JenkinsId));



CREATE TABLE spadedev.github 
(
  GitHubId VARCHAR(36),
  
  UserName VARCHAR(125),
 
 Password VARCHAR(125),
  

URL VARCHAR(125),
 
 Name varchar(45),
    
OrgId VARCHAR(36),
 
 PRIMARY KEY (GitHubId));



CREATE TABLE spadedev.group
 (
  GroupId VARCHAR(36),
  
GroupName VARCHAR(125),
 
 Description MEDIUMTEXT,

CustomerId VARCHAR(36),
PRIMARY KEY(GroupId));


CREATE TABLE spadedev.rally 
(
  RallyId VARCHAR(36),
  UserName VARCHAR(125),
  
Password VARCHAR(125),
  
URL VARCHAR(125),

  Version INT,
    
Name VARCHAR(125),
  
OrgId VARCHAR(36),
  
PRIMARY KEY (RallyId));




CREATE TABLE spadedev.artifactory 
(
  ArtifactoryId VARCHAR(36),
  UserName VARCHAR(125),
  
Password VARCHAR(125),
  

URL VARCHAR(125),
 
 Name varchar(125),
   
OrgId VARCHAR(36),
  
PRIMARY KEY (ArtifactoryId));




CREATE TABLE spadedev.sonarqube 
(
  SonarId VARCHAR(36),
  UserName VARCHAR(125),
  
Password VARCHAR(125),
  

URL VARCHAR(125),
  
Name varchar(125),
   
OrgId VARCHAR(36),
 
 PRIMARY KEY (SonarId));



CREATE TABLE spadedev.projectstate 
(
 
 ProjectStateId VARCHAR(36),
 ProjectStateName VARCHAR(125),
  
OrgId VARCHAR(36),
PRIMARY KEY (ProjectStateId));


CREATE TABLE spadedev.projectrole 

(
ProjectRoleId VARCHAR(36),
  ProjectRole VARCHAR(125),
  Permission VARCHAR(45),

PRIMARY KEY(ProjectRoleId));

CREATE TABLE spadedev.githuborg 

(
GitHubOrgId VARCHAR(36),
  GitHubOrgName VARCHAR(125),

PRIMARY KEY(GitHubOrgId));




alter table spadedev.customerinfo
 add constraint fkey foreign key(OrgId)
references spadedev.organisationunit (OrgId);
alter table spadedev.customerinfo
 add constraint fkey_gitreporgid foreign key(GitHubOrgId)
references spadedev.githuborg (GitHubOrgId);

alter table spadedev.worktype
 add constraint fk foreign key(CustomerId)
references spadedev.customerinfo (CustomerId);

alter table spadedev.role
 add constraint fkey_groupid foreign key(GroupId)
references spadedev.group (GroupId);

alter table spadedev.uiview
 add constraint fk_tabid foreign key(TabId)
references spadedev.tabs (TabId);
alter table spadedev.uiview
 add constraint fk_role foreign key(RoleId)
references spadedev.role (RoleId);

alter table spadedev.technology 
add constraint f_rpid foreign key(RallyProjectObjId)
references spadedev.rallyproject (RallyProjectObjId);
alter table spadedev.technology
 add constraint fk_groupid foreign key(GroupId)
references spadedev.group (GroupId);
alter table spadedev.technology
 add constraint fk_cust foreign key(CustomerId)
references spadedev.customerinfo (CustomerId);
alter table spadedev.technology
 add constraint fk_repo foreign key(GitHubRepoId)
references spadedev.githubrepo (GitHubRepoId);

alter table spadedev.organisationunit 
add constraint fk_rallyworkspace foreign key(RallyWorkspaceObjId)
references spadedev.rallyworkspace (RallyWorkspaceObjId);


alter table spadedev.category 
add constraint fk_categ foreign key(TechnologyId)
references spadedev.technology (TechnologyId);
alter table spadedev.category 
add constraint fk_category foreign key(RallyProjectObjId)
references spadedev.rallyproject (RallyProjectObjId);

alter table spadedev.projecttype
 add constraint fk_ptype foreign key(CustomerId)
references spadedev.customerinfo (CustomerId);



alter table spadedev.projectrequesttype 
add constraint fk_reqtype foreign key(CustomerId)
references spadedev.customerinfo (CustomerId);



alter table spadedev.projectonboard
 add constraint fk_rally foreign key(TechnologyId)
references spadedev.technology (TechnologyId);

alter table spadedev.projectonboard 
add constraint fk_typeid foreign key(ProjectTypeId)
references spadedev.projecttype (ProjectTypeId);


alter table spadedev.projectonboard
 add constraint fk_rallyid foreign key(RallyId)
references spadedev.rally (RallyId);


alter table spadedev.projectonboard 
add constraint fk_jenkinsid foreign key(JenkinsId)
references spadedev.jenkins (JenkinsId);


alter table spadedev.projectonboard 
add constraint fk_githubid foreign key(GitHubId)
references spadedev.github (GitHubId);


alter table spadedev.projectonboard 
add constraint fk_artifactid foreign key(ArtifactoryId)
references spadedev.artifactory (ArtifactoryId);
alter table spadedev.projectonboard 
add constraint fk_sonarid foreign key(SonarId)
references spadedev.sonarqube (SonarId);
alter table spadedev.projectonboard 
add constraint fk_projectstateid foreign key(ProjectStateId)
references spadedev.projectstate (ProjectStateId);
alter table spadedev.projectonboard 
add constraint fk_CustProject foreign key(CustomerId)
references spadedev.customerinfo (CustomerId);
alter table spadedev.projectonboard
 add constraint fkey_wtypeid foreign key(workTypeId)
references spadedev.worktype (workTypeId);
alter table spadedev.projectonboard
 add constraint fkey_rp foreign key(RallyProjectObjId)
references spadedev.rallyproject (RallyProjectObjId);

alter table spadedev.projectonboard
 add constraint fkey_preqtypeid foreign key(ProjectRequestTypeId)
references spadedev.projectrequesttype (ProjectRequestTypeId);

alter table spadedev.rallyproject add constraint fkey_rw foreign key(RallyWorkspaceObjId)
references spadedev.rallyworkspace (RallyWorkspaceObjId);

alter table spadedev.roletype
add constraint fk_roletype foreign key(RoleId)
references spadedev.role (RoleId);

 alter table spadedev.useronboard
 add constraint fk_onboardid foreign key(OnBoardId)
references spadedev.projectonboard (OnBoardId);
alter table spadedev.useronboard
 add constraint fk_projectroleid foreign key(ProjectRoleId)
references spadedev.projectrole (ProjectRoleId);
alter table spadedev.useronboard add constraint fk_usercustid foreign key(CustomerId)references spadedev.customerinfo (CustomerId);

alter table spadedev.jenkinsjob
 add constraint fk_jid foreign key(JenkinsId)
references spadedev.jenkins (JenkinsId);

alter table spadedev.user add constraint fkey_custuser foreign key(CustomerId)references spadedev.customerinfo (CustomerId);

alter table spadedev.groupmember 
add constraint fk_uid foreign key(UserId)
references spadedev.user (UserId);
alter table spadedev.groupmember
 add constraint fk_gid foreign key(GroupId)
references spadedev.group (GroupId);

alter table spadedev.jenkins 
add constraint fk_jorg foreign key(OrgId)
references spadedev.organisationunit (OrgId);

alter table spadedev.github
 add constraint fk_gorg foreign key(OrgId)
references spadedev.organisationunit (OrgId);

alter table spadedev.group add constraint fkey_groupcust foreign key(CustomerId)references spadedev.customerinfo (CustomerId);

alter table spadedev.rally 
add constraint fk_rorg foreign key(OrgId)
references spadedev.organisationunit (OrgId);

alter table spadedev.artifactory
 add constraint fk_aorg foreign key(OrgId)
references spadedev.organisationunit (OrgId);

alter table spadedev.sonarqube
 add constraint fk_sorg foreign key(OrgId)
references spadedev.organisationunit (OrgId);

alter table spadedev.projectstate add constraint fkey_stateorg foreign key(OrgId)references spadedev.organisationunit (OrgId);

alter table spadedev.user add constraint fk_userrole foreign key (ProjectRoleId) references spadedev.projectrole (ProjectRoleId);

create unique index unq_useronboard
    on spadedev.useronboard(EmailAddress, CustomerId, OnBoardId);