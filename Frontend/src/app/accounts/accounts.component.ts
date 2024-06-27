import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { BankAccount } from '../models/bank-account';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddAccountComponent } from '../add-account/add-account.component';
import { UpdateAccountComponent } from '../update-account/update-account.component';
import { DeleteAccountModalComponent } from '../delete-account-modal/delete-account-modal.component';
import { AccountOperationsComponent } from '../account-operations/account-operations.component';
import { AddOperationModalComponent } from '../add-operation-modal/add-operation-modal.component';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css'],
})
export class AccountsComponent implements OnInit {
  bankAccounts: BankAccount[] = [];
  keyword: string = '';
  page: number = 0;
  size: number = 5;
  totalPages: number = 0;

  constructor(
    private apiService: ApiService,
    private modalService: NgbModal,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.searchBankAccounts();
  }

  getRoles(): string[] {
    return this.authService.getRoles();
  }

  searchBankAccounts(): void {
    this.apiService
      .searchBankAccounts(this.keyword, this.page, this.size)
      .subscribe((data) => {
        this.bankAccounts = data.content;
        this.totalPages = data.totalPages;
      });
  }

  onSearch(): void {
    this.page = 0;
    this.searchBankAccounts();
  }

  onPageChange(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.page = page;
      this.searchBankAccounts();
    }
  }

  goToPage(pageInput: HTMLInputElement): void {
    const pageNumber = Number(pageInput.value) - 1;
    if (pageNumber >= 0 && pageNumber < this.totalPages) {
      this.page = pageNumber;
      this.searchBankAccounts();
    } else {
      alert('Invalid page number');
    }
  }

  openAddAccountModal(): void {
    const modalRef = this.modalService.open(AddAccountComponent);
    modalRef.result.then(
      (result) => {
        if (result === 'added') {
          this.searchBankAccounts();
        }
      },
      (reason) => {}
    );
  }

  openUpdateAccountModal(accountId: string): void {
    const modalRef = this.modalService.open(UpdateAccountComponent);
    modalRef.componentInstance.accountId = accountId;
    modalRef.result.then(
      (result) => {
        if (result === 'updated') {
          this.searchBankAccounts();
        }
      },
      (reason) => {}
    );
  }

  openDeleteAccountModal(accountId: string): void {
    const modalRef = this.modalService.open(DeleteAccountModalComponent);
    modalRef.componentInstance.accountId = accountId;
    modalRef.result.then(
      (result) => {
        if (result === 'deleted') {
          this.searchBankAccounts();
        }
      },
      (reason) => {}
    );
  }

  openAccountOperations(accountId: string): void {
    const modalRef = this.modalService.open(AccountOperationsComponent, {
      size: 'lg',
    });
    modalRef.componentInstance.accountId = accountId;
  }

  openAddOperationModal(accountId: string): void {
    const modalRef = this.modalService.open(AddOperationModalComponent);
    modalRef.componentInstance.accountId = accountId;
    modalRef.result.then(
      (result) => {
        if (result === 'added') {
          this.openAccountOperations(accountId);
        }
      },
      (reason) => {}
    );
  }
}
