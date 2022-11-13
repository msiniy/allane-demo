export interface Contract {
  id?: number;
  version: number;
  contractNumber: number;
  monthlyRate: number;
  vehicleId: number;
  customerId: number;
}

export interface ContractOverview {
  contract: Contract;
  customer: {
    id: number;
    firstName: string;
    lastName: string;
    birthDate: Date;
  };
  vehicle: {
    id: number;
    brandName: string;
    modelName: string;
    modelYear: number;
    vin?: string;
    price: number;
  };
}
