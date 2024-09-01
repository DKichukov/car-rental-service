import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  isSpinning: boolean = false;
  loginFrom!: FormGroup;
  
  constructor(private fb: FormBuilder) {
    
  }
  
  ngOnInit(): void {
   this.loginFrom = this.fb.group({
    email: ['', [Validators.email, Validators.required]],
    password: ['', [Validators.required]]
   });
  }
  login(){
  console.log(this.loginFrom.value); 
  
  }
  
}
