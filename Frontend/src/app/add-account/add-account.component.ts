import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ApiService } from '../api.service';
import { BankAccount } from '../models/bank-account';
import { Customer } from '../models/customer';

@Component({
  selector: 'app-add-account',
  templateUrl: './add-account.component.html',
  styleUrls: ['./add-account.component.css'],
})
export class AddAccountComponent implements OnInit {
  bankAccount: BankAccount = new BankAccount();
  customers: Customer[] = [];

  constructor(
    public activeModal: NgbActiveModal,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
    this.apiService.getCustomers().subscribe((data) => {
      this.customers = data;
    });
  }

  addBankAccount(): void {
    this.apiService.addBankAccount(this.bankAccount).subscribe(() => {
      this.activeModal.close('added');
    });
  }
}
