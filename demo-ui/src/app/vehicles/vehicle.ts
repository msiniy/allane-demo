export interface Vehicle {
  id: number;
  brandName: string;
  modelName: string;
  modelYear: number;
}

export interface VehicleDetails extends Vehicle {
  version: number;
  brandId: number;
  modelId: number
  price: number;
  vin?: string;
  contractId?: number;
  contractNumber?: number;
}

export interface Brand {
  brandId: number;
  brandName: string;
}

export interface Model {
  modelId: number;
  modelName: string;
  modelYear: number;
}
