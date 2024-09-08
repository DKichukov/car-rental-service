import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss'],
})
export class AdminDashboardComponent implements OnInit {
  cars: any = [];
  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.getAllCars();
  }

  getAllCars(): void {
    this.adminService.getAllCars().subscribe((data) => {
      console.log(data);
      data.forEach((element: any) => {
        element.processedImg = 'data:image/jpeg;base64,' + element.returnedImage;
        this.cars.push(element);
      });
    });
  }


}
