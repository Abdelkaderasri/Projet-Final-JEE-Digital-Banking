import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ApiService } from '../api.service';
import { AccountOperation } from '../models/account-operations';

@Component({
  selector: 'app-account-operations',
  templateUrl: './account-operations.component.html',
  styleUrls: ['./account-operations.component.css'],
})
export class AccountOperationsComponent implements OnInit {
  @Input() accountId!: string;
  operations: AccountOperation[] = [];

  constructor(
    public activeModal: NgbActiveModal,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
    this.loadOperations();
  }

  loadOperations(): void {
    this.apiService.getAccountOperations(this.accountId).subscribe((data) => {
      this.operations = data;
    });
  }
}
