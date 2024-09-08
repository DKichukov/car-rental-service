import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UdateCarComponent } from './udate-car.component';

describe('UdateCarComponent', () => {
  let component: UdateCarComponent;
  let fixture: ComponentFixture<UdateCarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UdateCarComponent],
    });
    fixture = TestBed.createComponent(UdateCarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
