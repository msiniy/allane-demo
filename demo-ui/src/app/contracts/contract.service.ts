import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Contract, ContractOverview } from './contract';
import { EntityService } from '../entity.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ContractService extends EntityService<
  ContractOverview,
  ContractOverview
> {
  private static readonly apiEndpoint = '/api/contracts';

  constructor(private readonly httpClient: HttpClient) {
    super(httpClient, ContractService.apiEndpoint);
  }

  save(contract: Contract): Observable<ContractOverview> {
    if (contract.id) {
      return this.httpClient.put<ContractOverview>(
        `${ContractService.apiEndpoint}/${contract.id}`,
        contract
      );
    }
    return this.httpClient.post<ContractOverview>(
      `${ContractService.apiEndpoint}`,
      contract
    );
  }
}
