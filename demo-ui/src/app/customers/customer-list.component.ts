import { Component, OnInit } from '@angular/core';
import { EntityListComponent } from '../entity-list.component';
import { Customer } from './customer';
import { CustomerService } from './customer.service';

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})
export class CustomerListComponent extends EntityListComponent<Customer, Customer> implements OnInit {

  constructor(private customerService: CustomerService) {
    super(customerService);
   }

   ngOnInit(): void {
    this.switchPage(0);
  }

}
