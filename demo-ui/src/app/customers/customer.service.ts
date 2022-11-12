import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { EntityService } from '../entity.service';
import { Customer } from './customer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService extends EntityService<Customer> {
  constructor(http: HttpClient) { super(http,  "/api/customers")}
}
