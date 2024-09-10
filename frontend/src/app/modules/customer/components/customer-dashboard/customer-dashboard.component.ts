import { Component } from '@angular/core';
import { CustomerService } from '../../service/customer.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-customer-dashboard',
  templateUrl: './customer-dashboard.component.html',
  styleUrls: ['./customer-dashboard.component.scss'],
})
export class CustomerDashboardComponent {
  cars: any[] = [];

  constructor(
    private customerService: CustomerService,
    private message: NzMessageService,
  ) {}

  ngOnInit(): void {
    this.getAllCars();
  }

  getAllCars(): void {
    this.customerService.getAllCars().subscribe((data) => {
      console.log(data);
      data.forEach((element: any) => {
        element.processedImg =
          'data:image/jpeg;base64,' + element.returnedImage;
        this.cars.push(element);
      });
    });
  }
}
