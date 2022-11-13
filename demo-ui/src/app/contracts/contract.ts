
export interface ContractOverview {
  contract: {
    id: number;
    version: number;
    contractNumber: number;
    monthlyRate: number;
  }
  customer: {
    id: number;
    firstName: string;
    lastName: string;
  }
  vehicle: {
    id: number;
    brandName: string;
    modelName: string;
    modelYear: number;
    vin?: string;
    price: number;
  }
}

