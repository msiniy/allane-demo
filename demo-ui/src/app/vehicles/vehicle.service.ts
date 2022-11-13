import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Brand, Model, Vehicle, VehicleDetails } from './vehicle';
import { EntityService } from '../entity.service';
import { Observable } from 'rxjs';

type ModelAndBrand = Model & Brand;

@Injectable({
  providedIn: 'root',
})
export class VehicleService extends EntityService<Vehicle, VehicleDetails> {
  private static readonly apiEndpoint = '/api/vehicles';

  constructor(private readonly httpClient: HttpClient) {
    super(httpClient, VehicleService.apiEndpoint);
  }

  getModels(): Observable<Array<ModelAndBrand>> {
    return this.httpClient.get<ModelAndBrand[]>(
      `${VehicleService.apiEndpoint}/modelsAndBrands`
    );
  }

  save(vehicle: VehicleDetails): Observable<VehicleDetails> {
    if (vehicle.id) {
      return this.httpClient.put<VehicleDetails>(
        `${VehicleService.apiEndpoint}/${vehicle.id}`,
        vehicle
      );
    }
    return this.httpClient.post<VehicleDetails>(
      `${VehicleService.apiEndpoint}`,
      vehicle
    );
  }
}
