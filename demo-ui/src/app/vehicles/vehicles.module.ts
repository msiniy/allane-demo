import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { VehicleListComponent } from './vehicle-list.component';
import { AppRoutingModule } from '../app-routing.module';
import { VehicleDetailsComponent } from './vehicle-details.component';



@NgModule({
  declarations: [
    VehicleListComponent,
    VehicleDetailsComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    ReactiveFormsModule,
  ]
})
export class VehiclesModule { }
