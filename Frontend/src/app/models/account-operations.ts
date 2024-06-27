export class AccountOperation {
  id!: number;
  operationDate!: Date;
  amount!: number;
  type!: OperationType;
}

export enum OperationType {
  DEBIT = 'DEBIT',
  CREDIT = 'CREDIT',
}
