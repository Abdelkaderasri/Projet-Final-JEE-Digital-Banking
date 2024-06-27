import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { Customer } from '../models/customer';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddCustomerComponent } from '../add-customer/add-customer.component';
import { UpdateCustomerComponent } from '../update-customer/update-customer.component';
import { DeleteCustomerModalComponent } from '../delete-customer-modal/delete-customer-modal.component';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css'],
})
export class CustomersComponent implements OnInit {
  customers: Customer[] = [];
  keyword: string = '';
  page: number = 0;
  size: number = 5;
  totalPages: number = 0;
  loadCustomers: any;

  constructor(
    private apiService: ApiService,
    private modalService: NgbModal,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.searchCustomers();
  }

  getRoles(): string[] {
    return this.authService.getRoles();
  }

  searchCustomers(): void {
    this.apiService
      .searchCustomers(this.keyword, this.page, this.size)
      .subscribe((data) => {
        this.customers = data.content;
        this.totalPages = data.totalPages;
      });
  }

  onSearch(): void {
    this.page = 0;
    this.searchCustomers();
  }

  onPageChange(page: number): void {
    this.page = page;
    this.searchCustomers();
  }

  openAddCustomerModal(): void {
    const modalRef = this.modalService.open(AddCustomerComponent);
    modalRef.result.then(
      (result) => {
        if (result === 'added') {
          this.searchCustomers();
        }
      },
      (reason) => {}
    );
  }

  openUpdateCustomerModal(customerId: number): void {
    const modalRef = this.modalService.open(UpdateCustomerComponent);
    modalRef.componentInstance.customerId = customerId;
    modalRef.result.then(
      (result) => {
        if (result === 'updated') {
          this.searchCustomers();
        }
      },
      (reason) => {}
    );
  }

  openDeleteCustomerModal(customerId: number): void {
    const modalRef = this.modalService.open(DeleteCustomerModalComponent);
    modalRef.componentInstance.customerId = customerId;
    modalRef.result.then(
      (result) => {
        if (result === 'deleted') {
          this.searchCustomers();
        }
      },
      (reason) => {}
    );
  }
}
