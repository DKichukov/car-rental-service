import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NgZorroImportsModule } from 'src/app/ngZorroImportsModule';
import { AdminRoutingModule } from './admin-routing.module';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { PostCarComponent } from './components/post-car/post-car.component';
import { UdateCarComponent } from './components/update-car/update-car.component';

@NgModule({
  declarations: [AdminDashboardComponent, PostCarComponent, UdateCarComponent],
  imports: [
    CommonModule,
    AdminRoutingModule,
    NzSpinModule,
    NgZorroImportsModule,
    ReactiveFormsModule,
    FormsModule,
  ],
})
export class AdminModule {}
