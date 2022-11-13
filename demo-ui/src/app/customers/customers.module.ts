import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerListComponent } from './customer-list.component';
import { AppRoutingModule } from '../app-routing.module';



@NgModule({
  declarations: [
    CustomerListComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
  ]
})
export class CustomersModule { }
