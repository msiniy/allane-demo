import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { EntityService } from '../entity.service';
import { Customer } from './customer';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerService extends EntityService<Customer, Customer> {
  private static readonly apiEndpoint = '/api/customers';

  constructor(private readonly httpClient: HttpClient) {
    super(httpClient, CustomerService.apiEndpoint);
  }

  save(customer: Customer): Observable<Customer> {
    if (customer.id) {
      return this.httpClient.put<Customer>(`${CustomerService.apiEndpoint}/${customer.id}`, customer);
    }
    return this.httpClient.post<Customer>(`${CustomerService.apiEndpoint}`, customer);
  }
}
