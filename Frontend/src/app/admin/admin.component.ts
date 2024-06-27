// src/app/admin/admin.component.ts
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  // Data for account balances chart
  public operationLabels: string[] = ['CREDIT', 'DEBIT'];

  public operationData: any[] = [{ data: [], label: '' }];

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadOperationCounts();
    this.loadCustomerOperationCounts();
  }

  loadOperationCounts(): void {
    this.apiService.loadOperationCounts().subscribe(
      (data: { CREDIT: any; DEBIT: any }) => {
        this.operationData = [
          { data: [data.CREDIT, data.DEBIT], label: 'Operations' },
        ];
        console.log(this.operationData[0].data);
      },
      (error: any) => {
        console.error('Error loading operation counts', error);
      }
    );
  }

  // Data for monthly transactions chart
  public transactionLabels: string[] = [
    'January',
    'February',
    'March',
    'April',
    'May',
  ];
  public transactionData: any[] = [
    { data: [3000, 4000, 3200, 4500, 5000], label: 'Transactions' },
  ];

  // Data for customer distribution chart

  customerData: any[] = [{ data: [], label: '' }];
  customerLabels: string[] = [];

  loadCustomerOperationCounts(): void {
    this.apiService.getOperationCountsByCustomer().subscribe((data) => {
      this.customerLabels = Object.keys(data);

      this.customerData = [
        { data: Object.values(data), label: ' Number of Operations ' },
      ];
    });
  }

  public chartOptions: any = {
    responsive: true,
  };
}
