import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ApiService } from '../api.service';
import { AccountOperation, OperationType } from '../models/account-operations';

@Component({
  selector: 'app-add-operation-modal',
  templateUrl: './add-operation-modal.component.html',
  styleUrls: ['./add-operation-modal.component.css'],
})
export class AddOperationModalComponent implements OnInit {
  @Input() accountId!: string;
  operation: AccountOperation = new AccountOperation();
  operationTypes = OperationType;

  constructor(
    public activeModal: NgbActiveModal,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
    this.operation.operationDate = new Date();
  }

  saveOperation(): void {
    this.apiService
      .addAccountOperation(this.accountId, this.operation)
      .subscribe(() => {
        this.activeModal.close('added');
      });
  }
}
