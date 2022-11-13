import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContractDetailsComponent } from './contracts/contract-details.component';
import { ContractListComponent } from './contracts/contract-list.component';
import { CustomerListComponent } from './customers/customer-list.component';
import { VehicleDetailsComponent } from './vehicles/vehicle-details.component';
import { VehicleListComponent } from './vehicles/vehicle-list.component';


const routes: Routes = [
  { path: 'contracts', component: ContractListComponent },
  { path: 'contracts/:id', component: ContractDetailsComponent },
  { path: '', redirectTo: '/contracts', pathMatch: 'full' },
  { path: 'vehicles', component: VehicleListComponent },
  { path: 'vehicles/:id', component: VehicleDetailsComponent },
  { path: 'customers', component: CustomerListComponent }
]


@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
