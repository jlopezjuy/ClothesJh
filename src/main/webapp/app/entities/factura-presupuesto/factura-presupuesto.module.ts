import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClothesSharedModule } from '../../shared';
import {
    FacturaPresupuestoService,
    FacturaPresupuestoPopupService,
    FacturaPresupuestoComponent,
    FacturaPresupuestoDetailComponent,
    FacturaPresupuestoDialogComponent,
    FacturaPresupuestoPopupComponent,
    FacturaPresupuestoDeletePopupComponent,
    FacturaPresupuestoDeleteDialogComponent,
    facturaPresupuestoRoute,
    facturaPresupuestoPopupRoute,
    FacturaPresupuestoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...facturaPresupuestoRoute,
    ...facturaPresupuestoPopupRoute,
];

@NgModule({
    imports: [
        ClothesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FacturaPresupuestoComponent,
        FacturaPresupuestoDetailComponent,
        FacturaPresupuestoDialogComponent,
        FacturaPresupuestoDeleteDialogComponent,
        FacturaPresupuestoPopupComponent,
        FacturaPresupuestoDeletePopupComponent,
    ],
    entryComponents: [
        FacturaPresupuestoComponent,
        FacturaPresupuestoDialogComponent,
        FacturaPresupuestoPopupComponent,
        FacturaPresupuestoDeleteDialogComponent,
        FacturaPresupuestoDeletePopupComponent,
    ],
    providers: [
        FacturaPresupuestoService,
        FacturaPresupuestoPopupService,
        FacturaPresupuestoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesFacturaPresupuestoModule {}
