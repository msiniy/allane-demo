import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { ContractOverview } from './contract';
import { EntityService } from '../entity.service';

@Injectable({
  providedIn: 'root'
})
export class ContractService extends EntityService<ContractOverview> {
  constructor(http: HttpClient) { super(http,  "/api/contracts")}
}
