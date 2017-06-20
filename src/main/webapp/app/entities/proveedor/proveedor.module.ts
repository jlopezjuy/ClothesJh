import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClothesSharedModule } from '../../shared';
import {
    ProveedorService,
    ProveedorPopupService,
    ProveedorComponent,
    ProveedorDetailComponent,
    ProveedorDialogComponent,
    ProveedorPopupComponent,
    ProveedorDeletePopupComponent,
    ProveedorDeleteDialogComponent,
    proveedorRoute,
    proveedorPopupRoute,
    ProveedorResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...proveedorRoute,
    ...proveedorPopupRoute,
];

@NgModule({
    imports: [
        ClothesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProveedorComponent,
        ProveedorDetailComponent,
        ProveedorDialogComponent,
        ProveedorDeleteDialogComponent,
        ProveedorPopupComponent,
        ProveedorDeletePopupComponent,
    ],
    entryComponents: [
        ProveedorComponent,
        ProveedorDialogComponent,
        ProveedorPopupComponent,
        ProveedorDeleteDialogComponent,
        ProveedorDeletePopupComponent,
    ],
    providers: [
        ProveedorService,
        ProveedorPopupService,
        ProveedorResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesProveedorModule {}
