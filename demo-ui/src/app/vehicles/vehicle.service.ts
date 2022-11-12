import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Vehicle } from './vehicle';
import { EntityService } from '../entity.service';

@Injectable({
  providedIn: 'root'
})
export class VehicleService extends EntityService<Vehicle> {
  constructor(http: HttpClient) { super(http,  "/api/vehicles")}
}
