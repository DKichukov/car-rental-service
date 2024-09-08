import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { PostCarComponent } from './components/post-car/post-car.component';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NgZorroImportsModule } from 'src/app/ngZorroImportsModule';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [AdminDashboardComponent, PostCarComponent],
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
