create schema spade;



CREATE TABLE spade.portalconfig 
(
  ConfigId VARCHAR(36),
ConfigKey VARCHAR(100),
  
ConfigValue VARCHAR(100));




CREATE TABLE spade.activitiworkflow 

(
  Id VARCHAR(36), 
 
ProcessName VARCHAR(125),

ActivitiPDId VARCHAR(125),
PRIMARY KEY(Id)

);



CREATE TABLE spade.customerinfo 

(
  CustomerId VARCHAR(36),
  

CustomerName VARCHAR(125) ,
  
CustomerType VARCHAR(125),
  
Region VARCHAR(125) ,
  

EmailAddress VARCHAR(125),
  
Contact VARCHAR(125),
 
 GitHubRepoId VARCHAR(36),
  
OrgId VARCHAR(36),

Internal TINYINT,  

PRIMARY KEY (CustomerId));





CREATE TABLE spade.worktype 

(
    WorkTypeName VARCHAR(125),
 
WorkTypeId VARCHAR(36), 
CustomerId VARCHAR(36
),
PRIMARY KEY (WorkTypeId));





CREATE TABLE spade.role 
(
  RoleId VARCHAR(36),
  
RoleName VARCHAR(125),

GroupId VARCHAR(36),
  
PRIMARY KEY (RoleId));



CREATE TABLE spade.uiview 
(
  ViewId VARCHAR(36),
  
TabId VARCHAR(36),
  
RoleId VARCHAR(36),

PRIMARY KEY (ViewId));



CREATE TABLE spade.technology 
(
  TechnologyId VARCHAR(36),
  
TechnologyName VARCHAR(125),
  
GroupId VARCHAR(36),
  
RallyProjectObjId BIGINT,
  
CustomerId VARCHAR(36),
  
GitHubRepoId VARCHAR(36),
  
PRIMARY KEY (TechnologyId));



CREATE TABLE spade.organisationunit
 
(
  OrgUnitName VARCHAR(125),
 
 
OrgId VARCHAR(36),
 
 
Description MEDIUMTEXT,
 
 
RallyWorkspaceObjId BIGINT,
 
 
PRIMARY KEY (OrgId));


CREATE TABLE spade.category
(
  TechnologyId VARCHAR(36),
RallyProjectObjId BIGINT,
GitHubFolder VARCHAR(125));


CREATE TABLE spade.tabs

(
  TabsName VARCHAR(125),
  

TabId VARCHAR(36),
  

PRIMARY KEY (TabId));


CREATE TABLE spade.projecttype 
(
  ProjectTypeId VARCHAR(36),
  
ProjectTypeName VARCHAR(125),
  
CustomerId VARCHAR(36),
PRIMARY KEY (ProjectTypeId));



CREATE TABLE spade.projectRequesttype 
(
  ProjectRequestTypeName VARCHAR(125),
ProjectRequestTypeId VARCHAR(36),
CustomerId VARCHAR(36),
PRIMARY KEY (ProjectRequestTypeId));


CREATE TABLE spade.projectonboard 
(
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
 PRIMARY KEY (ProjectId));



CREATE TABLE spade.rallyproject 
(
  RallyProjectObjId BIGINT,
  
Name VARCHAR(125),
 
 ProjectManager VARCHAR(125),
 
RallyWorkspaceObjId BIGINT,
 PRIMARY KEY (RallyProjectObjId));



CREATE TABLE spade.roletype 
(
  RoleType VARCHAR(125),
  
RoleId VARCHAR(36));


CREATE TABLE spade.useronboard 
(
  UserName VARCHAR(125),
  
EmailAddress VARCHAR(125) UNIQUE,
  
Workspace VARCHAR(125),

 ProjectId VARCHAR(125) ,

Disabled TINYINT ,
AccessLevel VARCHAR(45),
RallyUserObjId BIGINT,
CustomerId VARCHAR(36),
ProjectRoleId VARCHAR(36));


CREATE TABLE spade.jenkinsjob 
(
 JenkinsId VARCHAR(36),
JenkinsJobId VARCHAR(36),
  
JenkinsJobURL VARCHAR(125),
 
 JobName VARCHAR(125),
  
PRIMARY KEY (JenkinsJobId));



