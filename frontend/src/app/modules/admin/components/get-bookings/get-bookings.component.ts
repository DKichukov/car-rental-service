import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-get-bookings',
  templateUrl: './get-bookings.component.html',
  styleUrls: ['./get-bookings.component.scss'],
})
export class GetBookingsComponent {
  isSpinning: boolean = false;
  bookings: any;
  constructor(
    private adminService: AdminService,
    private message: NzMessageService
  ) {
    this.getBookings();
  }

  getBookings() {
    this.isSpinning = true;
    this.adminService.getCarBookings().subscribe((data) => {
      this.isSpinning = false;
      this.bookings = data;
    });
  }

  changeBookingStatus(bookId: number, status: string) {
    this.isSpinning = true;
    this.adminService.changeBookingStatus(bookId, status).subscribe(
      (data) => {
        this.isSpinning = false;
        this.getBookings();
        this.message.success('Booking status change successfully completed', {
          nzDuration: 5000,
        });
      },
      (err) => {
        this.message.error('Something went wrong!');
      }
    );
  }
}
