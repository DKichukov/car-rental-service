import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './auth/components/signup/signup.component';
import { LoginComponent } from './auth/components/login/login.component';
import { WelcomeComponent } from './welcome/welcome.component';

const routes: Routes = [
  { path: 'register', component: SignupComponent },
  { path: 'login', component: LoginComponent },
  {
    path: 'admin',
    loadChildren: () =>
      import('./modules/admin/admin.module').then((m) => m.AdminModule),
  },
  {
    path: 'customer',
    loadChildren: () =>
      import('./modules/customer/customer.module').then(
        (m) => m.CustomerModule,
      ),
  },
  { path: '', component: WelcomeComponent, data: { isWelcomePage: true } },
  { path: '*', component: WelcomeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
