import {NgModule, ModuleWithProviders} from "@angular/core";
import {Routes, RouterModule} from "@angular/router";
/* UI-View Components */
import {HomeComponent} from "./ui-view/home/home.component";
/* Dashboards Components */
import {DashboardsComponent} from "./ui-view/dashboards/dashboards.component";
import {CustomDashboardsComponent} from "./ui-view/dashboards/custom-dashboards/custom-dashboards.component";
/* Onboarding */
import {OnboardingComponent} from "./ui-view/onboarding/onboarding.component";
import {ManageProjectsComponent} from "./ui-view/onboarding/projects/manage-projects/manage-projects.component";
import {AddProjectComponent} from "./ui-view/onboarding/projects/add-project/add-project.component";
import {ManageUsersComponent} from "./ui-view/onboarding/users/manage-users/manage-users.component";
import {AddUserComponent} from "./ui-view/onboarding/users/add-user/add-user.component";
import {KmPortalComponent} from "./ui-view/km-portal/km-portal.component";
import {KmHomeComponent} from "./ui-view/km-portal/km-home/km-home.component";
import {ScrumComponent} from "./ui-view/km-portal/km-home/km-carousel/scrum/scrum.component";
import {SolutionArchitectComponent} from "./ui-view/km-portal/km-home/km-carousel/solution-architect/solution-architect.component";
import {ProjectManagerComponent} from "./ui-view/km-portal/km-home/km-carousel/project-manager/project-manager.component";
import {QaEngineerComponent} from "./ui-view/km-portal/km-home/km-carousel/qa-engineer/qa-engineer.component";
import {SoftwareDeveloperComponent} from "./ui-view/km-portal/km-home/km-carousel/software-developer/software-developer.component";
import {AgileCentralComponent} from "./ui-view/km-portal/tools/agile-central/agile-central.component";
import {AboutAgileComponent} from "./ui-view/km-portal/tools/agile-central/about-agile/about-agile.component";
import {OnboardComponent} from "./ui-view/km-portal/tools/agile-central/onboard/onboard.component";
import {ArtifactoryComponent} from "./ui-view/km-portal/tools/artifactory/artifactory.component";
import {OnboardArtifactoryComponent} from "./ui-view/km-portal/tools/artifactory/onboard-artifactory/onboard-artifactory.component";
import {AboutArtifactoryComponent} from "./ui-view/km-portal/tools/artifactory/about-artifactory/about-artifactory.component";
import {OnboardCdetsComponent} from "./ui-view/km-portal/tools/cdets/onboard-cdets/onboard-cdets.component";
import {CdetsComponent} from "./ui-view/km-portal/tools/cdets/cdets.component";
import {AboutCdetsComponent} from "./ui-view/km-portal/tools/cdets/about-cdets/about-cdets.component";
import {EclipseComponent} from "./ui-view/km-portal/tools/eclipse/eclipse.component";
import {AboutEclipseComponent} from "./ui-view/km-portal/tools/eclipse/about-eclipse/about-eclipse.component";
import {Github3Component} from "./ui-view/km-portal/tools/github3/github3.component";
import {AboutGithub3Component} from "./ui-view/km-portal/tools/github3/about-github3/about-github3.component";
import {OnboardGithub3Component} from "./ui-view/km-portal/tools/github3/onboard-github3/onboard-github3.component";
import {BranchingComponent} from "./ui-view/km-portal/tools/github3/branching/branching.component";
import {MigrateComponent} from "./ui-view/km-portal/tools/github3/migrate/migrate.component";
import {SscComponent} from "./ui-view/km-portal/tools/github3/ssc/ssc.component";
import {JenkinsComponent} from "./ui-view/km-portal/tools/jenkins/jenkins.component";
import {AboutJenkinsComponent} from "./ui-view/km-portal/tools/jenkins/about-jenkins/about-jenkins.component";
import {OnboardJenkinsComponent} from "./ui-view/km-portal/tools/jenkins/onboard-jenkins/onboard-jenkins.component";
import {SonarqubeComponent} from "./ui-view/km-portal/tools/sonarqube/sonarqube.component";
import {AboutSonarqubeComponent} from "./ui-view/km-portal/tools/sonarqube/about-sonarqube/about-sonarqube.component";
import {OnboardSonarqubeComponent} from "./ui-view/km-portal/tools/sonarqube/onboard-sonarqube/onboard-sonarqube.component";
import {DocumentationComponent} from "./ui-view/km-portal/documentation/documentation.component";
import {SupportProcessComponent} from "./ui-view/km-portal/documentation/support-process/support-process.component";
import {ReferenceComponent} from "./ui-view/km-portal/documentation/reference/reference.component";
import {PolicyDocumnentsComponent} from "./ui-view/km-portal/documentation/policy-documnents/policy-documnents.component";
import {EnablementPlanComponent} from "./ui-view/km-portal/enablement-plan/enablement-plan.component";
import {RoleBasedTrackComponent} from "./ui-view/km-portal/enablement-plan/role-based-track/role-based-track.component";
import {SdlpPhasedApproachComponent} from "./ui-view/km-portal/enablement-plan/sdlp-phased-approach/sdlp-phased-approach.component";
import {CiscoLearningPhasesComponent} from "./ui-view/km-portal/enablement-plan/cisco-learning-phases/cisco-learning-phases.component";
import {SampleNsoComponent} from "./ui-view/km-portal/enablement-plan/sample-nso/sample-nso.component";
import {EditProjectComponent} from "./ui-view/onboarding/projects/edit-project/edit-project.component";
import {ManageCustomersComponent} from "./ui-view/onboarding/customer/manage-customers/manage-customers.component";
import {EditUserComponent} from "./ui-view/onboarding/users/edit-user/edit-user.component";
import {EditCustomerComponent} from "./ui-view/onboarding/customer/edit-customer/edit-customer.component";
import {AddCustomerComponent} from "./ui-view/onboarding/customer/add-customer/add-customer.component";

