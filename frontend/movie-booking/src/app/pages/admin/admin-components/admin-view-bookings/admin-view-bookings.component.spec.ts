import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminViewBookingsComponent } from './admin-view-bookings.component';

describe('AdminViewBookingsComponent', () => {
  let component: AdminViewBookingsComponent;
  let fixture: ComponentFixture<AdminViewBookingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminViewBookingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminViewBookingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
