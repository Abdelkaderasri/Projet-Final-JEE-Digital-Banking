import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-delete-customer-modal',
  templateUrl: './delete-customer-modal.component.html',
  styleUrls: ['./delete-customer-modal.component.css'],
})
export class DeleteCustomerModalComponent {
  @Input() customerId!: number;

  constructor(
    public activeModal: NgbActiveModal,
    private apiService: ApiService
  ) {}

  deleteCustomer(): void {
    this.apiService.deleteCustomer(this.customerId).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