CREATE TABLE spade.githubrepo 
(
  GitHubRepoId VARCHAR(36),
 
 URL VARCHAR(125),
  
PRIMARY KEY (GitHubRepoId));



CREATE TABLE spade.user 
(
  Name VARCHAR(125),
 
 EmailAddress VARCHAR(125),
  
Contact VARCHAR(125),
UserId VARCHAR(36),
CustomerId VARCHAR(36),
PRIMARY KEY (UserId));


CREATE TABLE spade.groupmember
 (
  GroupId VARCHAR(36),
  
  
UserId VARCHAR(36));


CREATE TABLE spade.rallyworkspace 
(
  RallyWorkspaceObjId BIGINT,
  
Name VARCHAR(125),
 
 Owner VARCHAR(125),
  
Description MEDIUMTEXT,
  
PRIMARY KEY (RallyWorkspaceObjId));



CREATE TABLE spade.jenkins 
(
  UserName VARCHAR(125),

  Password VARCHAR(125),
URL VARCHAR(125),

  InstanceName varchar(125),
 
 JenkinsId VARCHAR(36),

  
OrgId VARCHAR(36),

  PRIMARY KEY (JenkinsId));



CREATE TABLE spade.github 
(
  UserName VARCHAR(125),
 
 Password VARCHAR(125),
  

URL VARCHAR(125),
 
 Name varchar(45),
  
GitHubId VARCHAR(36),

  
OrgId VARCHAR(36),
 
 PRIMARY KEY (GitHubId));



CREATE TABLE spade.group
 (
  GroupId VARCHAR(36),
  
GroupName VARCHAR(125),
 
 Description MEDIUMTEXT,

CustomerId VARCHAR(36),
PRIMARY KEY(GroupId));


CREATE TABLE spade.rally 
(
  UserName VARCHAR(125),
  
Password VARCHAR(125),
  
URL VARCHAR(125),

  Version INT,
  
RallyId VARCHAR(36),
  
Name VARCHAR(125),
  
OrgId VARCHAR(36),
  
PRIMARY KEY (RallyId));




CREATE TABLE spade.artifactory 
(
  UserName VARCHAR(125),
  
Password VARCHAR(125),
  

URL VARCHAR(125),
 
 Name varchar(125),
 
 ArtifactoryId VARCHAR(36),

  
OrgId VARCHAR(36),
  
PRIMARY KEY (ArtifactoryId));




CREATE TABLE spade.sonarqube 
(
  UserName VARCHAR(125),
  
Password VARCHAR(125),
  

URL VARCHAR(125),
  
Name varchar(125),
 
 SonarId VARCHAR(36),

  
OrgId VARCHAR(36),
 
 PRIMARY KEY (SonarId));



CREATE TABLE spade.projectstate 
(
  ProjectStateName VARCHAR(125),
 
 ProjectStateId VARCHAR(36),
  
OrgId VARCHAR(36),
PRIMARY KEY (ProjectStateId));


CREATE TABLE spade.projectrole 

(
  ProjectRole VARCHAR(125),

ProjectRoleId VARCHAR(36),

PRIMARY KEY(ProjectRoleId));




alter table spade.customerinfo
 add constraint fkey foreign key(OrgId)
references spade.organisationunit (OrgId);
alter table spade.customerinfo
 add constraint fkey_repoid foreign key(GitHubRepoId)
references spade.githubrepo (GitHubRepoId);

alter table spade.worktype
 add constraint fk foreign key(CustomerId)
references spade.customerinfo (CustomerId);

alter table spade.role
 add constraint fkey_groupid foreign key(GroupId)
references spade.group (GroupId);

alter table spade.uiview
 add constraint fk_tabid foreign key(TabId)
references spade.tabs (TabId);
alter table spade.uiview
 add constraint fk_role foreign key(RoleId)
references spade.role (RoleId);

alter table spade.technology 
add constraint f_rpid foreign key(RallyProjectObjId)
references spade.rallyproject (RallyProjectObjId);
alter table spade.technology
 add constraint fk_groupid foreign key(GroupId)
references spade.group (GroupId);
alter table spade.technology
 add constraint fk_cust foreign key(CustomerId)
references spade.customerinfo (CustomerId);
alter table spade.technology
 add constraint fk_repo foreign key(GitHubRepoId)
references spade.githubrepo (GitHubRepoId);

