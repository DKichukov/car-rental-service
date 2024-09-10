import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../../service/customer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from 'src/app/auth/services/storage/storage.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-book-car',
  templateUrl: './book-car.component.html',
  styleUrls: ['./book-car.component.scss'],
})
export class BookCarComponent implements OnInit {
  car: any;
  processedImage: any;
  cardId: number = this.activatedRoute.snapshot.params['id'];
  validateForm!: FormGroup;
  isSpinning: boolean = false;
  dateFormat: 'dd-MM-YYYY' = 'dd-MM-YYYY';

  constructor(
    private customerService: CustomerService,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      toDate: [null, Validators.required],
      fromDate: [null, Validators.required],
    });

    this.getCardById(this.cardId);
  }

  getCardById(carId: number) {
    this.customerService.getCarById(carId).subscribe((response) => {
      console.log(response);
      this.processedImage = 'data:image/jpeg;base64,' + response.returnedImage;
      this.car = response;
    });
  }

  bookCar(data: any) {
    console.log(data);
    let bookACarDto = {
      toDate: data.toDate,
      fromDate: data.fromDate,
      userId: StorageService.getUserId(),
      carId: this.cardId,
    };
    this.customerService.bookACar(bookACarDto).subscribe({
      next: (response) => {
        console.log(response);
        this.message.success('Booking request submitted successfully', {
          nzDuration: 5000,
        });
        this.router.navigate(['/customer/dashboard']);
      },
      error: (error) => {
        console.error(error);
        this.message.error('Something went wrong Please try again.', {
          nzDuration: 5000,
        });
      },
    });
  }
}
