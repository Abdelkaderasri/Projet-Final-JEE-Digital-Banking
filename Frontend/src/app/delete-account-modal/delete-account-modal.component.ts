import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-delete-account-modal',
  templateUrl: './delete-account-modal.component.html',
  styleUrls: ['./delete-account-modal.component.css'],
})
export class DeleteAccountModalComponent {
  @Input() accountId!: string;

  constructor(
    public activeModal: NgbActiveModal,
    private apiService: ApiService
  ) {}

  deleteAccount(): void {
    this.apiService.deleteBankAccount(this.accountId).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