alter table spade.organisationunit 
add constraint fk_rallyworkspace foreign key(RallyWorkspaceObjId)
references spade.rallyworkspace (RallyWorkspaceObjId);


alter table spade.category 
add constraint fk_categ foreign key(TechnologyId)
references spade.technology (TechnologyId);
alter table spade.category 
add constraint fk_category foreign key(RallyProjectObjId)
references spade.rallyproject (RallyProjectObjId);

alter table spade.projecttype
 add constraint fk_ptype foreign key(CustomerId)
references spade.customerinfo (CustomerId);



alter table spade.projectrequesttype 
add constraint fk_reqtype foreign key(CustomerId)
references spade.customerinfo (CustomerId);



alter table spade.projectonboard
 add constraint fk_rally foreign key(TechnologyId)
references spade.technology (TechnologyId);

alter table spade.projectonboard 
add constraint fk_typeid foreign key(ProjectTypeId)
references spade.projecttype (ProjectTypeId);


alter table spade.projectonboard
 add constraint fk_rallyid foreign key(RallyId)
references spade.rally (RallyId);


alter table spade.projectonboard 
add constraint fk_jenkinsid foreign key(JenkinsId)
references spade.jenkins (JenkinsId);


alter table spade.projectonboard 
add constraint fk_githubid foreign key(GitHubId)
references spade.github (GitHubId);


alter table spade.projectonboard 
add constraint fk_artifactid foreign key(ArtifactoryId)
references spade.artifactory (ArtifactoryId);
alter table spade.projectonboard 
add constraint fk_sonarid foreign key(SonarId)
references spade.sonarqube (SonarId);
alter table spade.projectonboard 
add constraint fk_projectstateid foreign key(ProjectStateId)
references spade.projectstate (ProjectStateId);
alter table spade.projectonboard 
add constraint fk_CustProject foreign key(CustomerId)
references spade.customerinfo (CustomerId);
alter table spade.projectonboard
 add constraint fkey_wtypeid foreign key(workTypeId)
references spade.worktype (workTypeId);
alter table spade.projectonboard
 add constraint fkey_rp foreign key(RallyProjectObjId)
references spade.rallyproject (RallyProjectObjId);

alter table spade.projectonboard
 add constraint fkey_preqtypeid foreign key(ProjectRequestTypeId)
references spade.projectrequesttype (ProjectRequestTypeId);

alter table spade.rallyproject add constraint fkey_rw foreign key(RallyWorkspaceObjId)
references spade.rallyworkspace (RallyWorkspaceObjId);

alter table spade.roletype
add constraint fk_roletype foreign key(RoleId)
references spade.role (RoleId);

alter table spade.useronboard 
add constraint fk_projectid foreign key(ProjectId)
references spade.projectonboard (ProjectId);
alter table spade.useronboard
 add constraint fk_projectroleid foreign key(ProjectRoleId)
references spade.projectrole (ProjectRoleId);
alter table spade.useronboard add constraint fk_usercustid foreign key(CustomerId)references spade.customerinfo (CustomerId);

alter table spade.jenkinsjob
 add constraint fk_jid foreign key(JenkinsId)
references spade.jenkins (JenkinsId);

alter table spade.user add constraint fkey_custuser foreign key(CustomerId)references spade.customerinfo (CustomerId);

alter table spade.groupmember 
add constraint fk_uid foreign key(UserId)
references spade.user (UserId);
alter table spade.groupmember
 add constraint fk_gid foreign key(GroupId)
references spade.group (GroupId);

alter table spade.jenkins 
add constraint fk_jorg foreign key(OrgId)
references spade.organisationunit (OrgId);

alter table spade.github
 add constraint fk_gorg foreign key(OrgId)
references spade.organisationunit (OrgId);

alter table spade.group add constraint fkey_groupcust foreign key(CustomerId)references spade.customerinfo (CustomerId);

alter table spade.rally 
add constraint fk_rorg foreign key(OrgId)
references spade.organisationunit (OrgId);

alter table spade.artifactory
 add constraint fk_aorg foreign key(OrgId)
references spade.organisationunit (OrgId);

alter table spade.sonarqube
 add constraint fk_sorg foreign key(OrgId)
references spade.organisationunit (OrgId);

alter table spade.projectstate add constraint fkey_stateorg foreign key(OrgId)references spade.organisationunit (OrgId);
