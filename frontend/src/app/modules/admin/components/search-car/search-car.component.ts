import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-search-car',
  templateUrl: './search-car.component.html',
  styleUrls: ['./search-car.component.scss'],
})
export class SearchCarComponent {
  isSpinning: boolean = false;
  searchCarForm: FormGroup = new FormGroup({});
  listOfBrands = [
    'BMW',
    'AUDI',
    'FERRARI',
    'TESLA',
    'VOLVO',
    'TOYOTA',
    'HONDA',
    'FORD',
    'NISSAN',
    'HYUNDAI',
    'LEXUS',
    'KIA',
  ];
  listOfType = ['Petrol', 'Hybrid', 'Diesel', 'Electric', 'CNG'];
  listOfColor = ['Red', 'White', 'Blue', 'Black', 'Orange', 'Grey', 'Silver'];
  listOfTransmission = ['Manual', 'Automatic'];

  cars: any = [];

  constructor(private fb: FormBuilder, private adminService: AdminService) {
    this.searchCarForm = this.fb.group({
      brand: [''],
      type: [''],
      transmission: [''],
      color: [''],
    });
  }

  searchCar() {
    this.isSpinning = true;
    this.adminService.seearchCar(this.searchCarForm.value).subscribe((res) => { 
      this.isSpinning = false;
      res.carDtoList.forEach((element: any) => {
        element.processedImg =
          'data:image/jpeg;base64,' + element.returnedImage;
        this.cars.push(element);
      });
    });
  }
}