const routes: Routes = [
    {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'onboarding',
        component: OnboardingComponent,
        children: [
            {
              path: '',
              redirectTo: 'manage-projects',
              pathMatch: 'full'
            },
            {
              path: 'manage-projects',
              component: ManageProjectsComponent
            },
            {
              path: 'add-project',
              component: AddProjectComponent
            },
            {
              path: 'manage-users',
              component: ManageUsersComponent
            },
            {
              path: 'add-user',
              component: AddUserComponent
            },
            {
              path: 'manage-customers',
              component: ManageCustomersComponent
            },
            {
              path: 'add-customer',
              component: AddCustomerComponent
            }
        ]
    },
    {
        path: 'edit-project',
        redirectTo: '/onboarding/edit-project',
        pathMatch: 'full'
    },
    {
        path: 'onboarding/edit-project',
        component: EditProjectComponent
    },
    {
        path:'edit-user',
        redirectTo : '/onboarding/edit-user',
        pathMatch : 'full'

    },
    {
        path: 'onboarding/edit-user',
        component : EditUserComponent

    },
    {
      path:'edit-customer',
      redirectTo : '/onboarding/edit-customer',
      pathMatch : 'full'

    },
    {
      path: 'onboarding/edit-customer',
      component : EditCustomerComponent

    },
    {
        path: 'dashboards',
        component: DashboardsComponent,
        children: [
            {
              path: '',
              redirectTo: 'custom-dashboards',
              pathMatch: 'full'
            },
            {
              path: 'custom-dashboards',
              component: CustomDashboardsComponent
            }
        ]
    },
    {
        path: 'km',
        component: KmPortalComponent,
        children: [
            {
              path: '',
              redirectTo: 'home',
              pathMatch: 'full'
            },
            {
              path: 'home',
              component: KmHomeComponent,
              children: [
                  {
                    path: '',
                    redirectTo: 'scrum',
                    pathMatch: 'full'
                  },
                  {
                    path: 'scrum',
                    component: ScrumComponent
                  },
                  {
                    path: 'solution-architect',
                    component: SolutionArchitectComponent
                  },
                  {
                    path: 'project-manager',
                    component: ProjectManagerComponent
                  },
                  {
                    path: 'software-developer',
                    component: SoftwareDeveloperComponent
                  },
                  {
                    path: 'qa-engineer',
                    component: QaEngineerComponent
                  }
              ]
            },
            {
              path: 'documentation',
              component: DocumentationComponent,
              children: [
                {
                  path: '',
                  redirectTo: 'policy-documents',
                  pathMatch: 'full'
                },
                {
                  path: 'policy-documents',
                  component: PolicyDocumnentsComponent
                },
                {
                  path: 'support-process',
                  component: SupportProcessComponent
                },
                {
                  path: 'reference',
                  component: ReferenceComponent
                }
              ]
            },
            {
              path: 'enablement-plan',
              component: EnablementPlanComponent,
              children: [
                {
                  path: '',
                  redirectTo: 'role-based-track',
                  pathMatch: 'full'
                },
                {
                  path: 'role-based-track',
                  component: RoleBasedTrackComponent
                },
                {
                  path: 'sdlp-phased-approach',
                  component: SdlpPhasedApproachComponent
                },
                {
                  path: 'cisco-learning-phases',
                  component: CiscoLearningPhasesComponent
                },
                {
                  path: 'sample-nso',
                  component: SampleNsoComponent
                }
              ]
            },
            {
              path: 'agile-central',
              component: AgileCentralComponent,
              children: [
                  {
                    path: '',
                    redirectTo: 'about-agile',
                    pathMatch: 'full'
                  },
                  {
                    path: 'about-agile',
                    component: AboutAgileComponent
                  },
                  {
                    path: 'how-to-onboard',
                    component: OnboardComponent
                  }
              ]
            },
            {
              path: 'artifactory',
              component: ArtifactoryComponent,
              children: [
                  {
                    path: '',
                    redirectTo: 'about-artifactory',
                    pathMatch: 'full'
                  },
                  {
                    path: 'about-artifactory',
                    component: AboutArtifactoryComponent
                  },
                  {
                    path: 'onboard-artifactory',
                    component: OnboardArtifactoryComponent
                  }
              ]
            },
            {
              path: 'cdets',
              component: CdetsComponent,
              children: [
                  {
                    path: '',
                    redirectTo: 'about-cdets',
                    pathMatch: 'full'
                  },
                  {
                    path: 'about-cdets',
                    component: AboutCdetsComponent
                  },
                  {
                    path: 'onboard-cdets',
                    component: OnboardCdetsComponent
                  }
              ]
            },
            {
              path: 'eclipse',
              component: EclipseComponent,
              children: [
                  {
                    path: '',
                    redirectTo: 'about-eclipse',
                    pathMatch: 'full'
                  },
                  {
                    path: 'about-eclipse',
                    component: AboutEclipseComponent
                  }
              ]
            },
            {
              path: 'github3',
              component: Github3Component,
              children: [
                  {
                    path: '',
                    redirectTo: 'about-github3',
                    pathMatch: 'full'
                  },
                  {
                    path: 'about-github3',
                    component: AboutGithub3Component
                  },
                  {
                    path: 'onboard-github3',
                    component: OnboardGithub3Component
                  },
                  {
                    path: 'branching-and-structure',
                    component: BranchingComponent
                  },
                  {
                    path: 'migrate',
                    component: MigrateComponent
                  },
                  {
                    path: 'publishing-to-ssc',
                    component: SscComponent
                  }
              ]
            },
            {
              path: 'jenkins',
              component: JenkinsComponent,
              children: [
                  {
                    path: '',
                    redirectTo: 'about-jenkins',
                    pathMatch: 'full'
                  },
                  {
                    path: 'about-jenkins',
                    component: AboutJenkinsComponent
                  },
                  {
                    path: 'onboard-jenkins',
                    component: OnboardJenkinsComponent
                  }
              ]
            },
            {
              path: 'sonarqube',
              component: SonarqubeComponent,
              children: [
                  {
                    path: '',
                    redirectTo: 'about-sonarqube',
                    pathMatch: 'full'
                  },
                  {
                    path: 'about-sonarqube',
                    component: AboutSonarqubeComponent
                  },
                  {
                    path: 'onboard-sonarqube',
                    component: OnboardSonarqubeComponent
                  }
              ]
            }
        ]

    }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: []
})
export class KmAdvancedServicesTestRoutingModule { }

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);
