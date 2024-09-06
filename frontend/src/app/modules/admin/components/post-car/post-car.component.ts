import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-car',
  templateUrl: './post-car.component.html',
  styleUrls: ['./post-car.component.scss'],
})
export class PostCarComponent implements OnInit {
  postCarForm!: FormGroup;
  isSpinning: boolean = false;
  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  listOfOption: Array<{ label: string; value: string }> = [];
  listOfBrands = [
    'BMW',
    'AUDI',
    'FERRARI',
    'TESLA',
    'VOLVO',
    'TOYOTA',
    'HONDA',
    'FORD',
    'NISSAN',
    'HYUNDAI',
    'LEXUS',
    'KIA',
  ];
  listOfType = ['Petrol', 'Hybrid', 'Diesel', 'Electric', 'CNG'];
  listOfColor = ['Red', 'White', 'Blue', 'Black', 'Orange', 'Grey', 'Silver'];
  listOfTransmission = ['Manual', 'Automatic'];

  constructor(
    private fb: FormBuilder,
    private adminService: AdminService,
    private message: NzMessageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.postCarForm = this.fb.group({
      name: [null, Validators.required],
      brand: [null, Validators.required],
      type: [null, Validators.required],
      color: [null, Validators.required],
      transmission: [null, Validators.required],
      price: [null, Validators.required],
      description: [null, Validators.required],
      year: [null, Validators.required],
    });
  }

  postCar(): void {
    if (this.postCarForm.valid) {
      this.isSpinning = true;
      const formData: FormData = new FormData();

      if (this.selectedFile) {
        formData.append('image', this.selectedFile, this.selectedFile.name);
      }

      Object.keys(this.postCarForm.controls).forEach((key) => {
        formData.append(key, this.postCarForm.get(key)?.value);
      });

      this.adminService.postCar(formData).subscribe({
        next: (res: any) => {
          this.isSpinning = false;
          this.message.success('Car posted successfully', { nzDuration: 5000 });
          this.router.navigateByUrl('/admin/dashboard');
        },
        error: (err) => {
          this.isSpinning = false;
          this.message.error('Error while posting car', { nzDuration: 5000 });
          console.error(err);
        },
      });
    } else {
      Object.values(this.postCarForm.controls).forEach((control) => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      this.previewImage();
    }
  }

  previewImage(): void {
    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }
}
