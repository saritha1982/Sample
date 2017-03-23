insert into spadedev.githubrepo values 
(uuid(),
'CICDRepoTest',
'https://github3.cisco.com/CICDPortalTesting/CICDRepoTest.git');


insert into spadedev.rallyworkspace values 
(59461540411,
'AS Software Development',
'mahramai',
'Rally Workspace');

 
insert into spadedev.organisationunit values
(
uuid(),
'Advanced Services',
'Organisation Unit',
59461540411);

INSERT INTO spadedev.githuborg
VALUES
(uuid(),
'CICDPortalTesting');

insert into spadedev.github values (uuid(),'as-ci-user.gen','!Devopsas','https://github3.cisco.com/',
'GitHub',(select OrgId from spadedev.organisationunit));

insert into spadedev.jenkins values(uuid(),'sarbr','Charter$2021','https://engci-private-sjc.cisco.com/jenkins/sso-as/job/sandbox/',
'Sandbox',(select OrgId from spadedev.organisationunit));


insert into spadedev.customerinfo values 
(uuid(),
'AS Community',
'internal',
'',
'',
1234, 

(select GitHubOrgId from spadedev.githuborg),

(select OrgId from spadedev.organisationunit),
1);


insert into spadedev.rally values
(uuid(),
'as-ci-user.gen@cisco.com',
 
'_5SjK7v6WT72boVr2CmUZhhc6jgTt0JZlDUzreNgf5Y',
'https://rally1.rallydev.com/',
1,
'Rally',
(select OrgId from spadedev.organisationunit));


INSERT INTO spadedev.group values
(uuid(),
'ACI-Managers',
 null,
 (select CustomerId from spadedev.customerinfo));


INSERT INTO spadedev.rallyproject
values
(77849357652,
'AS Projects',
null,
59461540411);

 
insert spadedev.technology values 
(uuid(),
 'ACI', 
(select GroupId from spadedev.group), 
 (select RallyProjectObjId from spadedev.rallyproject),
 (select CustomerId from spadedev.customerinfo),
 (select GitHubRepoId from spadedev.githubrepo));

insert spadedev.technology values 
(uuid(),
 'NSO', 
(select GroupId from spadedev.group), 
 (select RallyProjectObjId from spadedev.rallyproject),
 (select CustomerId from spadedev.customerinfo),
 (select GitHubRepoId from spadedev.githubrepo));

insert spadedev.technology values 
(uuid(),
 'Mobility', 
(select GroupId from spadedev.group), 
 (select RallyProjectObjId from spadedev.rallyproject),
 (select CustomerId from spadedev.customerinfo),
 (select GitHubRepoId from spadedev.githubrepo));

insert spadedev.technology values 
(uuid(),
 'Cloud Automation', 
(select GroupId from spadedev.group), 
 (select RallyProjectObjId from spadedev.rallyproject),
 (select CustomerId from spadedev.customerinfo),
 (select GitHubRepoId from spadedev.githubrepo));
 
 SELECT * FROM spadedev.technology;insert spadedev.technology values 
(uuid(),
 'Add New Technology', 
(select GroupId from spadedev.group), 
 (select RallyProjectObjId from spadedev.rallyproject),
 (select CustomerId from spadedev.customerinfo),
 (select GitHubRepoId from spadedev.githubrepo));
 
 
INSERT INTO spadedev.projecttype
VALUES
(uuid(),
'AS Fixed',
(select CustomerId from spadedev.customerinfo));

INSERT INTO spadedev.projecttype
VALUES
(uuid(),
'AS Transactional',
(select CustomerId from spadedev.customerinfo));

INSERT INTO spadedev.projecttype
VALUES
(uuid(),
'Subscription',
(select CustomerId from spadedev.customerinfo));


INSERT INTO spadedev.worktype 
VALUES
(uuid(),
'Practise Initiative',
(select CustomerId from spadedev.customerinfo));


