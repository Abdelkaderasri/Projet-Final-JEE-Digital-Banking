import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../api.service';
import { Customer } from '../models/customer';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-update-customer',
  templateUrl: './update-customer.component.html',
  styleUrls: ['./update-customer.component.css'],
})
export class UpdateCustomerComponent implements OnInit {
  @Input() customerId!: number;
  customer: Customer = new Customer();

  constructor(
    private apiService: ApiService,
    private route: ActivatedRoute,
    public activeModal: NgbActiveModal
  ) {}

  ngOnInit(): void {
    this.apiService.getCustomer(this.customerId).subscribe((customer) => {
      this.customer = customer;
    });
  }

  updateCustomer(): void {
    this.apiService
      .updateCustomer(this.customer.id, this.customer)
      .subscribe(() => {
        this.activeModal.close('updated');
      });
  }
}
