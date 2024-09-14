import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent implements OnInit {
  isSpinning: boolean = false;
  signupForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private message: NzMessageService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.signupForm = this.fb.group({
      name: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required]],
      checkPassword: [null, [Validators.required, this.confirmationValidate]],
    });
  }

  confirmationValidate = (control: FormGroup): { [s: string]: boolean } => {
    if (!control.value) {
      return { required: true };
    } else if (control.value !== this.signupForm.controls['password'].value) {
      return { confirm: true, required: true };
    }
    return {};
  };

  register() {
    this.authService.register(this.signupForm.value).subscribe(
        (res) => {
            if (res.id != null) {
                this.message.success('Signup successfully', { nzDuration: 5000 });
                this.router.navigateByUrl('/login');
            } else {
                this.message.error('Something went wrong', { nzDuration: 5000 });
            }
        },
        (error) => {
            if (error.status === 406) {
                this.message.error('Customer already exists!', { nzDuration: 5000 });
            } else if (error.status === 400) {
                this.message.error('Customer not created!', { nzDuration: 5000 });
            } else {
                this.message.error('Something went wrong', { nzDuration: 5000 });
            }
        }
    );
}
}
