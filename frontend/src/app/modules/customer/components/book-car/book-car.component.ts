import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../../service/customer.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-book-car',
  templateUrl: './book-car.component.html',
  styleUrls: ['./book-car.component.scss'],
})
export class BookCarComponent implements OnInit {
  car: any;
  processedImage: any;
  cardId: number = this.activatedRoute.snapshot.params['id'];

  constructor(
    private customerService: CustomerService,
    private activatedRoute: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.getCardById(this.cardId);
  }

  getCardById(carId: number) {
    this.customerService.getCarById(carId).subscribe((response) => {
      console.log(response);
      this.processedImage = 'data:image/jpeg;base64,' + response.returnedImage;
      this.car = response;
    });
  }
}
