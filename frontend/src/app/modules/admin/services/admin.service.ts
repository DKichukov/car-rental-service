import { HttpClient, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Observable } from 'rxjs';
import { StorageService } from 'src/app/auth/services/storage/storage.service';

const BASE_URL = ['http://localhost:8080'];

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  constructor(private http: HttpClient, private message: NzMessageService) {}

  postCar(carDto: any): Observable<any> {
    const headers = this.createAuthorizationHeader();
    console.log('Headers:', headers);
    return this.http.post(BASE_URL + '/api/admin/car', carDto, {
      headers: this.createAuthorizationHeader(),
    });
  }

  createAuthorizationHeader(): HttpHeaders {
    let autHeaders: HttpHeaders = new HttpHeaders();
    return autHeaders.set(
      'Authorization',
      'Bearer ' + StorageService.getToken()
    );
  }

  getAllCars(): Observable<any> {
    return this.http.get(BASE_URL + '/api/admin/cars', {
      headers: this.createAuthorizationHeader(),
    });
  }

  deleteCar(id: number): Observable<any> {
    return this.http.delete(BASE_URL + '/api/admin/car/' + id, {
      headers: this.createAuthorizationHeader(),
    });
  }

  getCarById(id: number): Observable<any> {
    return this.http.get(BASE_URL + '/api/admin/car/' + id, {
      headers: this.createAuthorizationHeader(),
    });
  }

  updateCar(carId: number, carDto: any): Observable<any> {
    return this.http.put(BASE_URL + '/api/admin/car/' + carId, carDto, {
      headers: this.createAuthorizationHeader(),
    });
  }

  getCarBookings(): Observable<any> {
    return this.http.get(BASE_URL + '/api/admin/car/bookings', {
      headers: this.createAuthorizationHeader(),
    });
  }

  changeBookingStatus(bookingId: number, status: string): Observable<any> {
    return this.http.get(
      `${BASE_URL}/api/admin/car/booking/${bookingId}/${status}`,
      {
        headers: this.createAuthorizationHeader(),
      }
    );
  }
}
