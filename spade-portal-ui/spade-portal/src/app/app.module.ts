/* Important Imports */
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { routing } from './app-routing.module';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
//import { ChartModule } from 'angular2-highcharts';
import 'rxjs/add/operator/map';

/* Layer Components*/
import { AppComponent } from './app.component';
import { UiViewComponent } from './ui-view/ui-view.component';
import { ServicesComponent } from './services/services.component';
import { DashboardsComponent } from './ui-view/dashboards/dashboards.component';
import { OnboardingComponent } from './ui-view/onboarding/onboarding.component';
import { KmPortalComponent } from './ui-view/km-portal/km-portal.component';

/* Onboarding Layer Components */
import { ProjectsComponent } from './ui-view/onboarding/projects/projects.component';
import { AddProjectComponent } from './ui-view/onboarding/projects/add-project/add-project.component';
import { ManageProjectsComponent } from './ui-view/onboarding/projects/manage-projects/manage-projects.component';
import { UsersComponent } from './ui-view/onboarding/users/users.component';
import { AddUserComponent } from './ui-view/onboarding/users/add-user/add-user.component';
import { ManageUsersComponent } from './ui-view/onboarding/users/manage-users/manage-users.component';

/* NavBars */
import { NavbarComponent } from './ui-view/navbar/navbar.component';
import { NavbarSpadeComponent } from './ui-view/navbar/navbar-spade/navbar-spade.component';
import { NavbarKmComponent } from './ui-view/navbar/navbar-km/navbar-km.component';

/* Onboarding Home */
import { HomeComponent } from './ui-view/home/home.component';
import { CarouselComponent } from './ui-view/home/carousel/carousel.component';
import { FeaturesComponent } from './ui-view/home/features/features.component';
import { GoogleChartsComponent } from './ui-view/home/google-charts/google-charts.component';

/* Dashboards Layer Component */
import { CustomDashboardsComponent } from './ui-view/dashboards/custom-dashboards/custom-dashboards.component';
import { SideNavbarComponent } from './ui-view/onboarding/side-navbar/side-navbar.component';
import { DashSideNavbarComponent } from './ui-view/dashboards/dash-side-navbar/dash-side-navbar.component';


/* KM Layer Components */
/* Home */
import { KmHomeComponent } from './ui-view/km-portal/km-home/km-home.component';
import { DocumentationComponent } from './ui-view/km-portal/documentation/documentation.component';
import { EnablementPlanComponent } from './ui-view/km-portal/enablement-plan/enablement-plan.component';
import { KmCarouselComponent } from './ui-view/km-portal/km-home/km-carousel/km-carousel.component';
import { ScrumComponent } from './ui-view/km-portal/km-home/km-carousel/scrum/scrum.component';
import { SolutionArchitectComponent } from './ui-view/km-portal/km-home/km-carousel/solution-architect/solution-architect.component';
import { SoftwareDeveloperComponent } from './ui-view/km-portal/km-home/km-carousel/software-developer/software-developer.component';
import { ProjectManagerComponent } from './ui-view/km-portal/km-home/km-carousel/project-manager/project-manager.component';
import { QaEngineerComponent } from './ui-view/km-portal/km-home/km-carousel/qa-engineer/qa-engineer.component';