INSERT INTO spadedev.sonarqube VALUES
(uuid(),'as-ci-user.gen',
'!Devopsas',
'https://engci-sonar-blr.cisco.com/sonar',
'Sonar',
(select OrgId from spadedev.organisationunit));


INSERT INTO spadedev.artifactory
VALUES
(uuid(),
'as-ci-user.gen',
'!Devopsas',
'http://engci-maven.cisco.com/artifactory',
'Artifactory',
(select OrgId from spadedev.organisationunit));


INSERT INTO spadedev.projectrequesttype
VALUES
(uuid(),
'New Request',
(select CustomerId from spadedev.customerinfo));
INSERT INTO spadedev.projectrequesttype
VALUES
(uuid(),
'Change Request',
(select CustomerId from spadedev.customerinfo));
INSERT INTO spadedev.projectrequesttype
VALUES
(uuid(),
'Code Dev',
(select CustomerId from spadedev.customerinfo));

INSERT INTO spadedev.portalconfig
(ConfigId,
ConfigKey,
ConfigValue)
VALUES
(uuid(),
'activitiUser',
'kermit');
INSERT INTO spadedev.portalconfig
(ConfigId,
ConfigKey,
ConfigValue)
VALUES
(uuid(),
'activitiPWD',
'kermit');
INSERT INTO spadedev.portalconfig
(ConfigId,
ConfigKey,
ConfigValue)
VALUES
(uuid(),
'activitiURL',
'http://127.0.0.1:8090/activiti-rest/service');


insert into spadedev.projectrole values (uuid(),'Business Owner','User');
insert into spadedev.projectrole values (uuid(),'Lead Solution Owner','Admin');
insert into spadedev.projectrole values (uuid(),'Software Developer','User');
insert into spadedev.projectrole values (uuid(),'Project Manager','User');
insert into spadedev.projectrole values (uuid(),'Architech / Tech Lead','User');
insert into spadedev.projectrole values (uuid(),'QA Engineer','User');
insert into spadedev.projectrole values (uuid(),'Sales','User');
insert into spadedev.projectrole values (uuid(),'SDM','User');
insert into spadedev.projectrole values (uuid(),'PMO','User');

INSERT INTO spadedev.projectstate
VALUES
(uuid(),
'Defined',
(select OrgId from spadedev.organisationunit));

INSERT INTO spadedev.projectstate
VALUES
(uuid(),
'Dev InProgress',
(select OrgId from spadedev.organisationunit));

INSERT INTO spadedev.projectstate
VALUES
(uuid(),
'QA Test InProgress',
(select OrgId from spadedev.organisationunit));

INSERT INTO spadedev.projectstate
VALUES
(uuid(),
'UAT InProgress',
(select OrgId from spadedev.organisationunit));

insert into spadedev.user values 
(uuid(), 'Reshmi' ,'reshchan@cisco.com',null,(select CustomerId from spadedev.customerinfo),
(select ProjectRoleId from spadedev.projectrole where ProjectRole = 'Project Manager'));

insert into spadedev.user values 
(uuid(), 'Mahalakshmi' ,'mahramai@cisco.com',null,(select CustomerId from spadedev.customerinfo),
(select ProjectRoleId from spadedev.projectrole where ProjectRole = 'Project Manager'));

insert into spadedev.user values 
(uuid(), 'Vijay' ,'vkumar@cisco.com',null,(select CustomerId from spadedev.customerinfo),
(select ProjectRoleId from spadedev.projectrole where ProjectRole = 'Project Manager'));


insert into spadedev.groupmember values ((select GroupId from spadedev.group),(select UserId from spadedev.user
where Name = 'Reshmi'));

insert into spadedev.groupmember values ((select GroupId from spadedev.group),(select UserId from spadedev.user
where Name = 'Mahalakshmi'));

insert into spadedev.groupmember values ((select GroupId from spadedev.group),(select UserId from spadedev.user
where Name = 'Vijay'));







  
  



