import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClothesSharedModule } from '../../shared';
import {
    DetalleFactPresService,
    DetalleFactPresPopupService,
    DetalleFactPresComponent,
    DetalleFactPresDetailComponent,
    DetalleFactPresDialogComponent,
    DetalleFactPresPopupComponent,
    DetalleFactPresDeletePopupComponent,
    DetalleFactPresDeleteDialogComponent,
    detalleFactPresRoute,
    detalleFactPresPopupRoute,
    DetalleFactPresResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...detalleFactPresRoute,
    ...detalleFactPresPopupRoute,
];

@NgModule({
    imports: [
        ClothesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DetalleFactPresComponent,
        DetalleFactPresDetailComponent,
        DetalleFactPresDialogComponent,
        DetalleFactPresDeleteDialogComponent,
        DetalleFactPresPopupComponent,
        DetalleFactPresDeletePopupComponent,
    ],
    entryComponents: [
        DetalleFactPresComponent,
        DetalleFactPresDialogComponent,
        DetalleFactPresPopupComponent,
        DetalleFactPresDeleteDialogComponent,
        DetalleFactPresDeletePopupComponent,
    ],
    providers: [
        DetalleFactPresService,
        DetalleFactPresPopupService,
        DetalleFactPresResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesDetalleFactPresModule {}
