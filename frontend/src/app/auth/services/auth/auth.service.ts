import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const BASIC_URL = environment.apiUrl;

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  register(signupRequest: any): Observable<any> {
    return this.http.post(BASIC_URL + '/api/auth/signup', signupRequest);
  }
  login(loginRequest: any): Observable<any> {
    return this.http.post(BASIC_URL + '/api/auth/login', loginRequest);
  }
}
