import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { StorageService } from '../../services/storage/storage.service';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  isSpinning: boolean = false;
  loginFrom!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private message: NzMessageService,
  ) {}

  ngOnInit(): void {
    this.loginFrom = this.fb.group({
      email: ['', [Validators.email, Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  login() {
    this.authService.login(this.loginFrom.value).subscribe((data) => {
      if (data.userId != null) {
        const user = {
          id: data.userId,
          role: data.userRole,
        };
        StorageService.saveUser(user);
        StorageService.saveToken(data.jwt);
        if (StorageService.isAdminLoggedIn()) {
          this.router.navigateByUrl('/admin/dashboard');
        } else if (StorageService.isCustomerLoggedIn()) {
          this.router.navigateByUrl('/customer/dashboard');
        } else {
          this.message.error('Bad cridentials', { nzDuration: 50000 });
        }
      }
    });
  }
}
