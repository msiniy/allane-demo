
export interface ContractOverview {
  contract: {
    id: number;
    version: number;
    contractNumber: number;
    monthlyRate: number;
  }
  customer: {
    firstName: string;
    lastName: string;
  }
  vehicle: {
    brand: string;
    model: string;
    year: number;
    vin: string;
    price: number;
  }
}
