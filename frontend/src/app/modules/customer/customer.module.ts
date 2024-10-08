import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerDashboardComponent } from './components/customer-dashboard/customer-dashboard.component';
import { BookCarComponent } from './components/book-car/book-car.component';
import { NgZorroImportsModule } from 'src/app/ngZorroImportsModule';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MyBookingsComponent } from './components/my-bookings/my-bookings.component';
import { GetBookingsComponent } from '../admin/components/get-bookings/get-bookings.component';
import { SearchCarComponent } from './components/search-car/search-car.component';

@NgModule({
  declarations: [
    CustomerDashboardComponent,
    BookCarComponent,
    MyBookingsComponent,
    GetBookingsComponent,
    SearchCarComponent,
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    NgZorroImportsModule,
    ReactiveFormsModule,
    FormsModule,
  ],
})
export class CustomerModule {}
