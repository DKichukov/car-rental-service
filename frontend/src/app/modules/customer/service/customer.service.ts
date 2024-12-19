import { HttpClient, HttpContext, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from 'src/app/auth/services/storage/storage.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAllCars(): Observable<any> {
    return this.http.get(`${this.apiUrl}/api/customer/cars`, {
      headers: this.createAuthorizationHeader(),
    });
  }

  getCarById(carId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/api/customer/car/${carId}`, {
      headers: this.createAuthorizationHeader(),
    });
  }

  bookACar(bookCarDto: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/api/customer/car/book`, bookCarDto, {
      headers: this.createAuthorizationHeader(),
    });
  }

  getBookingsByUserId(): Observable<any> {
    return this.http.get(
      `${this.apiUrl}/api/customer/car/bookings/${StorageService.getUserId()}`,
      {
        headers: this.createAuthorizationHeader(),
      }
    );
  }

  createAuthorizationHeader(): HttpHeaders {
    let autHeaders: HttpHeaders = new HttpHeaders();
    return autHeaders.set(
      'Authorization',
      'Bearer ' + StorageService.getToken()
    );
  }
  searchCar(searchCarDto: any): Observable<any> {
    const headers = this.createAuthorizationHeader();
    return this.http.post(
      `${this.apiUrl}/api/customer/car/search`,
      searchCarDto,
      {
        headers: this.createAuthorizationHeader(),
      }
    );
  }
}