/* Tools */
import { ToolsComponent } from './ui-view/km-portal/tools/tools.component';
import { AgileCentralComponent } from './ui-view/km-portal/tools/agile-central/agile-central.component';
import { AboutAgileComponent } from './ui-view/km-portal/tools/agile-central/about-agile/about-agile.component';
import { AgileSideNavbarComponent } from './ui-view/km-portal/tools/agile-central/agile-side-navbar/agile-side-navbar.component';
import { OnboardComponent } from './ui-view/km-portal/tools/agile-central/onboard/onboard.component';
import { ArtifactoryComponent } from './ui-view/km-portal/tools/artifactory/artifactory.component';
import { AboutArtifactoryComponent } from './ui-view/km-portal/tools/artifactory/about-artifactory/about-artifactory.component';
import { ArtifactorySideNavbarComponent } from './ui-view/km-portal/tools/artifactory/artifactory-side-navbar/artifactory-side-navbar.component';
import { OnboardArtifactoryComponent } from './ui-view/km-portal/tools/artifactory/onboard-artifactory/onboard-artifactory.component';
import { CdetsComponent } from './ui-view/km-portal/tools/cdets/cdets.component';
import { AboutCdetsComponent } from './ui-view/km-portal/tools/cdets/about-cdets/about-cdets.component';
import { CdetsSideNavbarComponent } from './ui-view/km-portal/tools/cdets/cdets-side-navbar/cdets-side-navbar.component';
import { OnboardCdetsComponent } from './ui-view/km-portal/tools/cdets/onboard-cdets/onboard-cdets.component';
import { EclipseComponent } from './ui-view/km-portal/tools/eclipse/eclipse.component';
import { AboutEclipseComponent } from './ui-view/km-portal/tools/eclipse/about-eclipse/about-eclipse.component';
import { EclipseSideNavbarComponent } from './ui-view/km-portal/tools/eclipse/eclipse-side-navbar/eclipse-side-navbar.component';
import { SonarqubeComponent } from './ui-view/km-portal/tools/sonarqube/sonarqube.component';
import { AboutSonarqubeComponent } from './ui-view/km-portal/tools/sonarqube/about-sonarqube/about-sonarqube.component';
import { OnboardSonarqubeComponent } from './ui-view/km-portal/tools/sonarqube/onboard-sonarqube/onboard-sonarqube.component';
import { SonarqubeSideNavbarComponent } from './ui-view/km-portal/tools/sonarqube/sonarqube-side-navbar/sonarqube-side-navbar.component';
import { Github3Component } from './ui-view/km-portal/tools/github3/github3.component';
import { JenkinsComponent } from './ui-view/km-portal/tools/jenkins/jenkins.component';
import { AboutGithub3Component } from './ui-view/km-portal/tools/github3/about-github3/about-github3.component';
import { AboutJenkinsComponent } from './ui-view/km-portal/tools/jenkins/about-jenkins/about-jenkins.component';
import { OnboardGithub3Component } from './ui-view/km-portal/tools/github3/onboard-github3/onboard-github3.component';
import { OnboardJenkinsComponent } from './ui-view/km-portal/tools/jenkins/onboard-jenkins/onboard-jenkins.component';
import { Github3SideNavbarComponent } from './ui-view/km-portal/tools/github3/github3-side-navbar/github3-side-navbar.component';
import { JenkinsSideNavbarComponent } from './ui-view/km-portal/tools/jenkins/jenkins-side-navbar/jenkins-side-navbar.component';
import { BranchingComponent } from './ui-view/km-portal/tools/github3/branching/branching.component';
import { MigrateComponent } from './ui-view/km-portal/tools/github3/migrate/migrate.component';
import { SscComponent } from './ui-view/km-portal/tools/github3/ssc/ssc.component';
import { PolicyDocumnentsComponent } from './ui-view/km-portal/documentation/policy-documnents/policy-documnents.component';
import { ReferenceComponent } from './ui-view/km-portal/documentation/reference/reference.component';
import { SupportProcessComponent } from './ui-view/km-portal/documentation/support-process/support-process.component';
import { DocSideNavbarComponent } from './ui-view/km-portal/documentation/doc-side-navbar/doc-side-navbar.component';
import { CiscoLearningPhasesComponent } from './ui-view/km-portal/enablement-plan/cisco-learning-phases/cisco-learning-phases.component';
import { EnableSideNavbarComponent } from './ui-view/km-portal/enablement-plan/enable-side-navbar/enable-side-navbar.component';
import { RoleBasedTrackComponent } from './ui-view/km-portal/enablement-plan/role-based-track/role-based-track.component';
import { SampleNsoComponent } from './ui-view/km-portal/enablement-plan/sample-nso/sample-nso.component';
import { SdlpPhasedApproachComponent } from './ui-view/km-portal/enablement-plan/sdlp-phased-approach/sdlp-phased-approach.component';
import { RallyDashboardsComponent } from './ui-view/dashboards/rally-dashboards/rally-dashboards.component';
import { CustomerComponent } from './ui-view/onboarding/customer/customer.component';
import { ManageCustomersComponent } from './ui-view/onboarding/customer/manage-customers/manage-customers.component';
import { EditProjectComponent } from './ui-view/onboarding/projects/edit-project/edit-project.component';
import { EditUserComponent } from './ui-view/onboarding/users/edit-user/edit-user.component';
import { EditCustomerComponent } from './ui-view/onboarding/customer/edit-customer/edit-customer.component';
import { AddCustomerComponent } from './ui-view/onboarding/customer/add-customer/add-customer.component';



@NgModule({
  declarations: [
    AppComponent,
    UiViewComponent,
    ServicesComponent,
    DashboardsComponent,
    OnboardingComponent,
    ProjectsComponent,
    AddProjectComponent,
    ManageProjectsComponent,
    UsersComponent,
    AddUserComponent,
    ManageUsersComponent,
    NavbarComponent,
    NavbarSpadeComponent,
    NavbarKmComponent,
    HomeComponent,
    CarouselComponent,
    FeaturesComponent,
    GoogleChartsComponent,
    CustomDashboardsComponent,
    SideNavbarComponent,
    DashSideNavbarComponent,
    KmPortalComponent,
    KmHomeComponent,
    KmCarouselComponent,
    ScrumComponent,
    SolutionArchitectComponent,
    ProjectManagerComponent,
    SoftwareDeveloperComponent,
    QaEngineerComponent,
    ToolsComponent,
    AgileCentralComponent,
    AboutAgileComponent,
    AgileSideNavbarComponent,
    OnboardComponent,
    ArtifactoryComponent,
    AboutArtifactoryComponent,
    ArtifactorySideNavbarComponent,
    OnboardArtifactoryComponent,
    CdetsComponent,
    AboutCdetsComponent,
    CdetsSideNavbarComponent,
    OnboardCdetsComponent,
    EclipseComponent,
    AboutEclipseComponent,
    EclipseSideNavbarComponent,
    SonarqubeComponent,
    AboutSonarqubeComponent,
    OnboardSonarqubeComponent,
    SonarqubeSideNavbarComponent,
    Github3Component,
    JenkinsComponent,
    AboutGithub3Component,
    AboutJenkinsComponent,
    OnboardGithub3Component,
    OnboardJenkinsComponent,
    Github3SideNavbarComponent,
    JenkinsSideNavbarComponent,
    BranchingComponent,
    MigrateComponent,
    SscComponent,
    DocumentationComponent,
    EnablementPlanComponent,
    PolicyDocumnentsComponent,
    ReferenceComponent,
    SupportProcessComponent,
    DocSideNavbarComponent,
    CiscoLearningPhasesComponent,
    EnableSideNavbarComponent,
    RoleBasedTrackComponent,
    SampleNsoComponent,
    SdlpPhasedApproachComponent,
    RallyDashboardsComponent,
    CustomerComponent,
    ManageCustomersComponent,
    EditProjectComponent,
    EditUserComponent,
    EditCustomerComponent,
    AddCustomerComponent
  ],
  imports: [
    BrowserModule,
    //ChartModule,
    ReactiveFormsModule,
    FormsModule,
    HttpModule,
    routing
  ],
  providers: [
    ServicesComponent,
    ManageProjectsComponent,
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    }
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
