import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from '../app-routing.module';

import { CustomerListComponent } from './customer-list.component';
import { CustomerDetailsComponent } from './customer-details.component';



@NgModule({
  declarations: [
    CustomerListComponent,
    CustomerDetailsComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    ReactiveFormsModule,
  ]
})
export class CustomersModule { }
