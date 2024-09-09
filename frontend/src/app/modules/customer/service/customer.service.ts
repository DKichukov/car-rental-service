import { HttpClient, HttpContext, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from 'src/app/auth/services/storage/storage.service';

const BASE_URL = ['http://localhost:8080'];

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  constructor(private http: HttpClient) {}

  getAllCars(): Observable<any> {
    return this.http.get(BASE_URL + '/api/customer/cars', {
      headers: this.createAuthorizationHeader(),
    });
  }

  getCarById(carId: number): Observable<any> {
    return this.http.get(BASE_URL + '/api/customer/car/' + carId, {
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
}
