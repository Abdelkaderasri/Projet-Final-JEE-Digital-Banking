import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ApiService } from '../api.service';
import { BankAccount } from '../models/bank-account';
import { Customer } from '../models/customer';

@Component({
  selector: 'app-update-account',
  templateUrl: './update-account.component.html',
  styleUrls: ['./update-account.component.css'],
})
export class UpdateAccountComponent implements OnInit {
  @Input() accountId!: string;
  bankAccount: BankAccount = new BankAccount();
  customers: Customer[] = [];

  constructor(
    public activeModal: NgbActiveModal,
    private apiService: ApiService
  ) {}

  compareCustomers(c1: Customer, c2: Customer): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  ngOnInit(): void {
    this.apiService.getBankAccount(this.accountId).subscribe((bankAccount) => {
      this.bankAccount = bankAccount;
    });
    this.apiService.getCustomers().subscribe((data) => {
      this.customers = data;
    });
  }

  updateBankAccount(): void {
    this.apiService
      .updateBankAccount(this.bankAccount.id, this.bankAccount)
      .subscribe(() => {
        this.activeModal.close('updated');
      });
  }
}
