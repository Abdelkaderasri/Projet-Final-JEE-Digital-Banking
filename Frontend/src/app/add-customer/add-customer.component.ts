import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { Customer } from '../models/customer';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-add-customer',
  templateUrl: './add-customer.component.html',
  styleUrls: ['./add-customer.component.css'],
})
export class AddCustomerComponent {
  customer: Customer = new Customer();

  constructor(
    private apiService: ApiService,
    public activeModal: NgbActiveModal
  ) {}

  addCustomer(): void {
    this.apiService.addCustomer(this.customer).subscribe(() => {
      this.activeModal.close('added');
    });
  }
}
