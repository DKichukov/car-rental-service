import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss'],
})
export class AdminDashboardComponent implements OnInit {
  cars: any = [];
  constructor(
    private adminService: AdminService,
    private message: NzMessageService,
  ) {}

  ngOnInit(): void {
    this.getAllCars();
  }

  getAllCars(): void {
    this.adminService.getAllCars().subscribe((data) => {
      console.log(data);
      data.forEach((element: any) => {
        element.processedImg =
          'data:image/jpeg;base64,' + element.returnedImage;
        this.cars.push(element);
      });
    });
  }

  deleteCar(id: number) {
    this.adminService.deleteCar(id).subscribe((res) => {
      this.message.success('Car deleted successfully', { nzDuration: 5000 });
      this.cars = this.cars.filter((car: any) => car.id !== id);
    });
  }
}
